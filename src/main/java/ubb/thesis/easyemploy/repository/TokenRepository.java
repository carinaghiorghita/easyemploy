package ubb.thesis.easyemploy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ubb.thesis.easyemploy.domain.entities.Token;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenString(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token token " +
            "SET token.confirmedAt = ?2 " +
            "WHERE token.tokenString = ?1")
    void updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
