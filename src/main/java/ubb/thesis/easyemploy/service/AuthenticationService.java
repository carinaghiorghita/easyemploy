package ubb.thesis.easyemploy.service;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.domain.entities.Token;
import ubb.thesis.easyemploy.domain.entities.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private static final String USER = "USER";
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserCompanyRelationService userCompanyRelationService;

    public Optional<BaseUser> loginByUsername(String username, String password) {
        var user = userCompanyRelationService.getUserByUsername(username);
        if (user.isEmpty()) {
            var company = userCompanyRelationService.getCompanyByUsername(username);
            if (company.isEmpty() || !BCrypt.checkpw(password, company.get().getPassword()))
                return Optional.empty();
            return Optional.of(company.get());
        } else if (!BCrypt.checkpw(password, user.get().getPassword()))
            return Optional.empty();
        return Optional.of(user.get());
    }

    public Optional<BaseUser> loginByEmail(String email, String password) {
        var user = userCompanyRelationService.getUserByEmail(email);
        if (user.isEmpty()) {
            var company = userCompanyRelationService.getCompanyByEmail(email);
            if (company.isEmpty() || !BCrypt.checkpw(password, company.get().getPassword()))
                return Optional.empty();
            return Optional.of(company.get());
        } else if (!BCrypt.checkpw(password, user.get().getPassword()))
            return Optional.empty();
        return Optional.of(user.get());
    }

    public Optional<BaseUser> getUserByEmail(String email) {
        var user = userCompanyRelationService.getUserByEmail(email);
        if (user.isEmpty()) {
            var company = userCompanyRelationService.getCompanyByEmail(email);
            if (company.isEmpty())
                return Optional.empty();
            return Optional.of(company.get());
        }
        return Optional.of(user.get());
    }

    public void updateUser(BaseUser user) {
        if (Objects.equals(user.getRole(), USER))
            this.userCompanyRelationService.updateUser((User) user);
        else
            this.userCompanyRelationService.updateCompany((Company) user);
    }

    public void signUp(String email, String password, String role) {
        if (role.equals(USER)) {
            var user = new User("", "", email, "", "", this.hashPassword(password), "", false);
            userCompanyRelationService.saveUser(user);
        } else if (role.equals("COMPANY")) {
            var company = new Company("", email, "", "", this.hashPassword(password), "", false);
            userCompanyRelationService.saveCompany(company);
        } else {
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

        String messageText = "Hello! You're one step away from creating your EasyEmploy account. Click here to confirm your registration: http://localhost:4200/confirm-account?token=" + tokenString;
        String subject = "Confirm registration";
        emailService.send(
                email,
                subject,
                messageText);

    }

    public void resetPassword(BaseUser user) {
        String tokenString = UUID.randomUUID().toString();

        Token token = new Token(
                tokenString,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user.getEmail()
        );

        tokenService.saveToken(token);

        String messageText = "Hello! Click here to reset your password: http://localhost:4200/reset-password?token=" + tokenString;
        String subject = "Reset password";
        emailService.send(
                user.getEmail(),
                subject,
                messageText);

    }

    public void confirm(String tokenString) {
        var token = getToken(tokenString);

        validateToken(token);

        tokenService.setConfirmedAt(tokenString);
        activateUser(token);
    }

    public Token getToken(String tokenString) {
        return tokenService.getToken(tokenString).orElseThrow(
                () -> new IllegalArgumentException("Invalid token")
        );
    }

    private static void validateToken(Token token) {
        if (token.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = token.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token has expired");
        }
    }

    private void activateUser(Token token) {
        var user = this.getUserByEmail(token.getEmail());

        if (user.isEmpty())
            throw new IllegalStateException("Invalid email");
        user.get().setActivated(true);
        if (Objects.equals(user.get().getRole(), USER)) {
            userCompanyRelationService.updateUser((User) user.get());
        } else {
            userCompanyRelationService.updateCompany((Company) user.get());
        }
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
