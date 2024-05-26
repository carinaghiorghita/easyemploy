package ubb.thesis.easyemploy.domain.dto;

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
    private String dateLastEdited;
    private CompanyExploreDto company;
    private Integer applicants;
}
