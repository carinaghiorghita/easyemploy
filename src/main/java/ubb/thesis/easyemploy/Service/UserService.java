package ubb.thesis.easyemploy.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        return result;
    }

    public User getUserById(Long id)
    {
        return this.userRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public void saveUser(User User) {
        logger.trace(" > createUser - method was entered. User = {}", User);
        userRepository.save(User);
        logger.trace(" > createUser - method ended. User = {}", User);
    }

    @Transactional
    public void updateUser(User User) {
        userRepository.findById(User.getId())
                .ifPresent(s -> {
                    s.setUsername(User.getUsername());
                    //todo rest
                });
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
