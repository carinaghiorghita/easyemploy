package ubb.thesis.easyemploy.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Repository.CompanyRepository;
import ubb.thesis.easyemploy.Repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticationService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private CompanyRepository companyRepository;

    public AuthenticationService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public Optional<? extends BaseUser> getUserByUsername(String username, String password){
        var user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            var company = companyRepository.findByUsername(username);
            if(company.isEmpty())
                return Optional.empty();
            return company;
        }
        return user;
    }
}
