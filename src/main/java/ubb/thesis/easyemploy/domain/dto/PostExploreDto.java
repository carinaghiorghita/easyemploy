package ubb.thesis.easyemploy.domain.dto;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
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
