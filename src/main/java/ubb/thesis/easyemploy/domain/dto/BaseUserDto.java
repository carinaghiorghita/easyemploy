package ubb.thesis.easyemploy.domain.dto;

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
