package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.Domain.Entities.JobApplication;
import ubb.thesis.easyemploy.Domain.Entities.JobApplicationKey;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {
}
