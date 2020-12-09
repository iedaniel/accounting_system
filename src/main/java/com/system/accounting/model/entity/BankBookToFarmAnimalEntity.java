package com.system.accounting.model.entity;

import com.system.accounting.model.dto.bank_book.farm_animals.AddFarmAnimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "bank_book_to_farm_animals")
@NoArgsConstructor
public class BankBookToFarmAnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_book_to_farm_animals_id_seq")
    @SequenceGenerator(name = "bank_book_to_farm_animals_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_book_id")
    private BankBookEntity bankBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_animal_id")
    private FarmAnimalEntity farmAnimal;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private EmployeeEntity creator;

    public BankBookToFarmAnimalEntity(AddFarmAnimal request,
                                      BankBookEntity bankBook,
                                      Map<String, FarmAnimalEntity> name2FarmAnimalMap) {
        this.bankBook = bankBook;
        this.farmAnimal = name2FarmAnimalMap.get(request.getName());
        this.value = request.getValue();
    }
}
