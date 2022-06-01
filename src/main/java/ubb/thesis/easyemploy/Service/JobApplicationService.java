package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Repository.JobApplicationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobApplicationService {

    @Autowired
    private final JobApplicationRepository jobApplicationRepository;

    public void save(JobApplication jobApplication){
        jobApplicationRepository.save(jobApplication);
    }

    public List<User> getApplicantsForPost(Post post){
        return jobApplicationRepository.findAll()
                .stream()
                .filter(jobApplication -> jobApplication.getPost().getId().equals(post.getId()))
                .map(JobApplication::getUser)
                .collect(Collectors.toList());
    }

    public boolean exists(JobApplicationKey jobApplicationKey){
        return jobApplicationRepository.existsById(jobApplicationKey);
    }
}
