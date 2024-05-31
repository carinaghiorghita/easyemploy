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
import ubb.thesis.easyemploy.service.JobApplicationService;
import ubb.thesis.easyemploy.service.PostService;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ExploreController {

    private final PostService postService;
    private final UserCompanyRelationService userCompanyRelationService;
    private final JobApplicationService jobApplicationService;
    private final UserConverter userConverter;
    private final CompanyConverter companyConverter;

    @GetMapping(value = "/api/getPosts")
    public List<PostExploreDto> getAllPosts() {
        PostConverter postConverter = new PostConverter(jobApplicationService);
        List<PostExploreDto> postsDto = new ArrayList<>();

        postService.getAllPosts().forEach(post ->
                postsDto.add(postConverter.convertModelToDto(post))
        );

        return postsDto;
    }

    @GetMapping(value = "/api/getUsers")
    public List<UserExploreDto> getAllUsers() {
        List<UserExploreDto> userExploreDtos = new ArrayList<>();

        userCompanyRelationService.getAllUsers().stream().filter(BaseUser::isActivated).forEach(user ->
                userExploreDtos.add(userConverter.convertModelToDto(user))
        );

        return userExploreDtos;
    }

    @GetMapping(value = "/api/getUsersExceptCurrent")
    public List<UserExploreDto> getUsersExceptCurrent(HttpSession httpSession) {
        User currentUser = new User();
        if (httpSession.getAttribute("role").equals("USER"))
            currentUser = this.userCompanyRelationService.getUserById((Long) httpSession.getAttribute("id"));

        return getUserExploreDtos(currentUser);
    }

    private List<UserExploreDto> getUserExploreDtos(User currentUser) {
        List<UserExploreDto> userExploreDtos = new ArrayList<>();

        userCompanyRelationService.getAllUsers()
                .stream()
                .filter(user -> user.isActivated() && !user.equals(currentUser))
                .forEach(user -> userExploreDtos.add(userConverter.convertModelToDto(user)));
        return userExploreDtos;
    }

    @GetMapping(value = "/api/getCompanies")
    public List<CompanyExploreDto> getAllCompanies() {
        List<CompanyExploreDto> companyExploreDtos = new ArrayList<>();

        userCompanyRelationService.getAllCompanies().stream().filter(BaseUser::isActivated).forEach(company ->
                companyExploreDtos.add(companyConverter.convertModelToDto(company))
        );

        return companyExploreDtos;
    }
}
