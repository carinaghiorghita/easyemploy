package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.Token;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserService userService;
    private final CompanyService companyService;
    private final TokenService tokenService;
    private final EmailService emailService;

    public Optional<? extends BaseUser> getUser(String username, String password){
        var user = userService.getUserByUsername(username);
        if(user.isEmpty()){
            var company = companyService.getCompanyByUsername(username);
            if(company.isEmpty() || !BCrypt.checkpw(password, company.get().getPassword()))
                return Optional.empty();
            return company;
        }
        else if(!BCrypt.checkpw(password, user.get().getPassword()))
            return Optional.empty();
        return user;
    }

    public Optional<? extends BaseUser> getUserByEmail(String email){
        var user = userService.getUserByEmail(email);
        if(user.isEmpty()){
            var company = companyService.getCompanyByEmail(email);
            if(company.isEmpty())
                return Optional.empty();
            return company;
        }
        return user;
    }

    public Optional<? extends BaseUser> getUserByUsername(String username){
        var user = userService.getUserByUsername(username);
        if(user.isEmpty()){
            var company = companyService.getCompanyByUsername(username);
            if(company.isEmpty())
                return Optional.empty();
            return company;
        }
        return user;
    }


    public void signUp(String email, String password, String role){
        if(role.equals("USER")){
            var user = new User("","",email,"","",this.hashPassword(password),false);
            userService.saveUser(user);
        }
        else if(role.equals("COMPANY")){
            var company = new Company("",email,"","",this.hashPassword(password),false);
            companyService.saveCompany(company);
        }
        else {
            throw new IllegalArgumentException("Invalid role");
        }

        String tokenString = UUID.randomUUID().toString();

        Token token = new Token(
                tokenString,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                email
        );

        tokenService.saveToken(token);

        String link = "http://localhost:4200/confirm-account?token=" + tokenString;
        emailService.send(
                email,
                link);

    }

    public void confirm(String tokenString) {
        var token = tokenService.getToken(tokenString).orElseThrow(
                () -> new IllegalArgumentException("Invalid token")
        );

        if (token.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = token.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token has expired");
        }

        tokenService.setConfirmedAt(tokenString);

        var user = this.getUserByEmail(token.getEmail());
        if(user.isEmpty())
            throw new IllegalStateException("Invalid email");
        user.get().setActivated(true);
        if(user.get() instanceof User)
            userService.updateUser((User) user.get());
        else
            companyService.updateCompany((Company) user.get());
    }

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
