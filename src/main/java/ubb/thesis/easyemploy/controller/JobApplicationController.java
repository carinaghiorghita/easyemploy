package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.thesis.easyemploy.converter.UserConverter;
import ubb.thesis.easyemploy.domain.dto.InterviewDto;
import ubb.thesis.easyemploy.domain.dto.JobApplicationDto;
import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.domain.entities.JobApplication;
import ubb.thesis.easyemploy.domain.entities.JobApplicationKey;
import ubb.thesis.easyemploy.domain.entities.Post;
import ubb.thesis.easyemploy.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    private final CompanyService companyService;
    private final PostService postService;
    private final FileDBService fileDBService;
    private final EmailService emailService;

    @PostMapping(value = "/api/saveApplication")
    public void saveApplication(@RequestBody JobApplicationDto jobApplicationDto, HttpSession httpSession) {
        var post = postService.getPostById(jobApplicationDto.getPostId());

        var userOptional = userService.getUserByUsername((String) httpSession.getAttribute("username"));
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var cv = fileDBService.getCVByUser((Long) httpSession.getAttribute("id"), jobApplicationDto.getPostId());
            var coverLetter = fileDBService.getCLByUser((Long) httpSession.getAttribute("id"), jobApplicationDto.getPostId());

            var year = Integer.parseInt(jobApplicationDto.getDob().substring(0, 4));
            var month = Integer.parseInt(jobApplicationDto.getDob().substring(5, 7));
            var day = Integer.parseInt(jobApplicationDto.getDob().substring(8, 10));

            var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()), user, post, cv, coverLetter, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress(), "", null, "", LocalDateTime.now(), LocalDateTime.now());

            jobApplicationService.save(jobApplication);
        } else {
            throw new IllegalArgumentException("No user with this username");
        }
    }

    @PostMapping(value = "/api/updateApplication")
    public void updateApplication(@RequestBody JobApplicationDto jobApplicationDto, HttpSession httpSession) throws ParseException {
        var post = postService.getPostById(jobApplicationDto.getPostId());
        var userOptional = userService.getUserByUsername((String) httpSession.getAttribute("username"));
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var cv = fileDBService.getCVByUser((Long) httpSession.getAttribute("id"), jobApplicationDto.getPostId());
            var coverLetter = fileDBService.getCLByUser((Long) httpSession.getAttribute("id"), jobApplicationDto.getPostId());

            var year = Integer.parseInt(jobApplicationDto.getDob().substring(0, 4));
            var month = Integer.parseInt(jobApplicationDto.getDob().substring(5, 7));
            var day = Integer.parseInt(jobApplicationDto.getDob().substring(8, 10));

            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            var date = jobApplicationDto.getDateCreated().equals("") ? LocalDateTime.now() : formatter.parse(jobApplicationDto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()), user, post, cv, coverLetter, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress(), jobApplicationDto.getFeedback(), null, "", date, LocalDateTime.now());

            jobApplicationService.update(jobApplication);
        } else {
            throw new IllegalArgumentException("No user with this username");
        }
    }

    @PostMapping(value = "/api/sendFeedback")
    public void sendFeedback(@RequestBody JobApplicationDto jobApplicationDto) throws ParseException {
        var post = postService.getPostById(jobApplicationDto.getPostId());
        var user = userService.getUserById(jobApplicationDto.getUserId());
        var cv = fileDBService.getCVByUser(jobApplicationDto.getUserId(), jobApplicationDto.getPostId());
        var coverLetter = fileDBService.getCLByUser(jobApplicationDto.getUserId(), jobApplicationDto.getPostId());

        var year = Integer.parseInt(jobApplicationDto.getDob().substring(0, 4));
        var month = Integer.parseInt(jobApplicationDto.getDob().substring(5, 7));
        var day = Integer.parseInt(jobApplicationDto.getDob().substring(8, 10));

        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        var date = formatter.parse(jobApplicationDto.getInterviewTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var createdDate = formatter.parse(jobApplicationDto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var editedDate = formatter.parse(jobApplicationDto.getDateLastEdited()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        var jobApplication = new JobApplication(new JobApplicationKey(user.getId(), post.getId()), user, post, cv, coverLetter, jobApplicationDto.getSalutations(), jobApplicationDto.getFirstName(), jobApplicationDto.getLastName(), LocalDate.of(year, month, day), jobApplicationDto.getEmail(), jobApplicationDto.getPhone(), jobApplicationDto.getAddress(), jobApplicationDto.getFeedback(), date, jobApplicationDto.getInterviewLink(), createdDate, editedDate);

        jobApplicationService.update(jobApplication);

        String messageText = "Hello! Your job application has received an update. Check it here: http://localhost:4200/view-post/" + post.getId() + ".";
        String subject = "Application update";
        emailService.send(
                jobApplication.getEmail(),
                subject,
                messageText);

    }

    @GetMapping(value = "/api/getApplication")
    public JobApplicationDto getApplication(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId) {
        //we need to work around returning the pdf files themselves, we only need their ID
        var jobApplication = jobApplicationService.getByIdNoFiles(new JobApplicationKey(userId, postId));
        var date = jobApplication.getInterviewTime() == null ? "" : jobApplication.getInterviewTime().toString();
        var createdDate = jobApplication.getDateCreated().toString();
        var editedDate = jobApplication.getDateLastEdited().toString();

        var dto = new JobApplicationDto(jobApplication.getSalutations(), jobApplication.getFirstName(), jobApplication.getLastName(), jobApplication.getDob().toString(), jobApplication.getEmail(), jobApplication.getPhone(), jobApplication.getAddress(), postId, userId, 0L, 0L, jobApplication.getFeedback(), date, jobApplication.getInterviewLink(), createdDate, editedDate);
        dto.setCvId(fileDBService.getFileId(userId, postId, true));
        dto.setCoverLetterId(fileDBService.getFileId(userId, postId, false));

        return dto;
    }

    @GetMapping(value = "/api/getApplicationPost")
    public JobApplicationDto getApplicationPost(@RequestParam("postId") Long postId, HttpSession httpSession) {
        //we need to work around returning the pdf files themselves, we only need their ID
        var userId = (Long) httpSession.getAttribute("id");
        var jobApplication = jobApplicationService.getByIdNoFiles(new JobApplicationKey(userId, postId));
        var date = jobApplication.getInterviewTime() == null ? "" : jobApplication.getInterviewTime().toString();
        var createdDate = jobApplication.getDateCreated().toString();
        var editedDate = jobApplication.getDateLastEdited().toString();

        var dto = new JobApplicationDto(jobApplication.getSalutations(), jobApplication.getFirstName(), jobApplication.getLastName(), jobApplication.getDob().toString(), jobApplication.getEmail(), jobApplication.getPhone(), jobApplication.getAddress(), postId, userId, 0L, 0L, jobApplication.getFeedback(), date, jobApplication.getInterviewLink(), createdDate, editedDate);
        dto.setCvId(fileDBService.getFileId(userId, postId, true));
        dto.setCoverLetterId(fileDBService.getFileId(userId, postId, false));

        return dto;
    }

    @GetMapping(value = "/api/getInterviewsCurrentUser")
    public List<InterviewDto> getInterviewsCurrentUser(HttpSession httpSession) {
        var role = (String) httpSession.getAttribute("role");
        List<InterviewDto> dtos = new ArrayList<>();

        if (role.equals("USER")) {
            jobApplicationService.getApplicationsWithInterviewsForUser((Long) httpSession.getAttribute("id"))
                    .forEach(jobApplication -> {
                        var dto = new InterviewDto(jobApplication.getFeedback(), jobApplication.getInterviewTime().toString(), jobApplication.getInterviewLink(), jobApplication.getPost().getJobTitle(), jobApplication.getPost().getCompany().getName());
                        dtos.add(dto);
                    });
        } else if (role.equals("COMPANY")) {
            List<Post> postsForCompany = postService.getPostsForCompany(companyService.getCompanyById((Long) httpSession.getAttribute("id")));
            postsForCompany.forEach(post ->
                    jobApplicationService.getApplicationsForPost(post)
                            .forEach(jobApplication -> {
                                var dto = new InterviewDto(jobApplication.getFeedback(), jobApplication.getInterviewTime().toString(), jobApplication.getInterviewLink(), post.getJobTitle(), jobApplication.getSalutations() + " " + jobApplication.getFirstName() + " " + jobApplication.getLastName());
                                dtos.add(dto);
                            })
            );
        } else {
            throw new IllegalArgumentException("Access not allowed");
        }

        return dtos;
    }

    @DeleteMapping(value = "/api/removeApplication")
    public void removeApplication(@RequestParam("userId") Long userId, @RequestParam("postId") Long postId) {
        var key = new JobApplicationKey(userId, postId);

        fileDBService.deleteByUser(userId, postId);
        jobApplicationService.deleteById(key);
    }

    @GetMapping(value = "/api/getApplicants")
    public List<UserExploreDto> getApplicantsForPost(@RequestParam("postId") Long postId) {
        UserConverter userConverter = new UserConverter();
        List<UserExploreDto> users = new ArrayList<>();

        jobApplicationService.getApplicantsForPost(postService.getPostById(postId))
                .forEach(user -> users.add(userConverter.convertModelToDto(user)));

        return users;
    }

    @GetMapping(value = "/api/getExcel/{postId}")
    public ResponseEntity<byte[]> getExcel(@PathVariable String postId) throws IOException {
        var post = postService.getPostById(Long.parseLong(postId));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + post.getJobTitle() + "_Applications.xlsx\"")
                .body(jobApplicationService.getExcel(post));
    }
}
