package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@lombok.Setter
@lombok.Getter
@MappedSuperclass
public class BaseUser {
    public static final String UNKNOWN = "UNKNOWN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phoneNumber;
    @Column(name = "description", length = 9999)
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

    protected BaseUser() {
    }

    public String getRole() {
        return UNKNOWN;
    };

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
