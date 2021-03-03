package com.system.accounting.service.excel;

import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.KozhuunEntity;
import com.system.accounting.model.entity.TransportEntity;
import com.system.accounting.service.repository.BankBookRepository;
import com.system.accounting.service.repository.BankBookRepository.BankBookWithInfo;
import com.system.accounting.service.repository.KozhuunRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        sheet.createFreezePane(3, 2);

        AtomicInteger lastRow = new AtomicInteger(2);
        Map<BankBookEntity, Boolean> used = new HashMap<>();
        List<BankBookWithInfo> bankBooks = bankBookRepository.findAllByKozhuunName(kozhuun.getName());
        Map<BankBookEntity, List<List<String>>> bankBookToAnimals = new HashMap<>();
        Map<BankBookEntity, List<List<String>>> bankBookToResidents = new HashMap<>();
        Map<BankBookEntity, List<List<String>>> bankbookToLands = new HashMap<>();
        Map<BankBookEntity, List<TransportEntity>> bankBookToTransport = new HashMap<>();
        bankBooks.forEach(e -> {
            BankBookEntity bankBook = e.getBankBook();
            bankBookToAnimals.computeIfAbsent(bankBook, k -> new ArrayList<>()).add(Arrays.asList(e.getAnimalName(), e.getAnimalCount()));
            bankBookToResidents.computeIfAbsent(bankBook, k -> new ArrayList<>()).add(Arrays.asList(e.getResidentName(), e.getRelation()));
            bankbookToLands.computeIfAbsent(bankBook, k -> new ArrayList<>()).add(Arrays.asList(e.getCadastralNumber(), e.getLandCategory(), e.getTotalArea() == null ? "" : e.getTotalArea().toString()));
            bankBookToTransport.computeIfAbsent(bankBook, k -> new ArrayList<>()).add(e.getTransport());
        });
        bankBooks.forEach(e -> {
            int startRow = lastRow.get();
            Row row = sheet.createRow(lastRow.get());
            BankBookEntity bankBook = e.getBankBook();
            if (used.computeIfAbsent(bankBook, __ -> false)) {
                return;
            }
            used.put(bankBook, true);
            row.createCell(0).setCellValue(bankBook.getName());
            row.createCell(1).setCellValue(e.getVillage());
            row.createCell(3).setCellValue(bankBook.getInn());
            row.createCell(5).setCellValue(bankBook.getAddress());
            List<List<String>> animals = bankBookToAnimals.get(bankBook).stream().distinct().collect(Collectors.toList());
            IntStream.range(startRow, startRow + animals.size())
                    .forEach(i -> {
                        Row animalRow;
                        if (i > lastRow.get()) {
                            animalRow = sheet.createRow(lastRow.incrementAndGet());
                        } else {
                            animalRow = sheet.getRow(i);
                        }
                        animalRow.createCell(15).setCellValue(animals.get(i - startRow).get(1));
                        animalRow.createCell(16).setCellValue(animals.get(i - startRow).get(0));
                    });
            List<List<String>> residents = bankBookToResidents.get(bankBook)
                    .stream()
                    .distinct()
                    .sorted(Comparator.comparing(Function.identity(), (l1, l2) -> l1.get(1) == null ? -1 : 1))
                    .collect(Collectors.toList());
            row.createCell(2).setCellValue(residents.get(0).get(0));
            IntStream.range(startRow, startRow + residents.size())
                    .forEach(i -> {
                        Row residentRow;
                        if (i > lastRow.get()) {
                            residentRow = sheet.createRow(lastRow.incrementAndGet());
                        } else {
                            residentRow = sheet.getRow(i);
                        }
                        residentRow.createCell(6).setCellValue(residents.get(i - startRow).get(0));
                        residentRow.createCell(7).setCellValue(residents.get(i - startRow).get(1));
                    });
            List<List<String>> lands = bankbookToLands.get(bankBook).stream().distinct().collect(Collectors.toList());
            IntStream.range(startRow, startRow + lands.size())
                    .forEach(i -> {
                        Row landRow;
                        if (i > lastRow.get()) {
                            landRow = sheet.createRow(lastRow.incrementAndGet());
                        } else {
                            landRow = sheet.getRow(i);
                        }
                        landRow.createCell(8).setCellValue(lands.get(i - startRow).get(0));
                        landRow.createCell(9).setCellValue(lands.get(i - startRow).get(1));
                        landRow.createCell(10).setCellValue(lands.get(i - startRow).get(2));
                    });
            List<TransportEntity> transport = bankBookToTransport.get(bankBook)
                    .stream()
                    .distinct()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            IntStream.range(startRow, startRow + transport.size())
                    .forEach(i -> {
                        Row transportRow;
                        if (i > lastRow.get()) {
                            transportRow = sheet.createRow(lastRow.incrementAndGet());
                        } else {
                            transportRow = sheet.getRow(i);
                        }
                        transportRow.createCell(11).setCellValue(transport.get(i - startRow).getName());
                        transportRow.createCell(12).setCellValue(transport.get(i - startRow).getNum());
                        transportRow.createCell(13).setCellValue(transport.get(i - startRow).getYear());
                        transportRow.createCell(14).setCellValue(transport.get(i - startRow).getRights());
                    });
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
