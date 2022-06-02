package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.Converter.UserConverter;
import ubb.thesis.easyemploy.Domain.DTO.JobApplicationDto;
import ubb.thesis.easyemploy.Domain.DTO.UserExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Service.FileDBService;
import ubb.thesis.easyemploy.Service.JobApplicationService;
import ubb.thesis.easyemploy.Service.PostService;
import ubb.thesis.easyemploy.Service.UserService;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
        var CV = fileDBService.getCVByUser((Long) httpSession.getAttribute("id"));
        var CL = fileDBService.getCLByUser((Long) httpSession.getAttribute("id"));

        var year = Integer.parseInt(jobApplicationDto.getDob().substring(0,4));
        var month = Integer.parseInt(jobApplicationDto.getDob().substring(5,7));
        var day = Integer.parseInt(jobApplicationDto.getDob().substring(8,10));

        var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()),user,post,CV,CL, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress(), "", null, "");

        jobApplicationService.save(jobApplication);
    }

    @PostMapping(value = "/api/updateApplication")
    public void updateApplication(@RequestBody JobApplicationDto jobApplicationDto, HttpSession httpSession){
        var post = postService.getPostById(jobApplicationDto.getPostId());
        var user = userService.getUserByUsername((String) httpSession.getAttribute("username")).get();
        var CV = fileDBService.getCVByUser((Long) httpSession.getAttribute("id"));
        var CL = fileDBService.getCLByUser((Long) httpSession.getAttribute("id"));

        var year = Integer.parseInt(jobApplicationDto.getDob().substring(0,4));
        var month = Integer.parseInt(jobApplicationDto.getDob().substring(5,7));
        var day = Integer.parseInt(jobApplicationDto.getDob().substring(8,10));

        var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()),user,post,CV,CL, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress(), jobApplicationDto.getFeedback(), null, "");

        jobApplicationService.update(jobApplication);
    }

    @PostMapping(value = "/api/sendFeedback")
    public void sendFeedback(@RequestBody JobApplicationDto jobApplicationDto) throws ParseException {
        var post = postService.getPostById(jobApplicationDto.getPostId());
        var user = userService.getUserById(jobApplicationDto.getUserId());
        var CV = fileDBService.getCVByUser(jobApplicationDto.getUserId());
        var CL = fileDBService.getCLByUser(jobApplicationDto.getUserId());

        var year = Integer.parseInt(jobApplicationDto.getDob().substring(0,4));
        var month = Integer.parseInt(jobApplicationDto.getDob().substring(5,7));
        var day = Integer.parseInt(jobApplicationDto.getDob().substring(8,10));

        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        var date = formatter.parse(jobApplicationDto.getInterviewTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()),user,post,CV,CL, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress(), jobApplicationDto.getFeedback(), date, jobApplicationDto.getInterviewLink());

        jobApplicationService.update(jobApplication);
    }

    @GetMapping(value = "/api/getApplication")
    public JobApplicationDto getApplication(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId){
        //we need to work around returning the pdf files themselves, we only need their ID
        var jobApplication = jobApplicationService.getByIdNoFiles(new JobApplicationKey(userId, postId));
        var date = jobApplication.getInterviewTime()==null ? "" : jobApplication.getInterviewTime().toString();

        var dto = new JobApplicationDto(jobApplication.getSalutations(), jobApplication.getFirstName(), jobApplication.getLastName(), jobApplication.getDob().toString(), jobApplication.getEmail(), jobApplication.getPhone(), jobApplication.getAddress(), postId, userId, 0L, 0L, jobApplication.getFeedback(), date, jobApplication.getInterviewLink());
        dto.setCVId(fileDBService.getFileId(userId,postId,true));
        dto.setCLId(fileDBService.getFileId(userId,postId,false));

        return dto;
    }

    @GetMapping(value = "/api/getApplicationPost")
    public JobApplicationDto getApplicationPost(@RequestParam("postId") Long postId, HttpSession httpSession){
        //we need to work around returning the pdf files themselves, we only need their ID
        var userId = (Long) httpSession.getAttribute("id");
        var jobApplication = jobApplicationService.getByIdNoFiles(new JobApplicationKey(userId, postId));
        var date = jobApplication.getInterviewTime()==null ? "" : jobApplication.getInterviewTime().toString();

        var dto = new JobApplicationDto(jobApplication.getSalutations(), jobApplication.getFirstName(), jobApplication.getLastName(), jobApplication.getDob().toString(), jobApplication.getEmail(), jobApplication.getPhone(), jobApplication.getAddress(), postId, userId, 0L, 0L, jobApplication.getFeedback(), date, jobApplication.getInterviewLink());
        dto.setCVId(fileDBService.getFileId(userId,postId,true));
        dto.setCLId(fileDBService.getFileId(userId,postId,false));

        return dto;
    }

    @DeleteMapping(value = "/api/removeApplication")
    public void removeApplication(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId){
        var key = new JobApplicationKey(userId, postId);

        fileDBService.deleteByUser(userId, postId);
        jobApplicationService.deleteById(key);
    }

    @GetMapping(value = "/api/getApplicants")
    public List<UserExploreDto> getApplicantsForPost(@RequestParam("postId") Long postId){
        UserConverter userConverter = new UserConverter();
        List<UserExploreDto> users = new ArrayList<>();

        jobApplicationService.getApplicantsForPost(postService.getPostById(postId))
                .forEach(user -> users.add(userConverter.convertModelToDto(user)));

        return users;
    }
}
