package bg.softuni.betting;

import jakarta.persistence.*;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @ManyToOne(optional = false)
    private Country country;

    public Town() {}
}
