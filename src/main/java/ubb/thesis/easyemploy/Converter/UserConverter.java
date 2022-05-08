package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.text.ParseException;

public class UserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convertModelToDto(User model) {
        return new UserDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getFirstName(), model.getLastName(), model.getPhoneNumber(), model.isActivated());
    }

    @Override
    public User convertDtoToModel(UserDto dto) {
        return new User(dto.getId(),dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.isActivated());
    }
}
