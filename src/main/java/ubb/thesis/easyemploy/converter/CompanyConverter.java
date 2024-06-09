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
        var companyBuilder = new Company.CompanyBuilder();
        return (Company) companyBuilder
                .name(dto.getName())
                .id(dto.getId())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .description(dto.getDescription())
                .activated(dto.isActivated())
                .build();    }
}
