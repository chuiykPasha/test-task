package test.task.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Course implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<UserCourses> userCourses;

    public Course() {
    }

    public Course(String name) {
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
