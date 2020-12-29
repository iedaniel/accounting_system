package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transport")
public class TransportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transport_id_seq")
    @SequenceGenerator(name = "transport_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @Column(name = "num")
    private Integer num;

    @Column(name = "rights")
    private String rights;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_book_id")
    private BankBookEntity bankBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;
}
