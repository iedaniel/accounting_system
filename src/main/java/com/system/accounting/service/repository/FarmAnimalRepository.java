package com.system.accounting.service.repository;

import com.system.accounting.model.entity.FarmAnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmAnimalRepository extends JpaRepository<FarmAnimalEntity, Long> {

    FarmAnimalEntity findByName(String name);

    @Query(value = "select farm_animals_name as farmAnimal, bbtfa_value as value\n" +
            "from ((select case\n" +
            "                  when parent.name is null then farm_animals.name || ' всего'\n" +
            "                  else farm_animals.name\n" +
            "                  end     as farm_animals_name,\n" +
            "              bbtfa.value as bbtfa_value,\n" +
            "              parent.name as tmp_col,\n" +
            "              2           as ord\n" +
            "       from farm_animals\n" +
            "                join bank_book_to_farm_animals bbtfa on farm_animals.id = bbtfa.farm_animal_id\n" +
            "                join (select farm_animal_id, bank_book_id, max(a.creation_date)\n" +
            "                      from bank_book_to_farm_animals a\n" +
            "                      group by farm_animal_id, bank_book_id) srt\n" +
            "                     on srt.farm_animal_id = bbtfa.farm_animal_id and srt.bank_book_id = bbtfa.bank_book_id and\n" +
            "                        srt.max = bbtfa.creation_date\n" +
            "                join bank_book bb on bbtfa.bank_book_id = bb.id\n" +
            "                left join farm_animals parent on farm_animals.parent_id = parent.id\n" +
            "       where bbtfa.bank_book_id = ?1)\n" +
            "      union\n" +
            "      (select farm_animals_name || ' всего', bbtfa_value, tmp_col, ord from (select parent.name      as farm_animals_name,\n" +
            "              sum(bbtfa.value) as bbtfa_value,\n" +
            "              parent.name      as tmp_col,\n" +
            "              1                as ord\n" +
            "       from farm_animals\n" +
            "                join bank_book_to_farm_animals bbtfa\n" +
            "                     on farm_animals.id = bbtfa.farm_animal_id\n" +
            "                join (select farm_animal_id, bank_book_id, max(a.creation_date)\n" +
            "                      from bank_book_to_farm_animals a\n" +
            "                      group by farm_animal_id, bank_book_id) srt\n" +
            "                     on srt.farm_animal_id = bbtfa.farm_animal_id and srt.bank_book_id = bbtfa.bank_book_id and\n" +
            "                        srt.max = bbtfa.creation_date\n" +
            "                join bank_book bb on bbtfa.bank_book_id = bb.id\n" +
            "                left join farm_animals parent on farm_animals.parent_id = parent.id\n" +
            "       where bbtfa.bank_book_id = ?1\n" +
            "         and parent.name notnull\n" +
            "       group by farm_animals_name) sel2)) sel1\n" +
            "order by tmp_col, ord", nativeQuery = true)
    List<BBSummary> findBBSummary(Long id);

    interface BBSummary {

        String getFarmAnimal();

        Integer getValue();
    }
}
