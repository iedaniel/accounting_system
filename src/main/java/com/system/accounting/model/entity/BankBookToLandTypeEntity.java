package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "land_to_land_type")
@NoArgsConstructor
public class BankBookToLandTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "land_to_land_type_id_seq")
    @SequenceGenerator(name = "land_to_land_type_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_id")
    private LandEntity land;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_type_id")
    private LandTypeEntity landType;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;
}
