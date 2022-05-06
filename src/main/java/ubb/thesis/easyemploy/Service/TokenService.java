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
    private final TokenRepository TokenRepository;

    public void saveToken(Token token) {
        TokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return TokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return TokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
