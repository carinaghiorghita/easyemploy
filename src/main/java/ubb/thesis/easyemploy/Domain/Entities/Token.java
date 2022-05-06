package ubb.thesis.easyemploy.Domain.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="token")
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private String email;

    public Token(String token, LocalDateTime createdAt, LocalDateTime expiresAt, String email) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.email = email;
    }

    protected Token() {

    }
}
