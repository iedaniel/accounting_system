package com.system.accounting.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {

    private boolean success = true;
    private T payload;
    private String message;

    public BaseResponse(T payload) {
        this.payload = payload;
    }

    public BaseResponse(String message) {
        this.success = false;
        this.message = message;
    }
}
