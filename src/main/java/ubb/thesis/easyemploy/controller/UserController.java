package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.converter.CompanyConverter;
import ubb.thesis.easyemploy.converter.UserConverter;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;
import ubb.thesis.easyemploy.domain.validation.UserValidator;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserCompanyRelationService userCompanyRelationService;

    @PostMapping(value = "/api/updateUser")
    public void updateUser(@RequestBody UserExploreDto userExploreDto) {
        var userConverter = new UserConverter();
        var user = userConverter.convertDtoToModel(userExploreDto);

        var userValidator = new UserValidator();
        userValidator.validateFirstName(user);
        userValidator.validateLastName(user);
        userValidator.validateUsername(user);
        userValidator.validatePhoneNumber(user);

        if (userCompanyRelationService.getUserById(user.getId()).getUsername().isEmpty()
                && (userCompanyRelationService.getCompanyByUsername(user.getUsername()).isPresent()
                || userCompanyRelationService.getUserByUsername(user.getUsername()).isPresent())) {
            throw new ValidationException("Username already exists!");
        }

        this.userCompanyRelationService.updateUser(user);
    }

    @GetMapping(value = "/api/getFollowedCompanies")
    public List<CompanyExploreDto> getFollowedCompanies(HttpSession httpSession) {
        var user = this.userCompanyRelationService.getUserById((Long) httpSession.getAttribute("id"));
        var companies = user.getFollowedCompanies();

        List<CompanyExploreDto> companyDtos = new ArrayList<>();
        var companyConverter = new CompanyConverter();
        companies.forEach(company -> companyDtos.add(companyConverter.convertModelToDto(company)));

        return companyDtos;
    }
}
