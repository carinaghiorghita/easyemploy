package ubb.thesis.easyemploy.domain.dto;

import java.util.Set;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
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
