package modules;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.HomeLoanPom;
import utils.DataUtils;

@Listeners(extentreport.ExtentReportManager.class)
public class HomeLoanTest {

	WebDriver driver;
	HomeLoanPom pom;
	List<List<Object>> result;
	JavascriptExecutor js;

	@Parameters({ "browser" })
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
		js = (JavascriptExecutor) driver;
		result = new ArrayList<>();
		pom = new HomeLoanPom(driver);

		List<Object> titles = new ArrayList<Object>();
		titles.add("Loan Emi");
		titles.add("Total Interest Amount");
		titles.add("Total Payment");

		result.add(titles);
	}

	@Test(priority = 2, dependsOnGroups = {
			"smoke" }, dataProvider = "homeLoanDataProvider", dataProviderClass = DataUtils.class)
	public void setData(String loanAmount, String loanInterest, String tenure) throws Exception {
		// Implicit Wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		js.executeScript("arguments[0].value='" + loanAmount + "';", pom.loanTextBox);
		js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.loanTextBox);

		js.executeScript("arguments[0].value='" + loanInterest + "';", pom.interestTextBox);
		js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.interestTextBox);

		js.executeScript("arguments[0].value='" + tenure + "';", pom.loanTermTextBox);
		js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.loanTermTextBox);

		List<Object> values = new ArrayList<Object>();

		values.add(pom.loanEMI.getText());
		values.add(pom.totalInterestPayable.getText());
		values.add(pom.totalPayment.getText());

		result.add(values);

		WebElement pdf = driver.findElement(By.linkText("Download PDF"));
		pdf.click();
		Thread.sleep(3000);
		File pdfFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.pdf");
		if (pdfFile.exists()) {
			System.out.println("PDF File downloaded successfully!");
		} else {
			System.out.println("PDF File not found.");
		}

		DataUtils.writeIntoExcel("src/test/resources/TestData.xlsx", "HomeLoan Output", result);

		WebElement sh = driver.findElement(By.linkText("Download Excel"));
		sh.click();
		Thread.sleep(2000);
		File excelFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.xlsx");
		if (excelFile.exists()) {
			System.out.println("Excel File downloaded successfully!");
		} else {
			System.out.println("Excel File not found.");
		}
		WebElement share = driver.findElement(By.linkText("Share"));
		share.click();
		Thread.sleep(1000);
		WebElement sharedlink = driver.findElement(By.id("sharelink"));
		System.out.println("Share Link : " + sharedlink.getAttribute("value"));

	}

	@AfterClass
	public void closeDriver() {
		driver.quit();
	}
}
