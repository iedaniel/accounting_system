package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "land_to_agriculture")
@NoArgsConstructor
public class LandToAgricultureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "land_to_agriculture_id_seq")
    @SequenceGenerator(name = "land_to_agriculture_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_id")
    private LandEntity land;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agriculture_id")
    private AgricultureEntity agriculture;

    @Column(name = "value")
    private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
