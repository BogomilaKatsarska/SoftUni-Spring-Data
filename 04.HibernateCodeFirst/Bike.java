package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bikes")
//@DiscriminatorValue("BIKE")
public class Bike extends Vehicle {
    @Column(name="wheel_size")
    private double wheelSize;

    public Bike() {
        super("Bike", 1);
    }

    public double getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(double wheelSize) {
        this.wheelSize = wheelSize;
    }
}
