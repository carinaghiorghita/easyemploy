package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
