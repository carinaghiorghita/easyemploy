package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.CompanyExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;

import java.util.HashSet;

public class CompanyConverter implements Converter<Company, CompanyExploreDto> {
    @Override
    public CompanyExploreDto convertModelToDto(Company model) {
        UserConverter userConverter = new UserConverter();

//        Set<UserExploreDto> followers = new HashSet<>();
//        model.getFollowers().forEach(user ->
//                followers.add(userConverter.convertModelToDto(user))
//        );

        return new CompanyExploreDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getName(), model.getPhoneNumber(), model.getDescription(), model.isActivated(), new HashSet<>());
    }

    @Override
    public Company convertDtoToModel(CompanyExploreDto dto) {
        return new Company(dto.getId(),dto.getName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.getDescription(), dto.isActivated());
    }
}
