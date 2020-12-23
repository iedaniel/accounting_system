package com.system.accounting.model.entity;

import com.system.accounting.model.dto.bank_book.residents.ResidentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@Table(name = "resident")
@NoArgsConstructor
public class ResidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resident_id_seq")
    @SequenceGenerator(name = "resident_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_book_id")
    private BankBookEntity bankBook;

    @Column(name = "relation")
    private String relation;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "residence_mark")
    private String residenceMark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @OneToOne(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "resident")
    private PassportEntity passport;

    public ResidentEntity(ResidentDto request, BankBookEntity bankBook) {
        this.name = request.getName();
        this.bankBook = bankBook;
        this.relation = request.getRelation();
        this.gender = request.getGender();
        this.birthDate = request.getBirthDate();
        this.residenceMark = request.getResidenceMark();
        this.passport = Optional.ofNullable(request.getPassport())
                .map(PassportEntity::new)
                .orElse(null);
    }
}
