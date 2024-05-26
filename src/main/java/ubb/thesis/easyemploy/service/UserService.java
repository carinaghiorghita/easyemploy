package ubb.thesis.easyemploy.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserCompanyRelationService userCompanyRelationService;

    @Autowired
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id)
    {
        return this.userRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public Optional<User> getUserByUsername(String username)
    {
        return this.userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email)
    {
        return this.userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
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

    public void deleteUser(User user){
        this.userCompanyRelationService.unfollowAll(user);
        this.deleteById(user.getId());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

}
