package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ubb.thesis.easyemploy.Service.PostService;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;
}
