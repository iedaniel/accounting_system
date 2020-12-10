package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "land")
@NoArgsConstructor
public class LandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "land_id_seq")
    @SequenceGenerator(name = "land_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_book_id")
    private BankBookEntity bankBook;

    @Column(name = "cadastral_number")
    private String cadastralNumber;

    @Column(name = "land_category")
    private String landCategory;

    @Column(name = "total_area")
    private String totalArea;

    @Column(name = "document")
    private String document;

    @Column(name = "document_end_date")
    private LocalDate documentEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "land")
    private List<BankBookToLandTypeEntity> landTypes;
}
