package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;

import java.text.ParseException;

public class CompanyConverter implements Converter<Company, CompanyDto> {
    @Override
    public CompanyDto convertModelToDto(Company model) {
        return new CompanyDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getName(), model.getPhoneNumber(), model.isActivated());
    }

    @Override
    public Company convertDtoToModel(CompanyDto dto) {
        return new Company(dto.getId(),dto.getName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.isActivated());
    }
}
