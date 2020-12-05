package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.household_book.HouseholdBookCreateRequest;
import com.system.accounting.model.dto.household_book.HouseholdBooksResponse;
import com.system.accounting.service.household_books.HouseholdBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kozhuuns/household_books")
@RequiredArgsConstructor
@Api("Работа с похозяйственными книгами")
public class HouseholdBookController {

    private final HouseholdBookService householdBookService;

    @PostMapping("/create")
    @ApiOperation("Создание похозяйственной книги")
    public BaseResponse<?> create(@RequestBody HouseholdBookCreateRequest request) {
        householdBookService.createHouseholdBook(request);
        return new BaseResponse<>();
    }

    @GetMapping
    @ApiOperation("Отображение похозяйственных книг")
    public BaseResponse<HouseholdBooksResponse> getHouseholdBooks() {
        return new BaseResponse<>(householdBookService.getHouseholdBooks());
    }
}
