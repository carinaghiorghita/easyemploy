package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.CompanyConverter;
import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Service.CompanyService;

@RestController
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping(value="/api/updateCompany")
    public void updateCompany(@RequestBody CompanyDto companyDto){
        var companyConverter = new CompanyConverter();
        this.companyService.updateCompany(companyConverter.convertDtoToModel(companyDto));
    }
}