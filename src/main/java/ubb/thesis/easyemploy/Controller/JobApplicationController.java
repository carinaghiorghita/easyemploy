package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.Domain.DTO.JobApplicationDto;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;
import ubb.thesis.easyemploy.Service.FileDBService;
import ubb.thesis.easyemploy.Service.JobApplicationService;
import ubb.thesis.easyemploy.Service.PostService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final UserService userService;
    private final PostService postService;
    private final FileDBService fileDBService;

    @PostMapping(value = "/api/saveApplication")
    public void saveApplication(@RequestBody JobApplicationDto jobApplicationDto, HttpSession httpSession){
        var post = postService.getPostById(jobApplicationDto.getPostId());
        var user = userService.getUserByUsername((String) httpSession.getAttribute("username")).get();
        var CV = fileDBService.getCVByUsername((String) httpSession.getAttribute("username"));
        var CL = fileDBService.getCLByUsername((String) httpSession.getAttribute("username"));

        var year = Integer.parseInt(jobApplicationDto.getDob().substring(0,4));
        var month = Integer.parseInt(jobApplicationDto.getDob().substring(5,7));
        var day = Integer.parseInt(jobApplicationDto.getDob().substring(8,10));

        var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()),user,post,CV,CL, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress());

        jobApplicationService.save(jobApplication);
    }

    @PostMapping(value = "/api/updateApplication")
    public void updateApplication(@RequestBody JobApplicationDto jobApplicationDto, HttpSession httpSession){
        var post = postService.getPostById(jobApplicationDto.getPostId());
        var user = userService.getUserByUsername((String) httpSession.getAttribute("username")).get();
        var CV = fileDBService.getCVByUsername((String) httpSession.getAttribute("username"));
        var CL = fileDBService.getCLByUsername((String) httpSession.getAttribute("username"));

        var year = Integer.parseInt(jobApplicationDto.getDob().substring(0,4));
        var month = Integer.parseInt(jobApplicationDto.getDob().substring(5,7));
        var day = Integer.parseInt(jobApplicationDto.getDob().substring(8,10));

        var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()),user,post,CV,CL, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress());

        jobApplicationService.update(jobApplication);
    }

    @GetMapping(value = "/api/getApplication")
    public JobApplicationDto getApplication(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId){
        var jobApplication = jobApplicationService.getByIdNoFiles(new JobApplicationKey(userId, postId));
        return new JobApplicationDto(jobApplication.getSalutations(), jobApplication.getFirstName(), jobApplication.getLastName(), jobApplication.getDob().toString(), jobApplication.getEmail(), jobApplication.getPhone(), jobApplication.getAddress(), postId, userId);
    }

    @DeleteMapping(value = "/api/removeApplication")
    public void removeApplication(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId, HttpSession httpSession){
        var username = (String) httpSession.getAttribute("username");
        var key = new JobApplicationKey(userId, postId);

        fileDBService.deleteByUsername(username);
        jobApplicationService.deleteById(key);
    }
}
