package ubb.thesis.easyemploy.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.repository.CompanyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    public final UserCompanyRelationService userCompanyRelationService;

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id)
    {
        return this.companyRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public Optional<Company> getCompanyByUsername(String username)
    {
        return this.companyRepository.findByUsername(username);
    }

    public Optional<Company> getCompanyByEmail(String email)
    {
        return this.companyRepository.findByEmail(email);
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(Company company) {
        companyRepository.findById(company.getId())
                .ifPresent(s -> {
                    s.setUsername(company.getUsername());
                    s.setActivated(company.isActivated());
                    s.setPassword(company.getPassword());
                    s.setName(company.getName());
                    s.setEmail(company.getEmail());
                    s.setPhoneNumber(company.getPhoneNumber());
                    s.setFollowers(company.getFollowers());
                    s.setDescription(company.getDescription());
                });
    }

    public void deleteCompany(Company company){
        this.userCompanyRelationService.removeFollowers(company);
        this.deleteById(company.getId());
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
