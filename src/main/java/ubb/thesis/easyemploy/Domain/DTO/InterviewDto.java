package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InterviewDto {
    private String feedback;
    private String interviewTime;
    private String interviewLink;
    private String jobTitle;
    private String interviewer;
}
