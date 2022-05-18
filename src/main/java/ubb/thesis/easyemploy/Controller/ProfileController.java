package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.CompanyConverter;
import ubb.thesis.easyemploy.Converter.UserConverter;
import ubb.thesis.easyemploy.Domain.DTO.CompanyExploreDto;
import ubb.thesis.easyemploy.Domain.DTO.UserExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Service.AuthenticationService;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final CompanyService companyService;
    private final AuthenticationService authenticationService;

    @GetMapping(value ="/api/getUser")
    public UserExploreDto getUser(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        var user = userService.getUserByUsername(username).get();
        var userConverter = new UserConverter();
        return userConverter.convertModelToDto(user);
    }

    @GetMapping(value ="/api/getCompany")
    public CompanyExploreDto getCompany(HttpSession httpSession){
        String username = (String) httpSession.getAttribute("username");
        var company = companyService.getCompanyByUsername(username).get();
        var companyConverter = new CompanyConverter();
        return companyConverter.convertModelToDto(company);
    }

    @DeleteMapping(value="/api/deleteAccount")
    public void deleteAccount(@RequestParam("username") String username){
        var user = this.authenticationService.getUserByUsername(username);

        if(user.get() instanceof User)
            this.userService.deleteUser((User) user.get());
        else
            this.companyService.deleteCompany((Company) user.get());
    }
}
