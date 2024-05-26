package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.converter.CompanyConverter;
import ubb.thesis.easyemploy.converter.UserConverter;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.service.AuthenticationService;
import ubb.thesis.easyemploy.service.CompanyService;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;
import ubb.thesis.easyemploy.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;

@RestController
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final CompanyService companyService;
    private final AuthenticationService authenticationService;
    private final UserCompanyRelationService userCompanyRelationService;

    @GetMapping(value ="/api/getUser")
    public UserExploreDto getUser(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        if(username==null)
            return new UserExploreDto(0L,"","","","","","","",false,new HashSet<>());
        var user = userService.getUserByUsername(username).get();
        var userConverter = new UserConverter();
        return userConverter.convertModelToDto(user);
    }

    @GetMapping(value ="/api/getUserById")
    public UserExploreDto getUserById(@RequestParam("id") Long id) {
        var user = userService.getUserById(id);
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

    @GetMapping(value ="/api/getCompanyById")
    public CompanyExploreDto getCompanyById(@RequestParam("id") Long id) {
        var company = companyService.getCompanyById(id);
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

    @PostMapping(value="/api/follow")
    public void follow(@RequestBody CompanyExploreDto companyDto, HttpSession httpSession){
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyDto);
        var user = this.userService.getUserById((Long)httpSession.getAttribute("id"));

        this.userCompanyRelationService.follow(user, company);
    }

    @PostMapping(value="/api/unfollow")
    public void unfollow(@RequestBody CompanyExploreDto companyDto, HttpSession httpSession){
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyDto);
        var user = this.userService.getUserById((Long)httpSession.getAttribute("id"));

        this.userCompanyRelationService.unfollow(user, company);
    }

}
