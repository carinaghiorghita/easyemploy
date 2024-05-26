package ubb.thesis.easyemploy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.domain.entities.JobApplication;
import ubb.thesis.easyemploy.domain.entities.JobApplicationKey;
import ubb.thesis.easyemploy.domain.entities.Post;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {
    List<JobApplication> findAllByPost(Post post);
}
