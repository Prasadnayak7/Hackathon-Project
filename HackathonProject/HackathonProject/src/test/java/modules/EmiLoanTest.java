package modules;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.EmiLoanPom;
import utils.DataUtils;


@Listeners(extentreport.ExtentReportManager.class)
public class EmiLoanTest {

	WebDriver driver;
	EmiLoanPom pom;
	List<List<Object>> result;

	String baseUrl = "https://emicalculator.net";

	@SuppressWarnings("deprecation")
	@BeforeClass
	@Parameters("browser")
	void setUp(String browser) {

		try {
			// Desired capabilities setup
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setPlatform(Platform.WIN11);

			// Set the browser name dynamically
			if (browser.equalsIgnoreCase("chrome")) {
				cap.setBrowserName("chrome");
			} else if (browser.equalsIgnoreCase("edge")) {
				cap.setBrowserName("MicrosoftEdge");
			} else {
				throw new IllegalArgumentException("Browser not supported: " + browser);
			}

			result = new ArrayList<>();

			List<Object> titles = new ArrayList<Object>();
			titles.add("Loan EMI");
			titles.add("Loan APR");
			titles.add("Total Interest Payable");
			titles.add("Total Payment");

			result.add(titles);

			// Connect to Selenium Grid
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

			// Driver configurations
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(baseUrl);
			driver.manage().window().maximize();

		} catch (MalformedURLException e) {
			System.out.println("Invalid Grid URL: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
		pom = new EmiLoanPom(driver);
	}

	@Test(priority = 3, dependsOnGroups = {
			"smoke" }, dataProvider = "emiLoanData", dataProviderClass = DataUtils.class)
	public void TestLoanCalculator(String loanAmount, String interestRate, String duration, String loanfees)
			throws Exception {
		driver.navigate().to("https://emicalculator.net/loan-calculator/");

		// Use methods from the Page Object class
		pom.setLoanAmount(loanAmount, driver);
		pom.setDuration(duration, driver);
		pom.setInterestRate(interestRate, driver);
		pom.setLoanfees(loanfees, driver);

		List<Object> values = new ArrayList<Object>();

		Thread.sleep(1000);
		// Additional assertions and checks can be added here
		String actualLOANEMI = pom.getLOANEMI();
		values.add(actualLOANEMI);

		String actualLOANAPR = pom.getLOANAPR();
		values.add(actualLOANAPR);

		String actualTotalInterestPayable = pom.getTotalInterestPayable();
		values.add(actualTotalInterestPayable);

		String actualTotalPayment = pom.getTotalPayment();
		values.add(actualTotalPayment);

		result.add(values);

		DataUtils.writeIntoExcel("src/test/resources/TestData.xlsx", "EMILoan Output", result);

		pom.pdfClick(driver);

		Thread.sleep(2000);
		File pdfFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.pdf");
		if (pdfFile.exists()) {
			System.out.println("PDF File downloaded successfully!");
		} else {
			System.out.println("PDF File not found.");
		}

		pom.excelClick(driver);
		Thread.sleep(2000);
		File excelFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.xlsx");
		if (excelFile.exists()) {
			System.out.println("Excel File downloaded successfully!");
		} else {
			System.out.println("Excel File not found.");
		}

		String shareLink = pom.getShareLink();

		System.out.println("Share Link: " + shareLink);
	}

	@AfterClass
	void tearDown() {
		driver.quit(); // Close the browser
	}
}
