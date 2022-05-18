package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Converter.PostConverter;
import ubb.thesis.easyemploy.Domain.DTO.PostExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Validation.PostValidator;
import ubb.thesis.easyemploy.Service.CompanyService;
import ubb.thesis.easyemploy.Service.PostService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping(value = "/api/getPostsForCurrentCompany")
    public List<PostExploreDto> getPostsForCurrentCompany(HttpSession httpSession){
        if(httpSession.getAttribute("role").equals("COMPANY")) {
            PostConverter postConverter = new PostConverter();
            List<PostExploreDto> postsDto = new ArrayList<>();

            postService.getPostsForCompany(companyService.getCompanyById((Long) httpSession.getAttribute("id")))
                    .forEach(post -> postsDto.add(postConverter.convertModelToDto(post)));
            postsDto.sort((p1, p2) -> p2.getDateCreated().compareTo(p1.getDateCreated()));
            return postsDto;
        } else
            throw new IllegalArgumentException("You are not a company!");
    }

    @GetMapping(value = "/api/getPostsFromFollowedCompanies")
    public List<PostExploreDto> getPostsFromFollowedCompanies(HttpSession httpSession){
        if(httpSession.getAttribute("role").equals("USER")) {
            PostConverter postConverter = new PostConverter();
            List<PostExploreDto> postsDto = new ArrayList<>();

            postService.getPostsFromFollowedCompanies(userService.getUserById((Long) httpSession.getAttribute("id")))
                .forEach(post -> postsDto.add(postConverter.convertModelToDto(post)));
            postsDto.sort((p1, p2) -> p2.getDateCreated().compareTo(p1.getDateCreated()));
            return postsDto;
        }else
            throw new IllegalArgumentException("You are not a user!");
    }

    @PostMapping(value = "/api/savePost")
    public void savePost(@RequestBody PostExploreDto postExploreDto, HttpSession httpSession) throws ParseException {
        var post = new Post(postExploreDto.getJobTitle(), postExploreDto.getExperienceLevel(), postExploreDto.getSalary(), postExploreDto.getDescription(), LocalDateTime.now(), null);

        var postValidator = new PostValidator();
        postValidator.validateTitle(post);
        postValidator.validateExperienceLevel(post);
        postValidator.validateSalary(post);

        var company = companyService.getCompanyById((Long) httpSession.getAttribute("id"));
        post.setCompany(company);

        postService.savePost(post);
    }
}
