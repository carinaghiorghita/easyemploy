package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.converter.CompanyConverter;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;
import ubb.thesis.easyemploy.domain.validation.CompanyValidator;
import ubb.thesis.easyemploy.service.CompanyService;
import ubb.thesis.easyemploy.service.UserService;

@RestController
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping(value = "/api/updateCompany")
    public void updateCompany(@RequestBody CompanyExploreDto companyExploreDto) {
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyExploreDto);

        var companyValidator = new CompanyValidator();
        companyValidator.validateName(company);
        companyValidator.validateUsername(company);
        companyValidator.validatePhoneNumber(company);

        if (companyService.getCompanyById(company.getId()).getUsername().isEmpty()
                && (companyService.getCompanyByUsername(company.getUsername()).isPresent()
                || userService.getUserByUsername(company.getUsername()).isPresent())) {
            throw new ValidationException("Username already exists!");
        }

        this.companyService.updateCompany(company);
    }
}
