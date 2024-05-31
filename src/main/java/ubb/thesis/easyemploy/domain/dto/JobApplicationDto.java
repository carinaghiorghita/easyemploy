package ubb.thesis.easyemploy.domain.dto;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder
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
    private Long cvId;
    private Long coverLetterId;
    private String feedback;
    private String interviewTime;
    private String interviewLink;
    private String dateCreated;
    private String dateLastEdited;
}
