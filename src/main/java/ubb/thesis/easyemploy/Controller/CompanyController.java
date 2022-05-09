package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.CompanyConverter;
import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;
import ubb.thesis.easyemploy.Domain.Validation.CompanyValidator;
import ubb.thesis.easyemploy.Domain.Validation.UserValidator;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

@RestController
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping(value="/api/updateCompany")
    public void updateCompany(@RequestBody CompanyDto companyDto){
        var companyConverter = new CompanyConverter();
        var company = companyConverter.convertDtoToModel(companyDto);

        var companyValidator = new CompanyValidator();
        companyValidator.validateName(company);
        companyValidator.validateUsername(company);
        companyValidator.validatePhoneNumber(company);

        if(companyService.getCompanyByUsername(company.getUsername()).isPresent()
        || userService.getUserByUsername(company.getUsername()).isPresent())
            throw new ValidationException(
                    "Username already exists!"
            );

        this.companyService.updateCompany(company);
    }
}
