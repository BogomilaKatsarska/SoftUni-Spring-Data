package bg.softuni.betting;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Column(name = "squad_number")
    private int squadNumber;

//    private Team team;

    @ManyToOne
    private Position position;

    @Column(name = "is_currently_injured", nullable = false)
    private boolean isCurrentlyInjured;

    public Player() {}
}
