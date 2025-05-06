package utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataUtils {

	@DataProvider(name = "navigationData")
	public Object[][] provideLoanAmountData() throws IOException {
		ExcelUtils excel = new ExcelUtils();
		Object[][] data = excel.readExcelData("src/test/resources/TestData.xlsx", "AssertData");
		for (Object[] row : data) {
			System.out.println(Arrays.toString(row));
		}
		return data;
	}

	@DataProvider(name = "carLoanDataProvider")
	public Object[][] provideCarExcelData() throws IOException {
		ExcelUtils excel = new ExcelUtils();
		Object[][] data = excel.readExcelData("src/test/resources/TestData.xlsx", "CarLoan EMI Inputs");

		for (Object[] row : data) {
			System.out.println(Arrays.toString(row));
		}

		return data;
	}
	
	@DataProvider(name = "homeLoanDataProvider")
    public Object[][] provideHomeLoanAmountData() throws IOException {
        ExcelUtils excel = new ExcelUtils();
        Object[][] data = excel.readExcelData("src/test/resources/TestData.xlsx", "HomeLoan EMI Inputs");
        
        for(Object[] row : data) {
        	System.out.println(Arrays.toString(row));
        }
        
        return data;
    }
	
	@DataProvider(name = "emiLoanData")
    public Object[][] provideEmiLoanAmountData() throws IOException {
        ExcelUtils excel = new ExcelUtils();
        Object[][] data = excel.readExcelData("src/test/resources/TestData.xlsx", "EMILoan Inputs");
        
        for(Object[] row : data) {
        	System.out.println(Arrays.toString(row));
        }
        
        return data;
    }
	
	
	@DataProvider(name = "loanAmountData")
    public Object[][] provideCalculateLoanData() throws IOException {
        ExcelUtils excel = new ExcelUtils();
        Object[][] data = excel.readExcelData("src/test/resources/TestData.xlsx", "LoanAmount Calculator Inputs");
        
        for(Object[] row : data) {
        	System.out.println(Arrays.toString(row));
        }
        
        return data;
    }
	
	@DataProvider(name = "loanTenureData")
    public Object[][] provideLoanTenureData() throws IOException {
        ExcelUtils excel = new ExcelUtils();
        Object[][] data = excel.readExcelData("src/test/resources/TestData.xlsx", "LoanTenure Calculator Inputs");
        
        for(Object[] row : data) {
        	System.out.println(Arrays.toString(row));
        }
        
        return data;
    }
	
	

	public static void writeIntoExcel(String filePath, String sheetName, List<List<Object>> results) throws Exception {
		ExcelUtils excelUtils = new ExcelUtils();
		excelUtils.writeExcelData(filePath, sheetName, results);
	}
}
