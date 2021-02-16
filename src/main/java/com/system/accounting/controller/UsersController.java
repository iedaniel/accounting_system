package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.TokenResponse;
import com.system.accounting.model.dto.UserLogin;
import com.system.accounting.model.dto.UserProfile;
import com.system.accounting.model.dto.employee.EmployeeCreateRequest;
import com.system.accounting.model.dto.employee.EmployeesResponse;
import com.system.accounting.security.ASAuthorizationFilter;
import com.system.accounting.service.registration.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = "Операции сотрудников")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/registration")
    @ApiOperation(value = "Метод регистрации новых пользователей")
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
    @ApiOperation("Login")
    public BaseResponse<TokenResponse> login(@RequestBody UserLogin userLogin) {
        return new BaseResponse<>(usersService.login(userLogin));
    }

    @GetMapping("/profile")
    @ApiOperation("Даёт всю информацию о текущем пользователе")
    public BaseResponse<UserProfile> profile(HttpServletRequest request) {
        String token = ASAuthorizationFilter.processToken(request.getHeader("Authorization"));
        return new BaseResponse<>(usersService.profile(token));
    }
}
