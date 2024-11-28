package bg.softuni.hospital_db;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    private String address;

    @Basic
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Basic
    private String pictureUrl; // base64 if we want to encode picture, blob

    @Column(name = "has_insurance")
    private boolean hasInsurance;

    @OneToMany(mappedBy = "patient")
    private List<Visitation> visitations;

    @OneToMany
    private Set<Diagnosis> diagnoses;

    @ManyToMany
    private Set<Medicament> medicaments;

    public Patient() {
        this.visitations = new ArrayList<>();
        this.diagnoses = new HashSet<>();
        this.medicaments = new HashSet<>();
    }
}
