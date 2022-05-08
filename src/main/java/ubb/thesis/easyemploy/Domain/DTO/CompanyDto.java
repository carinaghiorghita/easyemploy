package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private boolean activated;
}
