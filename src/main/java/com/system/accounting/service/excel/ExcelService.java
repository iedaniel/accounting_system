package com.system.accounting.service.excel;

import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.KozhuunEntity;
import com.system.accounting.service.repository.BankBookRepository;
import com.system.accounting.service.repository.KozhuunRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class ExcelService {

    private final KozhuunRepository kozhuunRepository;
    private final BankBookRepository bankBookRepository;

    public Workbook excel() {
        Workbook wb = new XSSFWorkbook();
        CellStyle firstRowCellStyle = createFirstRowCellStyle(wb);
        CellStyle secondRowStyle = createSecondRowStyle(wb);
        kozhuunRepository.findAll()
                .forEach(e -> getKozhuunSheet(wb, e, secondRowStyle, firstRowCellStyle));
        return wb;
    }

    private void getKozhuunSheet(Workbook wb, KozhuunEntity kozhuun, CellStyle secondRowStyle, CellStyle firstRowCellStyle) {
        Sheet sheet = wb.createSheet(kozhuun.getName());
        setWidth(sheet);
        createFirstRow(sheet, firstRowCellStyle);
        createSecondRow(wb, sheet, secondRowStyle);
        AtomicInteger lastRow = new AtomicInteger(2);
        bankBookRepository.findAllByKozhuunName(kozhuun.getName())
                .forEach(e -> {
                    int rowStart = lastRow.get();
                    Row row = sheet.createRow(lastRow.get());
                    BankBookEntity bankBook = e.getBankBook();
                    row.createCell(0).setCellValue(bankBook.getName());
                    row.createCell(1).setCellValue(e.getVillage());
                    row.createCell(2).setCellValue(e.getMainFio());
                    row.createCell(3).setCellValue(bankBook.getInn());
                    lastRow.getAndIncrement();
                });
    }

    private void createSecondRow(Workbook wb, Sheet sheet, CellStyle secondRowStyle) {
        Row secondRow = sheet.createRow(1);
        secondRow.setHeight((short) 800);
        createGreenHeading(secondRow, 0, "№ л.с.", secondRowStyle);
        createGreenHeading(secondRow, 1, "Село (сумон)", secondRowStyle);
        createGreenHeading(secondRow, 2, "ФИО главного по хозяйству", secondRowStyle);
        createGreenHeading(secondRow, 3, "ИНН", secondRowStyle);
        createGreenHeading(secondRow, 4, "Паспортные данные", secondRowStyle);
        createGreenHeading(secondRow, 5, "Адрес хозяйства", secondRowStyle);
        createGreenHeading(secondRow, 6, "ФИО члена хозяйства", secondRowStyle);
        createGreenHeading(secondRow, 7, "Родство", secondRowStyle);
        createGreenHeading(secondRow, 8, "Кадастровый номер\n участка", secondRowStyle);
        createGreenHeading(secondRow, 9, "Категория земель", secondRowStyle);
        createGreenHeading(secondRow, 10, "Общая площадь\n земли", secondRowStyle);
        createGreenHeading(secondRow, 11, "Наименование", secondRowStyle);
        createGreenHeading(secondRow, 12, "Количество", secondRowStyle);
        createGreenHeading(secondRow, 13, "Год выпуска", secondRowStyle);
        createGreenHeading(secondRow, 14, "Право пользования", secondRowStyle);
        createGreenHeading(secondRow, 15, "Количество", secondRowStyle);
        createGreenHeading(secondRow, 16, "Наименование", secondRowStyle);
        createGreenHeading(secondRow, 17, "Дата изменения \n данных", secondRowStyle);
    }

    private void createGreenHeading(Row secondRow, int column, String value, CellStyle secondRowStyle) {
        Cell cell = secondRow.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(secondRowStyle);
    }

    private CellStyle createSecondRowStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontName("Times new Roman");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private void createFirstRow(Sheet sheet, CellStyle firstRowCellStyle) {
        Row firstRow = createFirstRow(sheet);
        createHeadings(sheet, firstRow, firstRowCellStyle);
    }

    private void createHeadings(Sheet sheet, Row firstRow, CellStyle firstRowStyle) {
        createFirstRowHeading(sheet, firstRow, firstRowStyle, 6, "Члены хозяйства", "G1:H1");
        createFirstRowHeading(sheet, firstRow, firstRowStyle, 8, "Земельные участки", "I1:K1");
        createFirstRowHeading(sheet, firstRow, firstRowStyle, 11, "Сельскохозяйственная техника, оборудование, транспортные средства", "L1:O1");
        createFirstRowHeading(sheet, firstRow, firstRowStyle, 15, "Сельскохозяйственные животные, птицы и пчелы", "P1:Q1");
    }

    private Row createFirstRow(Sheet sheet) {
        Row firstRow = sheet.createRow(0);
        firstRow.setHeight((short) 800);
        return firstRow;
    }

    private void setWidth(Sheet sheet) {
        sheet.setColumnWidth(11, 4400);
        sheet.setColumnWidth(12, 4400);
        sheet.setColumnWidth(13, 4400);
        sheet.setColumnWidth(14, 4400);

        sheet.setColumnWidth(15, 5500);
        sheet.setColumnWidth(16, 5500);

        sheet.setColumnWidth(1, 5500);
        sheet.setColumnWidth(2, 6500);
        sheet.setColumnWidth(4, 5500);
        sheet.setColumnWidth(5, 5500);
        sheet.setColumnWidth(6, 6500);
        sheet.setColumnWidth(7, 5500);
        sheet.setColumnWidth(8, 5500);
        sheet.setColumnWidth(9, 5500);
        sheet.setColumnWidth(10, 5500);
        sheet.setColumnWidth(17, 5500);
    }

    private void createFirstRowHeading(Sheet sheet, Row firstRow, CellStyle firstRowStyle, int column, String title, String ref) {
        Cell cell = firstRow.createCell(column);
        cell.setCellStyle(firstRowStyle);
        cell.setCellValue(title);
        sheet.addMergedRegion(CellRangeAddress.valueOf(ref));
        RegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), CellRangeAddress.valueOf(ref), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), CellRangeAddress.valueOf(ref), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN.getCode(), CellRangeAddress.valueOf(ref), sheet);
    }

    private CellStyle createFirstRowCellStyle(Workbook wb) {
        CellStyle firstRowStyle = wb.createCellStyle();
        firstRowStyle.setAlignment(HorizontalAlignment.CENTER);
        firstRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        firstRowStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        firstRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = wb.createFont();
        font.setFontName("Times new Roman");
        font.setBold(true);
        firstRowStyle.setFont(font);
        return firstRowStyle;
    }
}
