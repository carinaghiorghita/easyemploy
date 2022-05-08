package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.CompanyConverter;
import ubb.thesis.easyemploy.Converter.UserConverter;
import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value="/api/updateUser")
    public void updateUser(@RequestBody UserDto userDto){
        var userConverter = new UserConverter();
        this.userService.updateUser(userConverter.convertDtoToModel(userDto));
    }
}