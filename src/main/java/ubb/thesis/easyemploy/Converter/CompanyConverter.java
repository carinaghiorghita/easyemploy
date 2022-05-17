package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class CompanyConverter implements Converter<Company, CompanyDto> {
    @Override
    public CompanyDto convertModelToDto(Company model) {
        UserConverter userConverter = new UserConverter();

//        Set<UserDto> followers = new HashSet<>();
//        model.getFollowers().forEach(user ->
//                followers.add(userConverter.convertModelToDto(user))
//        );

        return new CompanyDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getName(), model.getPhoneNumber(), model.isActivated(), new HashSet<>());
    }

    @Override
    public Company convertDtoToModel(CompanyDto dto) {
        return new Company(dto.getId(),dto.getName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.isActivated());
    }
}
