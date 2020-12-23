package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "household_book")
public class HouseholdBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "household_book_id_seq")
    @SequenceGenerator(name = "household_book_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "village_name")
    private String villageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kozhuun_id")
    private KozhuunEntity kozhuun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "householdBook")
    private List<BankBookEntity> bankBooks;
}
