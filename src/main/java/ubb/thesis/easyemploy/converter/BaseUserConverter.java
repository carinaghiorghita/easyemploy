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
        return new BaseUser(dto.getId(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.getDescription(), dto.isActivated());
    }
}
