package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostExploreDto {
    private Long id;
    private String jobTitle;
    private String experienceLevel;
    private Integer salary;
    private String description;
    private String dateCreated;
    private CompanyExploreDto company;
    private Integer applicants;
}
