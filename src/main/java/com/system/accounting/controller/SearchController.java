package com.system.accounting.controller;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.bank_book.BankBooksResponse;
import com.system.accounting.service.search.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
@Api(tags = "Поиск информации")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/bank_books")
    @ApiOperation("Поиск лицевых счетов")
    public BaseResponse<BankBooksResponse> findBankBooks(String query) {
        return new BaseResponse<>(searchService.findBankBooks(query));
    }
}
