package bg.softuni.betting;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Enumerated(EnumType.STRING)
    private PositionEnum id;

    @Basic
    private String description;

    public Position() {}

    public Position(PositionEnum id, String description) {
        this.id = id;
        this.description = description;
    }
}
