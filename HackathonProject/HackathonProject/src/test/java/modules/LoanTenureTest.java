package modules;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.LoanTenurePom;
import utils.DataUtils;

public class LoanTenureTest {
	RemoteWebDriver driver;
	public Logger logger;
	public Properties p;
	String BaseUrl = "https://emicalculator.net/";
	Scanner sc = new Scanner(System.in);
	LoanTenurePom p1;
	List<List<Object>> results = new ArrayList<>();

	@BeforeClass
	@Parameters({ "browser" })
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

		p1 = new LoanTenurePom(driver);

		List<Object> titles = new ArrayList<Object>();

		titles.add("Principal Loan Amount");
		titles.add("Loan APR");
		titles.add("Total Interest Payable");
		titles.add("Total Payment");

		results.add(titles);
	}

	@Test(priority = 4, dependsOnGroups = {
			"smoke" }, dataProvider = "loanTenureData", dataProviderClass = DataUtils.class)
	public void test(String loanAmt, String EMI, String InterestRate, String loanFeesAmt) throws Exception {
		driver.get("http://emicalculator.net");
		driver.manage().window().maximize();

		p1.clickMenu();

		p1.optionClick();

		driver.navigate().to("https://emicalculator.net/loan-calculator/");

		p1.subOptionClick();

		p1.sendLoanEmi(loanAmt);
		p1.sendLoanInterest(EMI);
		p1.sendLoanTerm(InterestRate);
		p1.sendFees(loanFeesAmt);

		List<Object> values = new ArrayList<Object>();

		String loanAmount = p1.getPla();
		values.add(loanAmount);
		System.out.println("Principal Amount: " + loanAmount);
		String aprText = p1.getApr();
		values.add(aprText);
		System.out.println("Loan APR: " + aprText + " %");
		String tipText = p1.getTip();
		values.add(tipText);
		System.out.println("Total Interest payable: " + tipText + " /-");
		String tpText = p1.getTp();
		values.add(tpText);
		System.out.println("Total Payment: " + tpText + " /-");
		results.add(values);

		List<WebElement> years = p1.getYears();
		for (WebElement row : years) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				System.out.println(cell.getText() + " ");
			}
			System.out.println();
		}

		p1.pdfClick();
		Thread.sleep(3000);
		File pdfFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.pdf");
		if (pdfFile.exists()) {
			System.out.println("PDF File downloaded successfully!");
		} else {
			System.out.println("PDF File not found.");
		}

		p1.excel.click();
		Thread.sleep(3000);
		File excelFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.xlsx");
		if (excelFile.exists()) {
			System.out.println("Excel File downloaded successfully!");
		} else {
			System.out.println("Excel File not found.");
		}

		String shareLink = p1.getShareLink();

		System.out.println("Share Link: " + shareLink);

		System.out.println(results);

		DataUtils.writeIntoExcel("src/test/resources/TestData.xlsx", "Loan Tenure Calculator Output", results);

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
