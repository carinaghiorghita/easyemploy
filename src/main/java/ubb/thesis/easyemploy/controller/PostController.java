package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.converter.PostConverter;
import ubb.thesis.easyemploy.domain.dto.PostExploreDto;
import ubb.thesis.easyemploy.domain.entities.Post;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.domain.validation.PostValidator;
import ubb.thesis.easyemploy.service.JobApplicationService;
import ubb.thesis.easyemploy.service.PostService;
import ubb.thesis.easyemploy.service.UserCompanyRelationService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserCompanyRelationService userCompanyRelationService;
    private final JobApplicationService jobApplicationService;

    @GetMapping(value = "/api/getPostsForCurrentCompany")
    public List<PostExploreDto> getPostsForCurrentCompany(HttpSession httpSession) {
        if (httpSession.getAttribute("role").equals("COMPANY")) {
            PostConverter postConverter = new PostConverter(jobApplicationService);
            List<PostExploreDto> postsDto = new ArrayList<>();

            processCompanyPosts(postService.getPostsForCompany(userCompanyRelationService.getCompanyById((Long) httpSession.getAttribute("id"))), postsDto, postConverter);
            return postsDto;
        } else {
            throw new IllegalArgumentException("You are not a company!");
        }
    }

    private void processCompanyPosts(List<Post> postService, List<PostExploreDto> postsDto, PostConverter postConverter) {
        postService
                .forEach(post -> postsDto.add(postConverter.convertModelToDto(post)));
        postsDto.sort((p1, p2) -> p2.getDateCreated().compareTo(p1.getDateCreated()));
    }

    @GetMapping(value = "/api/getPostsFromFollowedCompaniesNotApplied")
    public List<PostExploreDto> getPostsFromFollowedCompanies(HttpSession httpSession) {
        if (httpSession.getAttribute("role").equals("USER")) {
            PostConverter postConverter = new PostConverter(jobApplicationService);
            List<PostExploreDto> postsDto = new ArrayList<>();

            User user = userCompanyRelationService.getUserById((Long) httpSession.getAttribute("id"));
            List<Post> jobsApplied = jobApplicationService.getPostsForApplicant(user);

            processFollowedCompaniesPosts(user, jobsApplied, postsDto, postConverter);
            return postsDto;
        } else
            throw new IllegalArgumentException("You are not a user!");
    }

    private void processFollowedCompaniesPosts(User user, List<Post> jobsApplied, List<PostExploreDto> postsDto, PostConverter postConverter) {
        postService.getPostsFromFollowedCompanies(user)
                .stream()
                .filter(post -> !jobsApplied.contains(post))
                .forEach(post -> postsDto.add(postConverter.convertModelToDto(post)));

        postsDto.sort((p1, p2) -> p2.getDateCreated().compareTo(p1.getDateCreated()));
    }

    @GetMapping(value = "/api/getPostsApplied")
    public List<PostExploreDto> getPostsApplied(HttpSession httpSession) {
        if (httpSession.getAttribute("role").equals("USER")) {
            PostConverter postConverter = new PostConverter(jobApplicationService);
            List<PostExploreDto> postsDto = new ArrayList<>();

            processAppliedPosts(httpSession, postsDto, postConverter);
            return postsDto;
        } else
            throw new IllegalArgumentException("You are not a user!");

    }

    private void processAppliedPosts(HttpSession httpSession, List<PostExploreDto> postsDto, PostConverter postConverter) {
        User user = userCompanyRelationService.getUserById((Long) httpSession.getAttribute("id"));
        processCompanyPosts(jobApplicationService.getPostsForApplicant(user), postsDto, postConverter);
    }

    @PostMapping(value = "/api/savePost")
    public void savePost(@RequestBody PostExploreDto postExploreDto, HttpSession httpSession) {
        var post = new Post(postExploreDto.getJobTitle(), postExploreDto.getExperienceLevel(), postExploreDto.getSalary(), postExploreDto.getDescription(), LocalDateTime.now(), LocalDateTime.now(), null);

        var postValidator = new PostValidator();
        postValidator.validateTitle(post);
        postValidator.validateExperienceLevel(post);
        postValidator.validateSalary(post);

        var company = userCompanyRelationService.getCompanyById((Long) httpSession.getAttribute("id"));
        post.setCompany(company);

        postService.savePost(post);
    }

    @PostMapping(value = "/api/updatePost")
    public void updatePost(@RequestBody PostExploreDto postExploreDto) throws ParseException {
        var postConverter = new PostConverter(jobApplicationService);
        var post = postConverter.convertDtoToModel(postExploreDto);

        var postValidator = new PostValidator();
        postValidator.validateTitle(post);
        postValidator.validateExperienceLevel(post);
        postValidator.validateSalary(post);

        post.setDateLastEdited(LocalDateTime.now());

        postService.updatePost(post);
    }

    @DeleteMapping(value = "/api/deletePost")
    public void deletePost(@RequestParam("id") Long id) {
        this.postService.deletePost(this.postService.getPostById(id));
    }

    @GetMapping(value = "/api/getPost")
    public PostExploreDto getPost(@RequestParam("id") Long id) {
        var postConverter = new PostConverter(jobApplicationService);
        var post = this.postService.getPostById(id);

        return postConverter.convertModelToDto(post);
    }
}
