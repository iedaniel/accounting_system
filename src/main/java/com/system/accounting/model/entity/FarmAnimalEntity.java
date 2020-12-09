package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "farm_animals")
public class FarmAnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_animals_id_seq")
    @SequenceGenerator(name = "farm_animals_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private FarmAnimalEntity parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;
}
