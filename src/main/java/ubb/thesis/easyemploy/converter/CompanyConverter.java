package ubb.thesis.easyemploy.converter;

import org.springframework.stereotype.Component;
import ubb.thesis.easyemploy.domain.dto.CompanyExploreDto;
import ubb.thesis.easyemploy.domain.entities.Company;

import java.util.HashSet;

@Component
public class CompanyConverter implements Converter<Company, CompanyExploreDto> {
    @Override
    public CompanyExploreDto convertModelToDto(Company model) {
        return new CompanyExploreDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getName(), model.getPhoneNumber(), model.getDescription(), model.isActivated(), new HashSet<>());
    }

    @Override
    public Company convertDtoToModel(CompanyExploreDto dto) {
        return new Company(dto.getId(),dto.getName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.getDescription(), dto.isActivated());
    }
}
