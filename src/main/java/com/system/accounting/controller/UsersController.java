package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.TokenResponse;
import com.system.accounting.model.dto.UserLogin;
import com.system.accounting.model.dto.employee.EmployeeCreateRequest;
import com.system.accounting.model.dto.employee.EmployeesResponse;
import com.system.accounting.service.registration.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api("Контроллер, отвечающий за регистрацию пользователей")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/registration")
    @ApiOperation("Метод регистрации новых пользователей")
    public BaseResponse<?> register(@RequestBody EmployeeCreateRequest request) {
        usersService.registration(request);
        return new BaseResponse<>();
    }

    @GetMapping
    @ApiOperation("Метод получения профилей пользователей")
    public BaseResponse<EmployeesResponse> getUsers() {
        return new BaseResponse<>(usersService.getEmployees());
    }

    @PostMapping("/login")
    public BaseResponse<TokenResponse> login(@RequestBody UserLogin userLogin) {
        return new BaseResponse<>(usersService.login(userLogin));
    }
}
