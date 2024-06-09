package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
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

    public Post(Builder builder) {
        this.id = builder.id;
        this.jobTitle = builder.jobTitle;
        this.experienceLevel = builder.experienceLevel;
        this.salary = builder.salary;
        this.description = builder.description;
        this.dateCreated = builder.dateCreated;
        this.dateLastEdited = builder.dateLastEdited;
        this.company = builder.company;
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

    public static final class Builder {
        private Long id;
        private String jobTitle;
        private String experienceLevel;
        private Integer salary;
        private String description;
        private LocalDateTime dateCreated;
        private LocalDateTime dateLastEdited;
        private Company company;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public Builder experienceLevel(String experienceLevel) {
            this.experienceLevel = experienceLevel;
            return this;
        }

        public Builder salary(Integer salary) {
            this.salary = salary;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dateCreated(LocalDateTime dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder dateLastEdited(LocalDateTime dateLastEdited) {
            this.dateLastEdited = dateLastEdited;
            return this;
        }

        public Builder company(Company company) {
            this.company = company;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
