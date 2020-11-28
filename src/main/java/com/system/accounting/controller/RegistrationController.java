package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.employee.EmployeeCreateRequest;
import com.system.accounting.service.registration.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Api("Контроллер, отвечающий за регистрацию пользователей")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ApiOperation("Метод регистрации новых пользователей")
    public BaseResponse<?> register(@RequestBody EmployeeCreateRequest request) {
        registrationService.registration(request);
        return new BaseResponse<>();
    }
}
