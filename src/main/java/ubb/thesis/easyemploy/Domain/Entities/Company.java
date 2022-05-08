package ubb.thesis.easyemploy.Domain.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="company")
public class Company extends BaseUser{
    private String name;

    public Company(String name, String email, String phoneNumber, String username, String password, boolean activated) {
        super(email, phoneNumber, username, password, activated);
        this.name = name;
    }

    public Company(Long id, String name, String email, String phoneNumber, String username, String password, boolean activated) {
        super(id, email, phoneNumber, username, password, activated);
        this.name = name;
    }

    public Company(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
