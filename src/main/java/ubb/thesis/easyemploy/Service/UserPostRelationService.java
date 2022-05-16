package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.util.HashSet;

@Service
public class UserPostRelationService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserService userService;
    private final PostService postService;

    public UserPostRelationService(@Lazy UserService userService, @Lazy PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    public void apply(User user, Post post){
        var jobs = user.getJobsApplied();
        jobs.add(post);
        user.setJobsApplied(jobs);
        userService.updateUser(user);

        var applicants = post.getApplicants();
        applicants.add(user);
        post.setApplicants(applicants);
        postService.updatePost(post);
    }

    public void removeApplication(User user, Post post){
        var jobs = user.getJobsApplied();
        jobs.remove(post);
        user.setJobsApplied(jobs);
        userService.updateUser(user);

        var applicants = post.getApplicants();
        applicants.remove(user);
        post.setApplicants(applicants);
        postService.updatePost(post);
    }

    public void removeAllApplications(User user){
        postService.getAllPosts().forEach(post -> {
            post.getApplicants().remove(user);
            postService.updatePost(post);
        });

        user.setJobsApplied(new HashSet<>());
        userService.updateUser(user);
    }

    public void removeAllApplicants(Post post){
        userService.getAllUsers().forEach(user -> {
            user.getJobsApplied().remove(post);
            userService.updateUser(user);
        });

        post.setApplicants(new HashSet<>());
        postService.updatePost(post);
    }
}
