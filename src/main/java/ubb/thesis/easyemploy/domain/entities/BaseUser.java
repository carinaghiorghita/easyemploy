package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phoneNumber;
    @Column(name="description",length=9999)
    private String description;
    private String username;
    private String password;
    private boolean activated;

    public BaseUser(String email, String phoneNumber, String username, String password, String description, boolean activated) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.activated = activated;
        this.description = description;
    }

    public BaseUser(Long id, String email, String phoneNumber, String username, String password, String description, boolean activated) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.activated = activated;
        this.description = description;
    }

    protected BaseUser(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser baseUser = (BaseUser) o;
        return activated == baseUser.activated && id.equals(baseUser.id) && email.equals(baseUser.email) && phoneNumber.equals(baseUser.phoneNumber) && username.equals(baseUser.username) && password.equals(baseUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber, username, password, activated);
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
                '}';
    }
}
