package ubb.thesis.easyemploy.domain.dto;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
public class BaseUserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String description;
    private boolean activated;
    private String role;
}
