package com.system.accounting.controller;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.service.print.PrintService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/print")
@RequiredArgsConstructor
@Api(tags = "Печать документов")
public class PrintController {

    private final PrintService printService;

    @GetMapping(value = "/bank_book")
    public ModelAndView printSmth(BankBookSpecifierRequest request) {
        return new ModelAndView("index", printService.printBankBook(request));
    }
}
