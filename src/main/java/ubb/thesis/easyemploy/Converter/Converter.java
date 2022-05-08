package ubb.thesis.easyemploy.Converter;

import java.text.ParseException;

public interface Converter<T,U> {
    U convertModelToDto(T model);
    T convertDtoToModel(U dto) throws ParseException;
}
