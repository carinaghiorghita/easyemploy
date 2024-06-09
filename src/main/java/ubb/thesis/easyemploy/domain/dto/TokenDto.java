package ubb.thesis.easyemploy.domain.dto;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
public class TokenDto {
    private Long id;
    private String token;
    private String email;
}
