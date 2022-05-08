package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Token;
import ubb.thesis.easyemploy.Repository.TokenRepository;

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
        return tokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
