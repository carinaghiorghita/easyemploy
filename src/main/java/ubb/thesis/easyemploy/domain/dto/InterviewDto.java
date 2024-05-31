package ubb.thesis.easyemploy.domain.dto;

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
