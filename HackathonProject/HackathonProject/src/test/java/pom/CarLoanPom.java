package pom;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CarLoanPom 
{
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	public CarLoanPom(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }
	
	@FindBy(linkText="Car Loan")
	public WebElement carLoan;
	
	@FindBy(id="loanamount")
	public WebElement carLoanTxt;
	
	@FindBy(id="loaninterest")
	public WebElement intRateTxt;
	
	@FindBy(id="loanterm")
	public WebElement tenureTxt;
	
	@FindBy(xpath="//*[@id=\"emiamount\"]")
	public WebElement scroll1;
	
	@FindBy(xpath="//*[@id=\"emiamount\"]/p/span")
	public WebElement emiAmt;
	
	@FindBy(xpath="//*[@id=\"emitotalinterest\"]/p/span")
	public WebElement totInt;
	
	@FindBy(xpath="//*[@id=\"emitotalamount\"]/p/span")
	public WebElement totPay;
	
}
