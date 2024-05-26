package ubb.thesis.easyemploy.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.Token;
import ubb.thesis.easyemploy.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByTokenString(token);
    }

    public void setConfirmedAt(String token) {
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
