package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="token")
@lombok.Getter
@lombok.Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenString;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private String email;

    public Token(String tokenString, LocalDateTime createdAt, LocalDateTime expiresAt, String email) {
        this.tokenString = tokenString;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.email = email;
    }

    protected Token() {

    }
}
