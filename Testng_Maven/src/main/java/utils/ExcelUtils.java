package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static List<List<String>> readExcelData(String excelFilePath, String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(excelFilePath);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		List<List<String>> data = new ArrayList<>();
		rowIterator.next();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			List<String> rowData = new ArrayList<>();
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				Cell cell = row.getCell(i);
				if (cell != null) {
					switch (cell.getCellType()) {
					case STRING:
						rowData.add(cell.getStringCellValue());
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							rowData.add(cell.getDateCellValue().toString());
						} else {
							rowData.add(String.format("%.0f", cell.getNumericCellValue()));
						}
						break;
					default:
						rowData.add("");
					}
				} else {
					rowData.add("");
				}
			}
			data.add(rowData);
		}

		workbook.close();
		fis.close();
		return data;
	}
	public static void writeStatusToExcel(String excelFilePath, String sheetName, int rowIndex, int columnIndex, String status) throws IOException {
		FileInputStream fis = new FileInputStream(excelFilePath);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);

		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}

		Cell statusCell = row.createCell(columnIndex);
		statusCell.setCellValue(status);

		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		fos.close();
		workbook.close();
		fis.close();
	}
	public static void closeExcelFile(String excelFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        workbook.close();
        fis.close();
    }
}
