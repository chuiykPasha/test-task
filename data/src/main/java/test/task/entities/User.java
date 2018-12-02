package test.task.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCourses> userCourses;

    public User() {
    }

    public User(String name, Set<UserCourses> userCourses) {
        this.name = name;
        this.userCourses = userCourses;
    }
}
