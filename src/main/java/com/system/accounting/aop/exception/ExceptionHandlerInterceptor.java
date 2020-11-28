package com.system.accounting.aop.exception;

import com.system.accounting.model.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerInterceptor {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public BaseResponse<?> handle(Throwable e) {
        return new BaseResponse<>(e.getMessage());
    }
}
