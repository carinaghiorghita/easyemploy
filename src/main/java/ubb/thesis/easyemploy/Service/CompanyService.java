package ubb.thesis.easyemploy.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Repository.CompanyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    public static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanys() {
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

    public void saveCompany(Company Company) {
        logger.trace(" > createCompany - method was entered. Company = {}", Company);
        companyRepository.save(Company);
        logger.trace(" > createCompany - method ended. Company = {}", Company);
    }

    @Transactional
    public void updateCompany(Company Company) {
        companyRepository.findById(Company.getId())
                .ifPresent(s -> {
                    s.setUsername(Company.getUsername());
                    //todo rest
                });
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

}
