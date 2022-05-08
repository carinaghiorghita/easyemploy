package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.TokenDto;
import ubb.thesis.easyemploy.Domain.Entities.Token;

import java.text.ParseException;
import java.time.LocalDateTime;

public class TokenConverter implements Converter<Token, TokenDto> {
    @Override
    public TokenDto convertModelToDto(Token model) {
        return new TokenDto(model.getId(), model.getToken(),model.getEmail());
    }

    @Override
    public Token convertDtoToModel(TokenDto dto) throws ParseException {
        return new Token(dto.getToken(), LocalDateTime.now(), LocalDateTime.now(), dto.getEmail());
    }
}
