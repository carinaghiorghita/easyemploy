package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="\"user\"")
public class User extends BaseUser {
    private String firstName;
    private String lastName;

    @ManyToMany
    private Set<Company> followedCompanies = new HashSet<>();

    public User(String firstName, String lastName, String email, String phoneNumber, String username, String password, String description, boolean activated) {
        super(email, phoneNumber, username, password, description, activated);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Long id, String firstName, String lastName, String email, String phoneNumber, String username, String password, String description, boolean activated) {
        super(id, email, phoneNumber, username, password, description, activated);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {

    }

    @Override
    public String getRole() {
        return "USER";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Company> getFollowedCompanies() {
        return followedCompanies;
    }

    public void setFollowedCompanies(Set<Company> followedCompanies) {
        this.followedCompanies = followedCompanies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
