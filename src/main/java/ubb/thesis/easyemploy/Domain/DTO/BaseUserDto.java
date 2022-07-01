package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
