package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyExploreDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String description;
    private boolean activated;
    private Set<UserExploreDto> followers;
}
