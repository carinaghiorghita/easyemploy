package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@lombok.Setter
@lombok.Getter
@MappedSuperclass
public class BaseUser {
    private static final String UNKNOWN = "UNKNOWN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String email;
    protected String phoneNumber;
    @Column(name = "description", length = 9999)
    protected String description;
    protected String username;
    protected String password;
    protected boolean activated;

    public BaseUser(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.username = builder.username;
        this.password = builder.password;
        this.activated = builder.activated;
        this.description = builder.description;
    }

    protected BaseUser() {
    }

    public String getRole() {
        return UNKNOWN;
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

    public static class Builder {
        protected Long id;
        protected String email;
        protected String phoneNumber;
        protected String description;
        protected String username;
        protected String password;
        protected boolean activated;

        public Builder id (Long id) {
            this.id = id;
            return this;
        }

        public Builder email (String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber (String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder username (String username) {
            this.username = username;
            return this;
        }

        public Builder password (String password) {
            this.password = password;
            return this;
        }

        public Builder activated (boolean activated) {
            this.activated = activated;
            return this;
        }

        public Builder description (String description) {
            this.description = description;
            return this;
        }

        public BaseUser build() {
            return new BaseUser(this);
        }
    }
}
