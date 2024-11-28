package bg.softuni.hospital_db;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "visitation")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Instant date;

    @Basic
    private String comments;

    @ManyToOne() //manyToOne does not have mappedBy
    private Patient patient;

    public Visitation() {}
}
