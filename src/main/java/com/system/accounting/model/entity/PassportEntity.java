package com.system.accounting.model.entity;

import com.system.accounting.model.dto.bank_book.residents.PassportDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@Table(name = "passport")
@NoArgsConstructor
public class PassportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport_id_seq")
    @SequenceGenerator(name = "passport_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_id")
    private String passportId;

    @Column(name = "issuing_authority")
    private String issuingAuthority;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    private ResidentEntity resident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    public PassportEntity(PassportDto passport) {
        this.passportSeries = passport.getPassportSeries();
        this.passportId = passport.getPassportId();
        this.issuingAuthority = passport.getIssuingAuthority();
        this.issueDate = LocalDate.parse(passport.getIssueDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
