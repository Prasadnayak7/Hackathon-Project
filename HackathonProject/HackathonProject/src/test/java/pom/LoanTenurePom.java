package pom;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoanTenurePom {
	
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	public LoanTenurePom(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

	@FindBy(partialLinkText = "LOAN CALCULATORS")
	WebElement menu;
	
	public void clickMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menu));
        menu.click();
    }
	
	@FindBy(linkText = "Loan Calculator")
	WebElement option;
	
	public void optionClick() {
		wait.until(ExpectedConditions.elementToBeClickable(option));
		option.click();
	}
	
	@FindBy(id = "loan-tenure-calc")
	WebElement subOption;
	
	public void subOptionClick() {
		wait.until(ExpectedConditions.elementToBeClickable(subOption));
		subOption.click();
	}
	
	@FindBy(id = "loanamount")
	WebElement loanamount;
	
	public void sendLoanEmi(String loanAmt) {
		js.executeScript("arguments[0].scrollIntoView(true);", loanamount);
		js.executeScript("arguments[0].value = '"+loanAmt+"';", loanamount);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanamount);
	}
	
	@FindBy(id = "loanemi")
	WebElement loanEMI;
	
	public void sendLoanInterest(String loanemiAmt) {
		js.executeScript("arguments[0].scrollIntoView(true);", loanEMI);
		js.executeScript("arguments[0].value = '"+loanemiAmt+"';", loanEMI); 
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanEMI);
	}
	
	@FindBy(id = "loaninterest")
	WebElement loanInterest;
	
	public void sendLoanTerm(String loanint) {
		js.executeScript("arguments[0].scrollIntoView(true);", loanInterest);
        js.executeScript("arguments[0].value = '"+loanint+"';", loanInterest); 
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanInterest);
	}
	
	@FindBy(id = "loanfees")
	WebElement loanFees;
	
	public void sendFees(String loanFeesAmt) {
		js.executeScript("arguments[0].scrollIntoView(true);", loanFees);

		js.executeScript("arguments[0].value = '"+loanFeesAmt+"';", loanFees); 
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanFees);
	}
	
	
	
	@FindBy(xpath = "//*[@id='loansummary-tenure']/p/span")
	WebElement loanTenure;
	
	public String getPla() {
		js.executeScript("arguments[0].scrollIntoView(true);", loanTenure);
		return loanTenure.getText();
	}
	
	@FindBy(xpath = "//*[@id=\"loansummary-apr\"]/p/span")
	WebElement loanAPR;
	
	public String getApr() {
		js.executeScript("arguments[0].scrollIntoView(true);", loanAPR);

		return loanAPR.getText();
	}
	
	@FindBy(xpath = "//*[@id=\"loansummary-totalinterest\"]/p/span")
	WebElement payable;
	
	public String getTip() {
		js.executeScript("arguments[0].scrollIntoView(true);", payable);

		return payable.getText();
	}
	
	@FindBy(xpath = "//*[@id=\"loansummary-totalamount\"]/p/span")
	WebElement tp;
	
	public String getTp() {
		js.executeScript("arguments[0].scrollIntoView(true);", tp);

		return tp.getText();
	}
	
	
	
	@FindBy(xpath = "//*[@id='yearheader']")
	WebElement year;
	
	public List<WebElement> getYears() {
		js.executeScript("arguments[0].scrollIntoView(true);", year);
		List<WebElement> years = driver.findElements(By.xpath("//div[@id='loanpaymenttable']/table/tbody/tr[@class='row no-margin yearlypaymentdetails']"));
		
		return years;
	}
	
	@FindBy(linkText = "Download PDF")
	WebElement pdf;
	
	public void pdfClick() {
		js.executeScript("arguments[0].scrollIntoView(true);", pdf);
		pdf.click();
	}
	
	@FindBy(linkText = "Download Excel")
	public WebElement excel;
	
	public void excelClick() {
		js.executeScript("arguments[0].scrollIntoView(true);", excel);
		excel.click();
	}
	
	@FindBy(linkText = "Share")
	WebElement share;
	public void shareClick() {
		js.executeScript("arguments[0].scrollIntoView(true);", share);
		share.click();
	}
	
	
	@FindBy(id = "sharelink")
	WebElement shareLink;
	
	public String getShareLink() {
		share.click();
		String link = shareLink.getAttribute("value");
		
		return link;
	}
	
	
	
	
	
	
	
	
}
