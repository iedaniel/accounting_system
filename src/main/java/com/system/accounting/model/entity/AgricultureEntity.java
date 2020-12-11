package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "agriculture")
public class AgricultureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agriculture_id_seq")
    @SequenceGenerator(name = "agriculture_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AgricultureEntity parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;
}
