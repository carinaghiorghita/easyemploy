package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Repository.CompanyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {
    public static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    public final UserCompanyRelationService userCompanyRelationService;

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        List<Company> result = companyRepository.findAll();
        return result;
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
        //todo check if this actually works on followers
        this.userCompanyRelationService.removeFollowers(company);
        this.deleteById(company.getId());
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
    public void deleteByEmail(String email) {
        companyRepository.deleteByEmail(email);
    }

}
