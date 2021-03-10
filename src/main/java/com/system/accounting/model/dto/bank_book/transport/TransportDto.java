package com.system.accounting.model.dto.bank_book.transport;

import com.system.accounting.model.entity.TransportEntity;
import lombok.Getter;

@Getter
public class TransportDto {

    private final Long id;
    private final String name;
    private final Integer year;
    private final Integer num;
    private final String rights;
    private final String creatorName;

    public TransportDto (TransportEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.year = entity.getYear();
        this.num = entity.getNum();
        this.rights = entity.getRights();
        this.creatorName = entity.getCreator().getLogin();
    }
}
