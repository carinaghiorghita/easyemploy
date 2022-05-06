package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUsername(String username);
    Optional<Company> findByEmail(String email);
}
