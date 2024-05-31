package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.converter.BaseUserConverter;
import ubb.thesis.easyemploy.domain.dto.BaseUserDto;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

@RestController
@AllArgsConstructor
public class BaseUserController {

    private final UserCompanyRelationService userCompanyRelationService;
    private final BaseUserConverter baseUserConverter;

    @GetMapping(value = "/api/getUserByEmail")
    public BaseUserDto getUserByEmail(@RequestParam("email") String email) {
        email = email.replace("%2B", "+");
        var user = userCompanyRelationService.getUserByEmail(email);
        if (user.isEmpty()) {
            var company = userCompanyRelationService.getCompanyByEmail(email);
            if (company.isEmpty())
                return new BaseUserDto();
            return baseUserConverter.convertModelToDto(company.get());
        }
        return baseUserConverter.convertModelToDto(user.get());
    }
}
