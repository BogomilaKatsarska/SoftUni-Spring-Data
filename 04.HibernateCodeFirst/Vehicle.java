package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(name = "vehicles")
//@Inheritance(strategy =  InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) goes with GenerationType.IDENTITY + remove table names
//@DiscriminatorColumn(name = "type") - removes the auto-generated DTYPE
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // Bike and Car cannot have pk=1 at the same time, just one of them may have it
    //GenerationType.IDENTITY(good when InheritanceType.JOINED) - union-subclasses cannot use it, as Bike and Car have sth. in common(i.e. They are Vehicles)
    private long id;

    @Basic
    //@Column(insertable=false, updatable=false); in case of SINGLE_TABLE when having DiscriminatorColumn
    private String type;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    public Vehicle(){}
    public Vehicle(String type, int numberOfSeats) {
        this.type = type;
        this.numberOfSeats = numberOfSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
