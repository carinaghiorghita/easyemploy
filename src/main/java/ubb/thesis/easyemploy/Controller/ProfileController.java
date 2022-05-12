package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.BaseUserConverter;
import ubb.thesis.easyemploy.Converter.CompanyConverter;
import ubb.thesis.easyemploy.Converter.UserConverter;
import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping(value ="/api/getUser")
    public UserDto getUser(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        var user = userService.getUserByUsername(username).get();
        var userConverter = new UserConverter();
        return userConverter.convertModelToDto(user);
    }

    @GetMapping(value ="/api/getCompany")
    public CompanyDto getCompany(HttpSession httpSession){
        String username = (String) httpSession.getAttribute("username");
        var company = companyService.getCompanyByUsername(username).get();
        var companyConverter = new CompanyConverter();
        return companyConverter.convertModelToDto(company);
    }
}
