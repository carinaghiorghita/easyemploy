package ubb.thesis.easyemploy.Domain.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "job_application")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private FileDB CV;

    @ManyToOne
    @JoinColumn
    private FileDB CL;

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
