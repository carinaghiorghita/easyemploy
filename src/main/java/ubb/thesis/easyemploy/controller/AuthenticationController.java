package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.converter.BaseUserConverter;
import ubb.thesis.easyemploy.converter.TokenConverter;
import ubb.thesis.easyemploy.domain.dto.BaseUserDto;
import ubb.thesis.easyemploy.domain.dto.TokenDto;
import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.domain.validation.BaseUserValidator;
import ubb.thesis.easyemploy.service.AuthenticationService;
import ubb.thesis.easyemploy.service.TokenService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    public static final String COMPANY = "COMPANY";
    public static final String USER = "USER";
    public static final String USERNAME = "username";
    public static final String UNAUTH = "UNAUTH";
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping(value = "/api/login")
    public BaseUserDto loginUser(@RequestBody BaseUserDto userDto, HttpSession httpSession) {
        var user = authenticationService
                .loginByUsername(userDto.getUsername(), userDto.getPassword());
        if (user.isEmpty()) {
            user = authenticationService.loginByEmail(userDto.getUsername(), userDto.getPassword());
            if (user.isEmpty())
                throw new IllegalArgumentException("Username and password do not match!");
        }
        if (!user.get().isActivated())
            throw new IllegalStateException("Please confirm your email before proceeding.");
        var role = user.get() instanceof User ? USER : COMPANY;
        httpSession.setAttribute(USERNAME, user.get().getUsername());
        httpSession.setAttribute("role", role);
        httpSession.setAttribute("id", user.get().getId());
        var userConverter = new BaseUserConverter();
        return userConverter.convertModelToDto(user.get());
    }

    @GetMapping(value = "/api/logout")
    public void logoutUser(HttpSession httpSession) {
        httpSession.removeAttribute(USERNAME);
        httpSession.removeAttribute("id");
        httpSession.setAttribute("role", UNAUTH);
    }

    @GetMapping(value = "/api/getAuthenticatedUser")
    public BaseUserDto getAuthenticatedUser(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute(USERNAME);
        if (authenticationService.getUserByUsername(username).isPresent()) {
            BaseUser baseUser = authenticationService.getUserByUsername(username).get();
            var baseUserConverter = new BaseUserConverter();
            return baseUserConverter.convertModelToDto(baseUser);
        }
        return new BaseUserDto(0L, "", "", "", "", "", false, UNAUTH);
    }

    @GetMapping(value = "/api/getUserRole")
    public BaseUserDto getRole(HttpSession httpSession) {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || role.isEmpty()) {
            httpSession.setAttribute("role", UNAUTH);
            return new BaseUserDto(0L, "", "", "", "", "", false, UNAUTH);
        }
        return new BaseUserDto(0L, "", "", "", "", "", false, role);
    }

    @PostMapping(value = "/api/create-account")
    public void createUser(@RequestBody BaseUserDto userDto) {
        var baseUserConverter = new BaseUserConverter();
        var user = baseUserConverter.convertDtoToModel(userDto);

        var baseUserValidator = new BaseUserValidator();
        baseUserValidator.validateEmail(user);
        baseUserValidator.validatePassword(user.getPassword());

        if (authenticationService.getUserByEmail(userDto.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already in use!");

        authenticationService.signUp(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
    }

    @PostMapping(value = "/api/resend-confirmation")
    public void resendConfirmation(@RequestBody BaseUserDto userDto) {
        Optional<BaseUser> userOptional = authenticationService.getUserByEmail(userDto.getUsername());
        if(userOptional.isPresent()) {
            var user = userOptional.get();
            var role = user instanceof User ? USER : COMPANY;

            authenticationService.deleteUser(user);

            authenticationService.signUp(user.getEmail(), user.getPassword(), role);
        } else {
            throw new IllegalArgumentException("No user exists with this username");
        }
    }

    @GetMapping(value = "/api/reset-password")
    public void resetPassword(@RequestParam("email") String email) {
        var user = authenticationService.getUserByEmail(email);

        if (user.isEmpty())
            throw new IllegalArgumentException("No user with this email exists!");

        authenticationService.resetPassword(user.get());
    }

    @PostMapping(value = "/api/setNewPassword")
    public void setNewPassword(@RequestBody BaseUserDto baseUserDto) {
        var baseUserValidator = new BaseUserValidator();
        baseUserValidator.validatePassword(baseUserDto.getPassword());

        Optional<BaseUser> userOptional = authenticationService.getUserByEmail(baseUserDto.getEmail());
        if(userOptional.isPresent()) {
            var user = userOptional.get();

            user.setPassword(BCrypt.hashpw(baseUserDto.getPassword(), BCrypt.gensalt()));
            authenticationService.updateUser(user);
        } else {
            throw new IllegalArgumentException("No user exists with this email");
        }
    }

    @GetMapping(value = "/api/resend-confirmation-expired")
    public void resendConfirmationExpired(@RequestParam("expiredToken") String expiredToken) {
        var tokenObject = tokenService.getToken(expiredToken);
        if(tokenObject.isPresent()) {
            Optional<BaseUser> userOptional = authenticationService.getUserByEmail(tokenObject.get().getEmail());
            if(userOptional.isPresent()) {
                var user = userOptional.get();
                var role = user instanceof User ? USER : COMPANY;

                authenticationService.deleteUser(user);

                authenticationService.signUp(user.getEmail(), user.getPassword(), role);
            } else {
                throw new IllegalArgumentException("No user exists with this email");
            }
        } else {
            throw new IllegalArgumentException("Token does not exist");
        }
    }

    @GetMapping(path = "/api/confirm-account")
    public TokenDto confirm(@RequestParam("token") String token) {
        authenticationService.confirm(token);

        var tokenObject = tokenService.getToken(token);
        if (tokenObject.isEmpty())
            throw new IllegalArgumentException("Invalid token");
        var tokenConverter = new TokenConverter();
        return tokenConverter.convertModelToDto(tokenObject.get());
    }
}
