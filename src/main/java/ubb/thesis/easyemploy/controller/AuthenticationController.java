package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.converter.BaseUserConverter;
import ubb.thesis.easyemploy.converter.TokenConverter;
import ubb.thesis.easyemploy.domain.dto.BaseUserDto;
import ubb.thesis.easyemploy.domain.dto.TokenDto;
import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.domain.validation.UserValidator;
import ubb.thesis.easyemploy.service.AuthenticationService;
import ubb.thesis.easyemploy.service.TokenService;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private static final String USERNAME = "username";
    private static final String UNAUTH = "UNAUTH";
    @Autowired
    private final AuthenticationService authenticationService;
    private final UserCompanyRelationService userCompanyRelationService;
    private final TokenService tokenService;
    private final BaseUserConverter userConverter;

    @PostMapping(value = "/api/login")
    public BaseUserDto loginUser(@RequestBody BaseUserDto userDto, HttpSession httpSession) {
        var user = authenticationService
                .loginByUsername(userDto.getUsername(), userDto.getPassword())
                .orElse(authenticationService.loginByEmail(userDto.getUsername(), userDto.getPassword())
                        .orElseThrow(() -> new IllegalArgumentException("Username and password do not match!"))
                );
        if (!user.isActivated())
            throw new IllegalStateException("Please confirm your email before proceeding.");
        setSessionAttributes(httpSession, user);
        return userConverter.convertModelToDto(user);
    }

    private static void setSessionAttributes(HttpSession httpSession, BaseUser user) {
        var role = user.getRole();
        httpSession.setAttribute(USERNAME, user.getUsername());
        httpSession.setAttribute("role", role);
        httpSession.setAttribute("id", user.getId());
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
        Optional<User> userOptional = userCompanyRelationService.getUserByUsername(username);
        if (userOptional.isPresent()) {
            BaseUser baseUser = userOptional.get();
            return userConverter.convertModelToDto(baseUser);
        }
        return new BaseUserDto(0L, "", "", "", "", "", false, UNAUTH);
    }

    @GetMapping(value = "/api/getUserRole")
    public BaseUserDto getRole(HttpSession httpSession) {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || role.isEmpty()) {
            httpSession.setAttribute("role", UNAUTH);
            role = UNAUTH;
        }
        return new BaseUserDto(0L, "", "", "", "", "", false, role);
    }

    @PostMapping(value = "/api/create-account")
    public void createUser(@RequestBody BaseUserDto userDto) {
        var user = userConverter.convertDtoToModel(userDto);

        var baseUserValidator = new UserValidator();
        baseUserValidator.validateEmail(user);
        baseUserValidator.validatePassword(user.getPassword());

        if (authenticationService.getUserByEmail(userDto.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already in use!");

        authenticationService.signUp(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
    }

    @PostMapping(value = "/api/resend-confirmation")
    public void resendConfirmation(@RequestBody BaseUserDto userDto) {
        var userOptional = authenticationService.getUserByEmail(userDto.getUsername());
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var role = user.getRole();

            userCompanyRelationService.delete(user);

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
        var baseUserValidator = new UserValidator();
        baseUserValidator.validatePassword(baseUserDto.getPassword());

        var userOptional = authenticationService.getUserByEmail(baseUserDto.getEmail());
        if (userOptional.isPresent()) {
            var user = userOptional.get();

            user.setPassword(BCrypt.hashpw(baseUserDto.getPassword(), BCrypt.gensalt()));
            authenticationService.updateUser(user);
        } else {
            throw new IllegalArgumentException("No user exists with this email");
        }
    }

    @GetMapping(value = "/api/resend-confirmation-expired")
    public void resendConfirmationExpired(@RequestParam("expiredToken") String expiredToken) {
        var tokenObject = authenticationService.getToken(expiredToken);
        var userOptional = authenticationService.getUserByEmail(tokenObject.getEmail());
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var role = user.getRole();

            userCompanyRelationService.delete(user);
            authenticationService.signUp(user.getEmail(), user.getPassword(), role);
        } else {
            throw new IllegalArgumentException("No user exists with this email");
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
