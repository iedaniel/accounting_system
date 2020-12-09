package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.bank_book.BankBookCreateRequest;
import com.system.accounting.model.dto.bank_book.BankBookSearchRequest;
import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.dto.bank_book.BankBooksResponse;
import com.system.accounting.model.dto.bank_book.farm_animals.AddFarmAnimalsRequest;
import com.system.accounting.model.dto.bank_book.farm_animals.BookFarmAnimalsResponse;
import com.system.accounting.service.bank_book.BankBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kozhuuns/household_books/bank_books")
@RequiredArgsConstructor
@Api("Работа с лицевыми счетаим")
public class BankBookController {

    private final BankBookService bankBookService;

    @PostMapping("/create")
    @ApiOperation("Создание лицевого счёта для заданной похозяйственной книги")
    public BaseResponse<?> create(@RequestBody BankBookCreateRequest request) {
        bankBookService.createBankBook(request);
        return new BaseResponse<>();
    }

    @PostMapping
    @ApiOperation("Показ лицевых счетов для заданной похозяйственной книги")
    public BaseResponse<BankBooksResponse> getBankBooksByPath(@RequestBody BankBookSearchRequest request) {
        return new BaseResponse<>(bankBookService.getBankBooks(request));
    }

    @PostMapping("/farm_animals/add")
    @ApiOperation("Закрепление за лицевым счетом хозяйственных животных")
    public BaseResponse<?> addFarmAnimals(@RequestBody AddFarmAnimalsRequest request) {
        bankBookService.addFarmAnimals(request);
        return new BaseResponse<>();
    }

    @PostMapping("/farm_animals")
    @ApiOperation("Показ всех животных для заданного лицевого счета")
    public BaseResponse<BookFarmAnimalsResponse> farmAnimals(@RequestBody BankBookSpecifierRequest request) {
        return new BaseResponse<>(bankBookService.getAnimals(request));
    }
}
