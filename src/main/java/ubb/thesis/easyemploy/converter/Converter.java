package ubb.thesis.easyemploy.converter;

import java.text.ParseException;

public interface Converter<T,U> {
    U convertModelToDto(T model);
    T convertDtoToModel(U dto) throws ParseException;
}
