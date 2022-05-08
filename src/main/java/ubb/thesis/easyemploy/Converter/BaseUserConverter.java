package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.BaseUserDto;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.text.ParseException;

public class BaseUserConverter implements Converter<BaseUser, BaseUserDto> {

    @Override
    public BaseUserDto convertModelToDto(BaseUser model) {
        String role = model instanceof User ? "USER" : "COMPANY";
        return new BaseUserDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getPhoneNumber(), model.isActivated(), role);
    }

    @Override
    public BaseUser convertDtoToModel(BaseUserDto dto) {
        return new BaseUser(dto.getId(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.isActivated());
    }
}
