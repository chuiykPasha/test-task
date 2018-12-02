package test.task.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCourses> userCourses;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void setUserCourses(Set<UserCourses> userCourses) {
        this.userCourses = userCourses;
    }

    public Set<UserCourses> getUserCourses() {
        return userCourses;
    }

    public String getName() {
        return name;
    }
}
