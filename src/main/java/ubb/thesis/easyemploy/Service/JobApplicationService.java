package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Repository.JobApplicationRepository;

import javax.transaction.Transactional;
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

    @Transactional
    public void update(JobApplication jobApplication){
        jobApplicationRepository.findById(jobApplication.getId())
                .ifPresent(p -> {
                    p.setCL(jobApplication.getCL());
                    p.setCV(jobApplication.getCV());
                    p.setAddress(jobApplication.getAddress());
                    p.setDob(jobApplication.getDob());
                    p.setEmail(jobApplication.getEmail());
                    p.setFirstName(jobApplication.getFirstName());
                    p.setLastName(jobApplication.getLastName());
                    p.setPhone(jobApplication.getPhone());
                    p.setSalutations(jobApplication.getSalutations());
                });
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

    @Transactional
    public JobApplication getByIdNoFiles(JobApplicationKey id){
        var application = jobApplicationRepository.getOne(id);
        application.setCL(null);
        application.setCV(null);
        return application;
    }

    public void deleteById(JobApplicationKey key){
        jobApplicationRepository.deleteById(key);
    }
}
