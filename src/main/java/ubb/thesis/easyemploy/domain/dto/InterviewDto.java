package ubb.thesis.easyemploy.domain.dto;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
public class InterviewDto {
    private String feedback;
    private String interviewTime;
    private String interviewLink;
    private String jobTitle;
    private String interviewer;
}
