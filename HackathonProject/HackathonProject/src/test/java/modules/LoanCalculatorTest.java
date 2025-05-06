package modules;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.LoanCalculatorPom;
import utils.DataUtils;

public class LoanCalculatorTest {
	
	WebDriver driver;
	LoanCalculatorPom pom;
	List<List<Object>> results = new ArrayList<>();
	
	@Parameters({"browser"})
	@BeforeClass
	public void setUp(String b) throws Exception {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setPlatform(Platform.WIN11);
		
		switch (b.toLowerCase()) {
    	case "chrome":
    		cap.setBrowserName("chrome");
    		break;
    	case "edge":
    		cap.setBrowserName("MicrosoftEdge");
    		break;
		}
		
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		driver.get("http://emicalculator.net"); 
		driver.manage().window().maximize();
		
		pom = new LoanCalculatorPom(driver);
		
        List<Object> titles = new ArrayList<Object>();
        
        titles.add("Principal Loan Amount");
        titles.add("Loan APR");
        titles.add("Total Interest Payable");
        titles.add("Total Payment");
        
        results.add(titles);
	}

	@Test(priority=4, dependsOnGroups= {"smoke"}, dataProvider = "loanAmountData", dataProviderClass = DataUtils.class)
	public void test(String loanEmiAmt, String loanInterestAmt, String loanTermAmt, String loanFeesAmt) throws Exception {
		
		pom.clickMenu();
		
		pom.optionClick();
		
		driver.navigate().to("https://emicalculator.net/loan-calculator/");
		
		pom.subOptionClick();
		
		int noOfYears = (int) Math.round(Double.parseDouble(loanTermAmt)) + 1;
        
		pom.sendLoanEmi(loanEmiAmt);
		
        pom.sendLoanInterest(loanInterestAmt);
		
		pom.sendLoanTerm(loanTermAmt);
		
		pom.sendFees(loanFeesAmt);
        
        List<Object> values = new ArrayList<Object>();
        
		String plaText = pom.getPla();
		values.add(plaText);
		System.out.println("Principal Loan Amount: " + plaText + "/-");
		
		String aprText = pom.getApr();
		values.add(aprText);
		System.out.println("Loan APR: " + aprText + "%");
		
		String tipText = pom.getTip();
		values.add(tipText);
		System.out.println("Total Interest Payable: " + tipText + "/-");
		
		String tpText = pom.getTp();
		values.add(tpText);
		System.out.println("Total Payment: " + tpText + "/-");
		
		results.add(values);
		
		List<WebElement> years = pom.getYears();
		Assert.assertEquals(years.size(), noOfYears);
		System.out.println("No.of years: " + years.size());
		
		for (WebElement row : years) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            for (WebElement cell : cells) {
                System.out.print(cell.getText() + " "); 
            }
            
            System.out.println();
        }
		
		pom.pdfClick();
		Thread.sleep(3000);
		File pdfFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.pdf");
		if (pdfFile.exists()) {
			System.out.println("PDF File downloaded successfully!");
		} else {
			System.out.println("PDF File not found.");
		}
		
		pom.excel.click();
		Thread.sleep(3000);
		File excelFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.xlsx");
		if (excelFile.exists()) {
			System.out.println("Excel File downloaded successfully!");
		} else {
			System.out.println("Excel File not found.");
		}
		
		String shareLink = pom.getShareLink();
		
		System.out.println("Share Link: " + shareLink);
		
		System.out.println(results);
		
		DataUtils.writeIntoExcel("src/test/resources/TestData.xlsx", "Loan Amount Calculator Output", results);
	}
	
	@AfterClass
	public void close() {
		driver.quit();
	}
}
