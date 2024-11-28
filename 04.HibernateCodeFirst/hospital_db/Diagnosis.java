package bg.softuni.hospital_db;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "diagnoses")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Instant date;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comments;

    @ManyToOne
    private Patient patient;

    public Diagnosis() {}
}
