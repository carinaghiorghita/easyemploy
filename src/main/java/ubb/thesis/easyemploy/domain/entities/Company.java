package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "company")
public class Company extends BaseUser {
    private String name;

    @ManyToMany
    private Set<User> followers = new HashSet<>();

    public Company(String name, String email, String phoneNumber, String username, String password, String description, boolean activated) {
        super(email, phoneNumber, username, password, description, activated);
        this.name = name;
    }

    public Company(Long id, String name, String email, String phoneNumber, String username, String password, String description, boolean activated) {
        super(id, email, phoneNumber, username, password, description, activated);
        this.name = name;
    }

    public Company() {
    }

    @Override
    public String getRole() {
        return "COMPANY";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                '}';
    }
}
