package ubb.thesis.easyemploy.converter;

import org.springframework.stereotype.Component;
import ubb.thesis.easyemploy.domain.dto.BaseUserDto;
import ubb.thesis.easyemploy.domain.entities.BaseUser;

@Component
public class BaseUserConverter implements Converter<BaseUser, BaseUserDto> {

    @Override
    public BaseUserDto convertModelToDto(BaseUser model) {
        return new BaseUserDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getPhoneNumber(), model.getDescription(), model.isActivated(), model.getRole());
    }

    @Override
    public BaseUser convertDtoToModel(BaseUserDto dto) {
        var builder = new BaseUser.Builder();
        return builder
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
