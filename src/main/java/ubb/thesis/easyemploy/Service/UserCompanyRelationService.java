package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;

@Service
@AllArgsConstructor
public class UserCompanyRelationService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserService userService;
    private final CompanyService companyService;

    public void follow(User user, Company company){
        var follows = user.getFollowedCompanies();
        follows.add(company);
        user.setFollowedCompanies(follows);
        userService.updateUser(user);

        var followers = company.getFollowers();
        followers.add(user);
        company.setFollowers(followers);
        companyService.updateCompany(company);
    }

    public void unfollow(User user, Company company){
        var follows = user.getFollowedCompanies();
        follows.remove(company);
        user.setFollowedCompanies(follows);
        userService.updateUser(user);

        var followers = company.getFollowers();
        followers.remove(user);
        company.setFollowers(followers);
        companyService.updateCompany(company);
    }

}
