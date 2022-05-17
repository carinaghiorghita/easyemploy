package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/api/getPosts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(value = "/api/getPostsForCurrentCompany")
    public List<Post> getPostsForCurrentCompany(HttpSession httpSession){
        if(httpSession.getAttribute("role").equals("COMPANY"))
            return postService.getPostsForCompany((Long) httpSession.getAttribute("id"));
        else
            throw new IllegalArgumentException("You are not a company!");
    }
}
