package ubb.thesis.easyemploy.converter;

import ubb.thesis.easyemploy.domain.dto.TokenDto;
import ubb.thesis.easyemploy.domain.entities.Token;

import java.text.ParseException;
import java.time.LocalDateTime;

public class TokenConverter implements Converter<Token, TokenDto> {
    @Override
    public TokenDto convertModelToDto(Token model) {
        return new TokenDto(model.getId(), model.getTokenString(),model.getEmail());
    }

    @Override
    public Token convertDtoToModel(TokenDto dto) throws ParseException {
        return new Token(dto.getToken(), LocalDateTime.now(), LocalDateTime.now(), dto.getEmail());
    }
}
