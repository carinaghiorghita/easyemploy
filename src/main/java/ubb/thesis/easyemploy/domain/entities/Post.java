package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="post")
@lombok.Getter
@lombok.Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String experienceLevel;
    private Integer salary;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastEdited;

    @ManyToOne
    @JoinColumn
    private Company company;

    public Post() {
    }

    public Post(String jobTitle, String experienceLevel, Integer salary, String description, LocalDateTime dateCreated, LocalDateTime dateLastEdited, Company company) {
        this.jobTitle = jobTitle;
        this.experienceLevel = experienceLevel;
        this.salary = salary;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateLastEdited = dateLastEdited;
        this.company = company;
    }


    public Post(Long id, String jobTitle, String experienceLevel, Integer salary, String description, LocalDateTime dateCreated, LocalDateTime dateLastEdited, Company company) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.experienceLevel = experienceLevel;
        this.salary = salary;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateLastEdited = dateLastEdited;
        this.company = company;
    }

}
