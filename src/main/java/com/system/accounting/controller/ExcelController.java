package com.system.accounting.controller;

import com.system.accounting.service.excel.ExcelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
@Api(tags = "Выгрузка excel-файла по всей базе сервиса")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping
    public void excel(HttpServletResponse response) throws IOException {
        Workbook wb = excelService.excel();
        response.setHeader("Content-disposition", "attachment; filename=Kozhuun.xlsx");
        wb.write(response.getOutputStream());
    }
}
