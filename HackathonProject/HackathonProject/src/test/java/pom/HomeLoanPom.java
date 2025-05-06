package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeLoanPom {

	public HomeLoanPom(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id ="loanamount")
	public WebElement loanTextBox;
	
	@FindBy(id="loaninterest")
	public WebElement interestTextBox;
	
	@FindBy(id = "loanterm")
	public WebElement loanTermTextBox;
	
	@FindBy(xpath = "//*[@id=\"emiamount\"]/p/span")
	public WebElement loanEMI;
	
	@FindBy(xpath = "//*[@id=\"emitotalinterest\"]/p/span")
	public WebElement totalInterestPayable;
	
	@FindBy(xpath = "//*[@id=\"emitotalamount\"]/p/span")
	public WebElement totalPayment;
}
