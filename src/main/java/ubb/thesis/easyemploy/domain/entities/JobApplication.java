package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "job_application")
@Entity
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class JobApplication {
    @EmbeddedId
    JobApplicationKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn
    private FileDB cv;

    @ManyToOne
    @JoinColumn
    private FileDB coverLetter;

    private String salutations;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String phone;
    private String address;

    private String feedback;
    private LocalDateTime interviewTime;
    private String interviewLink;

    private LocalDateTime dateCreated;
    private LocalDateTime dateLastEdited;

}
