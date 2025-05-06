package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public Object[][] readExcelData(String filePath, String sheetName) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum();
		int columnCount = sheet.getRow(0).getLastCellNum();

		Object[][] data = new Object[rowCount][columnCount];

		for (int i = 1; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < columnCount; j++) {
				Cell cell = row.getCell(j);
				data[i - 1][j] = getCellValue(cell);
			}
		}
		workbook.close();
		fileInputStream.close();

		return data;
	}

	private String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		default:
			return "";
		}
	}
	
	public void writeExcelData(String filePath, String sheetName, List<List<Object>> results) throws IOException {
    	FileInputStream fileInputStream = new FileInputStream(filePath);
    	Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);
 
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        
        int rowIndex = 0;
        for (List<Object> rowData : results) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
 
            int cellIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = row.getCell(cellIndex);
                if (cell == null) {
                    cell = row.createCell(cellIndex);
                }
 
                cell.setCellValue(cellData.toString());
                cellIndex++;
            }
            rowIndex++;
        }
 
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }
}
