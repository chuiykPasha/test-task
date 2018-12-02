package test.task.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
public class UserCourses implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private String grade;

    public UserCourses() {
    }

    public UserCourses(User user, Course course, String grade) {
        this.user = user;
        this.course = course;
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }
}
