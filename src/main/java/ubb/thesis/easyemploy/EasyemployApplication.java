package ubb.thesis.easyemploy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Service.UserService;

@SpringBootApplication
public class EasyemployApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(EasyemployApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        var user = new User(1L,"John","Doe","johndoe@gmail.com","0722674528","johndoe","johndoe",false);
//        userService.saveUser(user);
//        System.out.println(userService.getAllUsers());
    }
}
