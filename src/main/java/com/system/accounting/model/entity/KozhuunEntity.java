package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "kozhuun")
public class KozhuunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kozhuun_id_seq")
    @SequenceGenerator(name = "kozhuun_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kozhuun", cascade = CascadeType.ALL)
    private List<HouseholdBookEntity> householdBooks;
}
