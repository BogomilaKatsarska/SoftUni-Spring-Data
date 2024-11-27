package bg.softuni.composition;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batches")
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String serialNumber;

    @OneToMany(mappedBy = "batch", targetEntity = Shampoo.class, fetch = FetchType.EAGER)
    private Set<Shampoo> shampoo;

    public Batch() {
        this.shampoo = new HashSet<>();
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Set<Shampoo> getShampoo() {
        return shampoo;
    }

    public void setShampoo(Set<Shampoo> shampoo) { // should I do it setShampoo or addShampoo???
        this.shampoo = shampoo;
    }
    public void addShampoo(Shampoo shampoo) {
        this.shampoo.add(shampoo);
    }
}
