package com.system.accounting.controller;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.dto.bank_book.lands.LandSpecifierRequest;
import com.system.accounting.service.print.PrintService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/print")
@RequiredArgsConstructor
@Api(tags = "Печать документов")
public class PrintController {

    private final PrintService printService;

    @GetMapping(value = "/bank_book")
    public ModelAndView printBookRights(LandSpecifierRequest request) {
        return new ModelAndView("bb_rights", printService.printBankBook(request));
    }

    @GetMapping(value = "/bank_book/summary")
    public ModelAndView printBookSummary(BankBookSpecifierRequest request) {
        return new ModelAndView("bb_summary", printService.printBankBookSummary(request));
    }
}
