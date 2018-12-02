package test.task.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Course implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "course")
    private Set<UserCourses> userCourses;


    public Course() {
    }

    public Course(String name, Set<UserCourses> userCourses) {
        this.name = name;
        this.userCourses = userCourses;
    }
}
