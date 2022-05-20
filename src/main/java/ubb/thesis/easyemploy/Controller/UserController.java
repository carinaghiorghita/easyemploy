package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.CompanyConverter;
import ubb.thesis.easyemploy.Converter.UserConverter;
import ubb.thesis.easyemploy.Domain.DTO.CompanyExploreDto;
import ubb.thesis.easyemploy.Domain.DTO.UserExploreDto;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;
import ubb.thesis.easyemploy.Domain.Validation.UserValidator;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping(value="/api/updateUser")
    public void updateUser(@RequestBody UserExploreDto userExploreDto){
        var userConverter = new UserConverter();
        var user = userConverter.convertDtoToModel(userExploreDto);

        var userValidator = new UserValidator();
        userValidator.validateFirstName(user);
        userValidator.validateLastName(user);
        userValidator.validateUsername(user);
        userValidator.validatePhoneNumber(user);

        if(userService.getUserById(user.getId()).getUsername().equals("")) {
            if (companyService.getCompanyByUsername(user.getUsername()).isPresent()
                    || userService.getUserByUsername(user.getUsername()).isPresent())
                throw new ValidationException(
                        "Username already exists!"
                );
        }

        this.userService.updateUser(user);
    }

    @GetMapping(value = "/api/getFollowedCompanies")
    public List<CompanyExploreDto> getFollowedCompanies(HttpSession httpSession){
        var user = this.userService.getUserById((Long)httpSession.getAttribute("id"));
        var companies = user.getFollowedCompanies();

        List<CompanyExploreDto> companyDtos = new ArrayList<>();
        var companyConverter = new CompanyConverter();
        companies.forEach(company -> companyDtos.add(companyConverter.convertModelToDto(company)));

        return companyDtos;
    }
}
