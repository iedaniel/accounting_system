package com.system.accounting.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {

    private boolean success = true;
    private T data;
    private String message;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(String message) {
        this.success = false;
        this.message = message;
    }
}
