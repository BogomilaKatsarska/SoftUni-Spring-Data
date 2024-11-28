package bg.softuni.universitySystem;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Column(name = "average_grade")
    private double averageGrade;

    @Basic
    private int attendance;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    public Student() {
        super();
    }

}