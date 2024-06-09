package ubb.thesis.easyemploy.converter;

import org.springframework.stereotype.Component;
import ubb.thesis.easyemploy.domain.dto.UserExploreDto;
import ubb.thesis.easyemploy.domain.entities.User;

import java.util.HashSet;

@Component
public class UserConverter implements Converter<User, UserExploreDto> {

    @Override
    public UserExploreDto convertModelToDto(User model) {
        return new UserExploreDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getFirstName(), model.getLastName(), model.getPhoneNumber(), model.getDescription(), model.isActivated(), new HashSet<>());
    }

    @Override
    public User convertDtoToModel(UserExploreDto dto) {
        var userBuilder = new User.UserBuilder();
        return (User) userBuilder
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .id(dto.getId())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .description(dto.getDescription())
                .activated(dto.isActivated())
                .build();
    }
}
