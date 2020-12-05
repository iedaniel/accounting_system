package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kozhuun_id")
    private KozhuunEntity kozhuun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;
}
