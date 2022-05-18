package ubb.thesis.easyemploy.Domain.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="post")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String experienceLevel;
    private Integer salary;
    private String description;
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn
    private Company company;

    @ManyToMany
    private Set<User> applicants = new HashSet<>();

    public Post() {
    }

    public Post(String jobTitle, String experienceLevel, Integer salary, String description, LocalDateTime dateCreated, Company company) {
        this.jobTitle = jobTitle;
        this.experienceLevel = experienceLevel;
        this.salary = salary;
        this.description = description;
        this.dateCreated = dateCreated;
        this.company = company;
    }

    public Post(String jobTitle, String experienceLevel, Integer salary, String description, LocalDateTime dateCreated, Company company, Set<User> applicants) {
        this.jobTitle = jobTitle;
        this.experienceLevel = experienceLevel;
        this.salary = salary;
        this.description = description;
        this.dateCreated = dateCreated;
        this.company = company;
        this.applicants = applicants;
    }

}
