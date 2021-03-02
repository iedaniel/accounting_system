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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_category_id")
    private LandCategoryEntity landCategory;

    @Column(name = "total_area")
    private Double totalArea;

    @Column(name = "document")
    private String document;

    @Column(name = "document_end_date")
    private LocalDate documentEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "land")
    private List<LandToLandTypeEntity> landTypes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "land")
    private List<LandToAgricultureEntity> agricultures;
}
