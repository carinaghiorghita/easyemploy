package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.Converter.BaseUserConverter;
import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BaseUserController {

    private UserService userService;
    private CompanyService companyService;

    @GetMapping(value = "/api/getUserByEmail")
    public BaseUserDto getUserByEmail(@RequestParam("email") String email) {
        var user = userService.getUserByEmail(email);
        var baseUserConverter = new BaseUserConverter();
        if(user.isEmpty()){
            var company = companyService.getCompanyByEmail(email);
            if(company.isEmpty())
                return new BaseUserDto();
            return baseUserConverter.convertModelToDto(company.get());
        }
        return baseUserConverter.convertModelToDto(user.get());
    }

}
