package bg.softuni.exercise;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private StoreLocation storeLocation;

    @Basic
    private Instant date;

    public Sale() {}
}
