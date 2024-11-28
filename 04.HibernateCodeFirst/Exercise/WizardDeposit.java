package bg.softuni.exercise;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, name = "first_name")
    private String firstName;

    @Column(length = 60, name = "last_name", nullable = false)
    private String lastName;

    @Column(columnDefinition = "TEXT(1000)")
    private String notes;

    @Basic(optional = false)
    private int age;

    @Column(length = 100, name = "magic_wand_creator")
    private String magicWandCreator;

    @Column(name = "magic_wand_size")
    private short magicWandSize;

    @Column(length = 20, name = "deposit_group")
    private String depositGroup;

    @Column(name = "deposit_start_date")
    private Instant depositStartDate;

    @Column(name = "deposit_amount")
    private Double depositAmount;

    @Column(name = "deposit_interest")
    private Double depositInterest;

    @Column(name = "deposit_charge")
    private Double depositCharge;

    @Column(name = "deposit_expiration_date")
    private Instant depositExpirationDate;

    @Column(name = "is_deposit_expired")
    private boolean isDepositExpired;

    public WizardDeposit() {
    }
}
