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
 
public class LoanCalculatorPom {
	
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	public LoanCalculatorPom(WebDriver driver) {
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
	
	@FindBy(id = "loan-amount-calc")
	WebElement subOption;
	
	public void subOptionClick() {
		wait.until(ExpectedConditions.elementToBeClickable(subOption));
		subOption.click();
	}
	
	@FindBy(id = "loanemi")
	WebElement loanEmi;
	public void sendLoanEmi(String loanEmiAmt) {
		js.executeScript("arguments[0].value = '"+loanEmiAmt+"';", loanEmi);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanEmi);
	}
	
	@FindBy(id = "loaninterest")
	WebElement loanInterest;
	public void sendLoanInterest(String loanInterestAmt) {
		js.executeScript("arguments[0].value = '"+loanInterestAmt+"';", loanInterest); 
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanInterest);
	}
	
	@FindBy(id = "loanterm")
	WebElement loanTerm;
	public void sendLoanTerm(String loanTermAmt) {
		js.executeScript("arguments[0].scrollIntoView(true);", loanTerm);
        js.executeScript("arguments[0].value = '"+loanTermAmt+"';", loanTerm); 
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanTerm);
	}
	
	@FindBy(id = "loanfees")
	WebElement loanFees;
	
	public void sendFees(String loanFeesAmt) {
		js.executeScript("arguments[0].value = '"+loanFeesAmt+"';", loanFees); 
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanFees);
	}
	
	@FindBy(xpath = "//*[@id='loansummary-loanamount']/p/span")
	WebElement pla;
	
	public String getPla() {
		js.executeScript("arguments[0].scrollIntoView(true);", pla);
		return pla.getText();
	}
	
	@FindBy(xpath = "//*[@id='loansummary-apr']/p/span")
	WebElement apr;
	
	public String getApr() {
		return apr.getText();
	}
	
	@FindBy(xpath = "//*[@id='loansummary-totalinterest']/p/span")
	WebElement tip;
	
	public String getTip() {
		return tip.getText();
	}
	
	@FindBy(xpath = "//*[@id='loansummary-totalamount']/p/span")
	WebElement tp;
	
	public String getTp() {
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
	
	@FindBy(linkText = "Share")
	public WebElement share;
	
	@FindBy(id = "sharelink")
	WebElement shareLink;
	
	public String getShareLink() {
		share.click();
		String link = shareLink.getAttribute("value");
		return link;
	}
}