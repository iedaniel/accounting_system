package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.bank_book.BankBookCreateRequest;
import com.system.accounting.model.dto.bank_book.BankBookSearchRequest;
import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.dto.bank_book.BankBooksResponse;
import com.system.accounting.model.dto.bank_book.farm_animals.AddFarmAnimalsRequest;
import com.system.accounting.model.dto.bank_book.farm_animals.BookFarmAnimalsResponse;
import com.system.accounting.model.dto.bank_book.lands.agricultures.AddAgriculturesRequest;
import com.system.accounting.model.dto.bank_book.lands.land_types.AddLandTypesRequest;
import com.system.accounting.model.dto.bank_book.lands.LandCreateRequest;
import com.system.accounting.model.dto.bank_book.lands.LandsResponse;
import com.system.accounting.model.dto.bank_book.residents.AddResidentsRequest;
import com.system.accounting.model.dto.bank_book.residents.BookResidentsResponse;
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

    @PostMapping("/residents/add")
    @ApiOperation("Добавление членов хозяйства")
    public BaseResponse<?> addResidents(@RequestBody AddResidentsRequest request) {
        bankBookService.addResidents(request);
        return new BaseResponse<>();
    }

    @PostMapping("/residents")
    @ApiOperation("Отображение всех членов хозяйства")
    public BaseResponse<BookResidentsResponse> residents(@RequestBody BankBookSpecifierRequest request) {
        return new BaseResponse<>(bankBookService.getResidents(request));
    }

    @PostMapping("/lands/add")
    @ApiOperation("Добавить информацию о земельном участке")
    public BaseResponse<?> addLand(@RequestBody LandCreateRequest request) {
        bankBookService.addLand(request);
        return new BaseResponse<>();
    }

    @PostMapping("/lands")
    @ApiOperation("Показ информации о земельных участках")
    public BaseResponse<LandsResponse> getLands(@RequestBody BankBookSpecifierRequest request) {
        return new BaseResponse<>(bankBookService.getLands(request));
    }

    @PostMapping("/land_types/add")
    @ApiOperation("Добавить информацию о земельном участке")
    public BaseResponse<?> addLandTypes(@RequestBody AddLandTypesRequest request) {
        bankBookService.addLandTypes(request);
        return new BaseResponse<>();
    }

    @PostMapping("/agricultures/add")
    @ApiOperation("Добавить информацию о земельных культурах")
    public BaseResponse<?> addAgricultures(@RequestBody AddAgriculturesRequest request) {
        bankBookService.addAgricultures(request);
        return new BaseResponse<>();
    }
}
