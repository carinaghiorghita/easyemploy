package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;
import ubb.thesis.easyemploy.Domain.Entities.Post;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {
    List<JobApplication> findAllByPost(Post post);
}
