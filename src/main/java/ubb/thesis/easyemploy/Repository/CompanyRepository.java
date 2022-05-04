package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.Domain.Entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
