package bg.softuni;

import bg.softuni.entities.Bike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends IdEntity{
    @Column(name = "avg_grade")
    private double avgGrade;

    private Bike bike;

    public Student() {
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }
}
