package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.converter.CompanyConverter;
import ubb.thesis.easyemploy.converter.PostConverter;
import ubb.thesis.easyemploy.converter.UserConverter;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.dto.PostExploreDto;
import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.service.CompanyService;
import ubb.thesis.easyemploy.service.JobApplicationService;
import ubb.thesis.easyemploy.service.PostService;
import ubb.thesis.easyemploy.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ExploreController {
    private final PostService postService;
    private final UserService userService;
    private final CompanyService companyService;
    private final JobApplicationService jobApplicationService;

    @GetMapping(value = "/api/getPosts")
    public List<PostExploreDto> getAllPosts(){
        PostConverter postConverter = new PostConverter(jobApplicationService);
        List<PostExploreDto> postsDto = new ArrayList<>();

        postService.getAllPosts().forEach(post ->
                postsDto.add(postConverter.convertModelToDto(post))
        );

        return postsDto;
    }

    @GetMapping(value = "/api/getUsers")
    public List<UserExploreDto> getAllUsers(){
        UserConverter userConverter = new UserConverter();
        List<UserExploreDto> userExploreDtos = new ArrayList<>();

        userService.getAllUsers().stream().filter(BaseUser::isActivated).forEach(user ->
                userExploreDtos.add(userConverter.convertModelToDto(user))
        );

        return userExploreDtos;
    }

    @GetMapping(value = "/api/getUsersExceptCurrent")
    public List<UserExploreDto> getUsersExceptCurrent(HttpSession httpSession){
        User currentUser = new User();
        if(httpSession.getAttribute("role").equals("USER"))
            currentUser = this.userService.getUserById((Long)httpSession.getAttribute("id"));
        UserConverter userConverter = new UserConverter();
        List<UserExploreDto> userExploreDtos = new ArrayList<>();

        User finalCurrentUser = currentUser;
        userService.getAllUsers().stream().filter(user -> user.isActivated() && !user.equals(finalCurrentUser)).forEach(user ->
                userExploreDtos.add(userConverter.convertModelToDto(user))
        );

        return userExploreDtos;
    }

    @GetMapping(value = "/api/getCompanies")
    public List<CompanyExploreDto> getAllCompanies(){
        CompanyConverter companyConverter = new CompanyConverter();
        List<CompanyExploreDto> companyExploreDtos = new ArrayList<>();

        companyService.getAllCompanies().stream().filter(BaseUser::isActivated).forEach(company ->
                companyExploreDtos.add(companyConverter.convertModelToDto(company))
        );

        return companyExploreDtos;
    }

}
