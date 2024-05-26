package ubb.thesis.easyemploy.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.domain.entities.User;

import java.util.HashSet;

@Service
public class UserCompanyRelationService {
    private final UserService userService;
    private final CompanyService companyService;

    public UserCompanyRelationService(@Lazy UserService userService, @Lazy CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    public void follow(User user, Company company) {
        var follows = user.getFollowedCompanies();
        follows.add(company);
        user.setFollowedCompanies(follows);
        userService.updateUser(user);

        var followers = company.getFollowers();
        followers.add(user);
        company.setFollowers(followers);
        companyService.updateCompany(company);
    }

    public void unfollow(User user, Company company) {
        var follows = user.getFollowedCompanies();
        follows.remove(company);
        user.setFollowedCompanies(follows);
        userService.updateUser(user);

        var followers = company.getFollowers();
        followers.remove(user);
        company.setFollowers(followers);
        companyService.updateCompany(company);
    }

    public void unfollowAll(User user) {
        this.companyService.getAllCompanies().forEach(company -> {
            company.getFollowers().remove(user);
            companyService.updateCompany(company);
        });

        user.setFollowedCompanies(new HashSet<>());
        userService.updateUser(user);
    }

    public void removeFollowers(Company company) {
        this.userService.getAllUsers().forEach(user -> {
            user.getFollowedCompanies().remove(company);
            userService.updateUser(user);
        });

        company.setFollowers(new HashSet<>());
        companyService.updateCompany(company);
    }
}
