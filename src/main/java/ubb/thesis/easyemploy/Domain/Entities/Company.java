package ubb.thesis.easyemploy.Domain.Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="company")
public class Company extends BaseUser{
    private String name;

//    @OneToMany(mappedBy = "company")
//    private Set<Post> posts = new HashSet<>();

    @ManyToMany
    private Set<User> followers = new HashSet<>();

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

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

//    public Set<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(Set<Post> posts) {
//        this.posts = posts;
//    }

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
