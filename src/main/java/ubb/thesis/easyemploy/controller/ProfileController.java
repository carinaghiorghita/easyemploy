package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.converter.CompanyConverter;
import ubb.thesis.easyemploy.converter.UserConverter;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;

@RestController
@AllArgsConstructor
public class ProfileController {

    private final UserCompanyRelationService userCompanyRelationService;
    private final UserConverter userConverter;

    @GetMapping(value = "/api/getUser")
    public UserExploreDto getUser(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        return userConverter.convertModelToDto(
                userCompanyRelationService.getUserByUsername(username)
                        .orElseThrow(() -> new IllegalArgumentException("No user with this username"))
        );
    }

    @GetMapping(value = "/api/getUserById")
    public UserExploreDto getUserById(@RequestParam("id") Long id) {
        var user = userCompanyRelationService.getUserById(id);
        return userConverter.convertModelToDto(user);
    }

    @GetMapping(value = "/api/getCompany")
    public CompanyExploreDto getCompany(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        var company = userCompanyRelationService.getCompanyByUsername(username);
        if (company.isPresent()) {
            var companyConverter = new CompanyConverter();
            return companyConverter.convertModelToDto(company.get());
        } else {
            throw new IllegalArgumentException("No company with this username");
        }
    }

    @GetMapping(value = "/api/getCompanyById")
    public CompanyExploreDto getCompanyById(@RequestParam("id") Long id) {
        var company = userCompanyRelationService.getCompanyById(id);
        var companyConverter = new CompanyConverter();
        return companyConverter.convertModelToDto(company);
    }

    @DeleteMapping(value = "/api/deleteAccount")
    public void deleteAccount(@RequestParam("username") String username) {
        var user = this.userCompanyRelationService.getByUsername(username);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with this username");
        } else {
            this.userCompanyRelationService.delete(user.get());
        }
    }

    @PostMapping(value = "/api/follow")
    public void follow(@RequestBody CompanyExploreDto companyDto, HttpSession httpSession) {
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyDto);
        var user = this.userCompanyRelationService.getUserById((Long) httpSession.getAttribute("id"));

        this.userCompanyRelationService.follow(user, company);
    }

    @PostMapping(value = "/api/unfollow")
    public void unfollow(@RequestBody CompanyExploreDto companyDto, HttpSession httpSession) {
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyDto);
        var user = this.userCompanyRelationService.getUserById((Long) httpSession.getAttribute("id"));

        this.userCompanyRelationService.unfollow(user, company);
    }

}
