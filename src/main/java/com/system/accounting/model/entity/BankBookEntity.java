package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bank_book")
public class BankBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_book_id_seq")
    @SequenceGenerator(name = "bank_book_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_book_id")
    private HouseholdBookEntity householdBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "closing_reason")
    private String closingReason;

    @Column(name = "address")
    private String address;

    @Column(name = "inn")
    private String inn;

    @Column(name = "additional_info")
    private String additionalInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bankBook")
    private List<BankBookToFarmAnimalEntity> farmAnimals;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bankBook")
    private List<ResidentEntity> residents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bankBook")
    private List<LandEntity> lands;
}
