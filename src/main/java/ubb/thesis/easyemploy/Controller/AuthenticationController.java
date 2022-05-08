package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.Converter.BaseUserConverter;
import ubb.thesis.easyemploy.Converter.TokenConverter;
import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Domain.DTO.TokenDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Service.AuthenticationService;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.TokenService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

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
        //todo use converter
        return userDto;
    }

    @GetMapping(value ="/api/getAuthenticatedUser")
    public BaseUserDto getUser(HttpSession httpSession){
        String username= (String) httpSession.getAttribute("username");
        if(authenticationService.getUserByUsername(username).isPresent()) {
            BaseUser baseUser = authenticationService.getUserByUsername(username).get();
            var baseUserConverter = new BaseUserConverter();
            return baseUserConverter.convertModelToDto(baseUser);
        }
        return new BaseUserDto(0L,"","","","",false,"");
    }


    @PostMapping(value = "/api/create-account")
    public void createUser(@RequestBody BaseUserDto userDto) {
        //todo validate data

        if(authenticationService.getUserByEmail(userDto.getEmail()).isPresent())
            throw new RuntimeException("Email already in use!");

        authenticationService.signUp(userDto.getEmail(),userDto.getPassword(),userDto.getRole());
    }

    @GetMapping(path = "/api/confirm-account")
    public TokenDto confirm(@RequestParam("token") String token) {
        authenticationService.confirm(token);

        var tokenObject = tokenService.getToken(token);
        if(tokenObject.isEmpty())
            throw new IllegalArgumentException("Invalid token");
        var tokenConverter = new TokenConverter();
        return tokenConverter.convertModelToDto(tokenObject.get());
    }
}
