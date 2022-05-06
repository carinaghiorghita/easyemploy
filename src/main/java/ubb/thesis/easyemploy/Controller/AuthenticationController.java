package ubb.thesis.easyemploy.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Service.AuthenticationService;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final CompanyService companyService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    UserService userService,
                                    CompanyService companyService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @PostMapping(value = "/api/login")
    public BaseUserDto loginUser(@RequestBody BaseUserDto userDto, HttpSession httpSession) {
        var user = authenticationService.getUser(userDto.getUsername(), userDto.getPassword());
        if (user.isEmpty())
            return null;
        httpSession.setAttribute("username", userDto.getUsername());
        if (user.get() instanceof User)
            userDto.setRole("USER");
        else
            userDto.setRole("COMPANY");
        userDto.setId(user.get().getId());
        //todo set email maybe? idk
        return userDto;
    }

    @PostMapping(value = "/api/create-account")
    public void createUser(@RequestBody BaseUserDto userDto, HttpSession httpSession) {
        //todo validate data

        if(authenticationService.getUserByEmail(userDto.getEmail()).isPresent())
            throw new RuntimeException("Email already in use!");

        authenticationService.signUp(userDto.getEmail(),userDto.getPassword(),userDto.getRole());
    }

    @GetMapping(path = "/api/confirm-account")
    public void confirm(@RequestParam("token") String token) {
        authenticationService.confirm(token);
    }
}
