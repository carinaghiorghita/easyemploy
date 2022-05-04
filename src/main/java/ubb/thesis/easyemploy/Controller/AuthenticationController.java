package ubb.thesis.easyemploy.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Service.AuthenticationService;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/api/login")
    public BaseUserDto loginUser(@RequestBody BaseUserDto userDto, HttpSession httpSession) {
        var user = authenticationService.getUserByUsername(userDto.getUsername(), userDto.getPassword());
        if(user.isEmpty())
            return null;
        httpSession.setAttribute("username", userDto.getUsername());
        if(user.get() instanceof User)
            userDto.setRole("USER");
        else
            userDto.setRole("COMPANY");
        userDto.setId(user.get().getId());
        return userDto;
    }

}
