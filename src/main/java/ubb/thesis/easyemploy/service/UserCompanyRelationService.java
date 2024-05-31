package ubb.thesis.easyemploy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.repository.CompanyRepository;
import ubb.thesis.easyemploy.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCompanyRelationService {

    private static final String USER = "USER";

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void follow(User user, Company company) {
        var follows = user.getFollowedCompanies();
        follows.add(company);
        user.setFollowedCompanies(follows);
        updateUser(user);

        var followers = company.getFollowers();
        followers.add(user);
        company.setFollowers(followers);
        updateCompany(company);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.findById(user.getId())
                .ifPresent(s -> {
                    s.setUsername(user.getUsername());
                    s.setActivated(user.isActivated());
                    s.setPassword(user.getPassword());
                    s.setFirstName(user.getFirstName());
                    s.setLastName(user.getLastName());
                    s.setEmail(user.getEmail());
                    s.setPhoneNumber(user.getPhoneNumber());
                    s.setFollowedCompanies(user.getFollowedCompanies());
                    s.setDescription(user.getDescription());
                });
    }

    @Transactional
    public void updateCompany(Company company) {
        companyRepository.findById(company.getId())
                .ifPresent(s -> {
                    s.setUsername(company.getUsername());
                    s.setActivated(company.isActivated());
                    s.setPassword(company.getPassword());
                    s.setName(company.getName());
                    s.setEmail(company.getEmail());
                    s.setPhoneNumber(company.getPhoneNumber());
                    s.setFollowers(company.getFollowers());
                    s.setDescription(company.getDescription());
                });
    }

    @Transactional
    public void unfollow(User user, Company company) {
        var follows = user.getFollowedCompanies();
        follows.remove(company);
        user.setFollowedCompanies(follows);
        updateUser(user);

        var followers = company.getFollowers();
        followers.remove(user);
        company.setFollowers(followers);
        updateCompany(company);
    }

    @Transactional
    public void unfollowAll(User user) {
        this.companyRepository.findAll().forEach(company -> {
            company.getFollowers().remove(user);
            updateCompany(company);
        });

        user.setFollowedCompanies(new HashSet<>());
        updateUser(user);
    }

    @Transactional
    public void removeFollowers(Company company) {
        this.userRepository.findAll().forEach(user -> {
            user.getFollowedCompanies().remove(company);
            updateUser(user);
        });

        company.setFollowers(new HashSet<>());
        updateCompany(company);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Company> getCompanyByUsername(String username) {
        return companyRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Company> getCompanyByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public Company getCompanyById(Long id) {
        return this.companyRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public User getUserById(Long id) {
        return this.userRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public Optional<BaseUser> getByUsername(String username) {
        var user = getUserByUsername(username);
        if (user.isEmpty()) {
            var company = getCompanyByUsername(username);
            if (company.isEmpty())
                return Optional.empty();
            return Optional.of(company.get());
        }
        return Optional.of(user.get());
    }

    @Transactional
    public void delete(BaseUser user) {
        if (Objects.equals(user.getRole(), USER)) {
            unfollowAll((User) user);
            deleteUserById(user.getId());
        } else {
            removeFollowers((Company) user);
            deleteCompanyById(user.getId());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
