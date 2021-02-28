package com.system.accounting.service.excel;

import com.system.accounting.model.entity.KozhuunEntity;
import com.system.accounting.service.repository.KozhuunRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExcelService {

    private final KozhuunRepository kozhuunRepository;

    public Workbook excel() {
        Workbook wb = new XSSFWorkbook();
        kozhuunRepository.findAll()
                .forEach(e -> getKozhuunSheet(wb, e));
        return wb;
    }

    private void getKozhuunSheet(Workbook wb, KozhuunEntity kozhuun) {
        Sheet sheet = wb.createSheet(kozhuun.getName());
        setWidth(sheet);
        createFirstRow(wb, sheet);
        createSecondRow(wb, sheet);
    }

    private void createSecondRow(Workbook wb, Sheet sheet) {
        Row secondRow = sheet.createRow(1);
        secondRow.setHeight((short) 800);

        createGreenHeading(wb, secondRow, 0, "№ л.с.");
        createGreenHeading(wb, secondRow, 1, "Село (сумон)");
        createGreenHeading(wb, secondRow, 2, "ФИО главного по хозяйству");
        createGreenHeading(wb, secondRow, 3, "ИНН");
        createGreenHeading(wb, secondRow, 4, "Паспортные данные");
        createGreenHeading(wb, secondRow, 5, "Адрес хозяйства");
        createGreenHeading(wb, secondRow, 6, "ФИО члена хозяйства");
        createGreenHeading(wb, secondRow, 7, "Родство");
        createGreenHeading(wb, secondRow, 8, "Кадастровый номер\n участка");
        createGreenHeading(wb, secondRow, 9, "Категория земель");
        createGreenHeading(wb, secondRow, 10, "Общая площадь\n земли");
        createGreenHeading(wb, secondRow, 11, "Наименование");
        createGreenHeading(wb, secondRow, 12, "Количество");
        createGreenHeading(wb, secondRow, 13, "Год выпуска");
        createGreenHeading(wb, secondRow, 14, "Право пользования");
        createGreenHeading(wb, secondRow, 15, "Количество");
        createGreenHeading(wb, secondRow, 16, "Наименование");
        createGreenHeading(wb, secondRow, 17, "Дата изменения \n данных");
    }

    private void createGreenHeading(Workbook wb, Row secondRow, int column, String value) {
        Cell cell = secondRow.createCell(column);
        cell.setCellValue(value);
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
        cell.setCellStyle(cellStyle);
    }

    private void createFirstRow(Workbook wb, Sheet sheet) {
        Row firstRow = createFirstRow(sheet);
        CellStyle firstRowStyle = createFirstRowCellStyle(wb);
        createHeadings(sheet, firstRow, firstRowStyle);
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
