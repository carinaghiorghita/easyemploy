package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.converter.CompanyConverter;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;
import ubb.thesis.easyemploy.domain.validation.CompanyValidator;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

@RestController
@AllArgsConstructor
public class CompanyController {

    private final UserCompanyRelationService userCompanyRelationService;

    @PostMapping(value = "/api/updateCompany")
    public void updateCompany(@RequestBody CompanyExploreDto companyExploreDto) {
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyExploreDto);

        var companyValidator = new CompanyValidator();
        companyValidator.validateName(company);
        companyValidator.validateUsername(company);
        companyValidator.validatePhoneNumber(company);

        if (userCompanyRelationService.getCompanyById(company.getId()).getUsername().isEmpty()
                && (userCompanyRelationService.getCompanyByUsername(company.getUsername()).isPresent()
                || userCompanyRelationService.getUserByUsername(company.getUsername()).isPresent())) {
            throw new ValidationException("Username already exists!");
        }

        this.userCompanyRelationService.updateCompany(company);
    }
}
