package smokeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.DataUtils;

@Listeners(extentreport.ExtentReportManager.class)
public class NavigationBar {

	WebDriver driver;
	WebDriverWait myWait;

	@BeforeClass
	@Parameters("browser")
	public void init(String b) throws Exception {

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
		driver.get("https://emicalculator.net/");
		driver.manage().window().maximize();

		myWait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	@Test(groups = { "smoke" }, dataProvider = "navigationData", dataProviderClass = DataUtils.class)
	public void execute(String homePage, String homeLoan, String loanCalci) {

		String homePageText1 = myWait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/main/article/div[1]/h1")))
				.getText();
		System.out.println(homePageText1);
		Assert.assertEquals(homePageText1, homePage, "Page Navigation UnSuccessful");

		WebElement homeDropdown = driver.findElement(By.partialLinkText("LOAN CALCULATORS"));
		homeDropdown.click();

		WebElement homeLoanOption = myWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Home Loan EMI Calculator")));
		homeLoanOption.click();

		driver.navigate().to("https://emicalculator.net/home-loan-emi-calculator/");

		String actualtext2 = myWait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/main/article/div[1]/h1")))
				.getText();
		System.out.println(actualtext2);
		Assert.assertEquals(actualtext2, homeLoan, "Page Navigation UnSuccessful");

		WebElement homeLoanDropdown = driver.findElement(By.partialLinkText("LOAN CALCULATORS"));
		homeLoanDropdown.click();

		WebElement loanCalculatorOption = myWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Loan Calculator")));
		loanCalculatorOption.click();

		driver.navigate().to("https://emicalculator.net/loan-calculator/");

		String actualtext3 = myWait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/main/article/div[1]/h1")))
				.getText();
		System.out.println(actualtext3);
		Assert.assertEquals(actualtext3, loanCalci, "Page Navigation UnSuccessful");

		WebElement homeLogo = driver.findElement(By.className("navbar-brand"));
		homeLogo.click();

		driver.navigate().to("https://emicalculator.net/");
		String homePageText2 = myWait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/main/article/div[1]/h1")))
				.getText();
		System.out.println(homePageText2);
		Assert.assertEquals(homePageText2, homePage, "Page Navigation UnSuccessful");
	}

	@AfterClass
	public void quit1() {
		driver.quit();
	}

}
