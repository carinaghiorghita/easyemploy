package ubb.thesis.easyemploy.converter;

import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.domain.entities.User;

import java.util.HashSet;

public class UserConverter implements Converter<User, UserExploreDto> {

    @Override
    public UserExploreDto convertModelToDto(User model) {
        return new UserExploreDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getFirstName(), model.getLastName(), model.getPhoneNumber(), model.getDescription(), model.isActivated(), new HashSet<>());
    }

    @Override
    public User convertDtoToModel(UserExploreDto dto) {
        return new User(dto.getId(),dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.getDescription(), dto.isActivated());
    }
}
