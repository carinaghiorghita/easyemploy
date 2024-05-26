package ubb.thesis.easyemploy.converter;

import ubb.thesis.easyemploy.domain.dto.BaseUserDto;
import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.entities.User;

public class BaseUserConverter implements Converter<BaseUser, BaseUserDto> {

    @Override
    public BaseUserDto convertModelToDto(BaseUser model) {
        String role = model instanceof User ? "USER" : "COMPANY";
        return new BaseUserDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getPhoneNumber(), model.getDescription(), model.isActivated(), role);
    }

    @Override
    public BaseUser convertDtoToModel(BaseUserDto dto) {
        return new BaseUser(dto.getId(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.getDescription(), dto.isActivated());
    }
}
