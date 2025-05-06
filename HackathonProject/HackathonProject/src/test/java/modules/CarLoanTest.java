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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.CarLoanPom;
import utils.DataUtils;


@Listeners(extentreport.ExtentReportManager.class)
public class CarLoanTest
{
	WebDriver driver=null;
	List<List<Object>> result = new ArrayList<>();
	CarLoanPom pom;
	JavascriptExecutor js;
	
	
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
		
		js = (JavascriptExecutor) driver;
		
		pom = new CarLoanPom(driver);
		
        List<Object> titles = new ArrayList<Object>();
        
        titles.add("Loan EMI");
        titles.add("Total Interest Payable");
        titles.add("Total Payment");
        
        result.add(titles);
	}
	
	
    @Test(dependsOnGroups = {"smoke"}, priority = 1, dataProvider="carLoanDataProvider", dataProviderClass=DataUtils.class)
    public void test(String carLoanAmt,String intRate,String tenure) throws Exception 
    {
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        //js.executeScript("arguments[0].scrollIntoView(true);", pom3.carLoan);
        pom.carLoan.click();
        
        js.executeScript("arguments[0].value='" + carLoanAmt + "';", pom.carLoanTxt);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.carLoanTxt);
        
        js.executeScript("arguments[0].value='" + intRate + "';", pom.intRateTxt);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.intRateTxt);
        
        js.executeScript("arguments[0].value='" + tenure + "';", pom.tenureTxt);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", pom.tenureTxt);
        
        js.executeScript("arguments[0].scrollIntoView(true);", pom.scroll1);
        
        String emiAmttxt = pom.emiAmt.getText();
        
        String totIntTxt = pom.totInt.getText();
        
        String totPayTxt = pom.totPay.getText();
        
        List<Object> values = new ArrayList<Object>();
        values.add(emiAmttxt);
        values.add(totIntTxt);
        values.add(totPayTxt);
        
        result.add(values);
        
        DataUtils.writeIntoExcel("src/test/resources/TestData.xlsx", "CarLoan Output",result);
        
        WebElement scroll2 = driver.findElement(By.xpath("//*[@id=\"yearheader\"]"));
        js.executeScript("arguments[0].scrollIntoView(true);", scroll2);
        
        WebElement pdf = driver.findElement(By.linkText("Download PDF"));
		pdf.click();
		Thread.sleep(3000);
		File pdfFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.pdf");
		if (pdfFile.exists()) 
		{
			System.out.println("PDF File downloaded successfully!");
		} 
		else 
		{
			System.out.println("PDF File not found.");
		}
		
		WebElement excel = driver.findElement(By.linkText("Download Excel"));
		excel.click();
		Thread.sleep(3000);
		File excelFile = new File("C:\\Users\\2388952\\Downloads\\loan_amortization_schedule.xlsx");
		if (excelFile.exists()) 
		{
			System.out.println("Excel File downloaded successfully!");
		} 
		else 
		{
			System.out.println("Excel File not found.");
		}
		
		WebElement share = driver.findElement(By.linkText("Share"));
		share.click();
		WebElement shareLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sharelink")));
		String link = shareLink.getAttribute("value");
		System.out.println("Share Link : " + link);
    }
    
    @AfterClass
    public void quitWindow()
    {
    	driver.quit();
    }
}
