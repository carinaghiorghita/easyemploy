package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JobApplicationDto {
    private String salutations;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String phone;
    private String address;
    private Long postId;
    private Long userId;
    private Long CVId;
    private Long CLId;
    private String feedback;
    private String interviewTime;
    private String interviewLink;
}
