package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ubb.thesis.easyemploy.Domain.Entities.Token;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token token " +
            "SET token.confirmedAt = ?2 " +
            "WHERE token.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
