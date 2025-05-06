package pom;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmiLoanPom {

    WebDriver driver;
    
    

    // Web elements using @FindBy annotation
    @FindBy(xpath = "//*[@id='loanamount']")
    WebElement loanAmountField;

    @FindBy(xpath = "//*[@id='loanterm']")
    WebElement durationField;

    @FindBy(xpath = "//*[@id='loaninterest']")
    WebElement interestRateField;
    
    @FindBy(xpath="//*[@id='loanfees']")
    WebElement loanfeesField;
    
    @FindBy(xpath = "//*[@id='loansummary-emi']/p/span")
    WebElement loanEmi;

    @FindBy(xpath = "//*[@id='loansummary-apr']/p/span")
    WebElement loanAPR;

    @FindBy(xpath = "//*[@id=\"loansummary-totalinterest\"]/p/span")
    WebElement totalInterest;

    @FindBy(xpath = "//*[@id='loansummary-totalamount']/p/span")
    WebElement totalAmount;
    
    @FindBy(linkText = "Download PDF")
  	WebElement pdf;


    // Constructor to initialize the PageFactory
    public EmiLoanPom(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        JavascriptExecutor js = (JavascriptExecutor) driver;
    }

    // Methods to interact with the fields
    public void setLoanAmount(String loanAmount,WebDriver driver) {
    	 
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	    // Clear the existing value
    	    js.executeScript("arguments[0].value = '';", loanAmountField);

    	    // Set the new value
    	    js.executeScript("arguments[0].value = arguments[1];", loanAmountField, loanAmount);

    	    // Trigger any event listeners attached to the field (if needed)
    	    js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanAmountField);
    }

    public void setDuration(String duration,WebDriver driver) {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    // Clear the existing value
	    js.executeScript("arguments[0].value = '';", durationField);

	    // Set the new value
	    js.executeScript("arguments[0].value = arguments[1];", durationField, duration);

	    // Trigger any event listeners attached to the field (if needed)
	    js.executeScript("arguments[0].dispatchEvent(new Event('change'));", durationField);
    }

    public void setInterestRate(String interestRate,WebDriver driver) {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    // Clear the existing value
	    js.executeScript("arguments[0].value = '';", interestRateField);

	    // Set the new value
	    js.executeScript("arguments[0].value = arguments[1];", interestRateField, interestRate);

	    // Trigger any event listeners attached to the field (if needed)
	    js.executeScript("arguments[0].dispatchEvent(new Event('change'));", interestRateField);
    }
    public void setLoanfees(String loanfees,WebDriver driver) {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    // Clear the existing value
	    js.executeScript("arguments[0].value = '';", loanfeesField);

	    // Set the new value
	    js.executeScript("arguments[0].value = arguments[1];", loanfeesField, loanfees);

	    // Trigger any event listeners attached to the field (if needed)
	    js.executeScript("arguments[0].dispatchEvent(new Event('change'));", loanfeesField);
    }
    public String getLOANEMI() {
        return loanEmi.getText(); // Assuming the value is stored in the "value" attribute
    }

    public String getLOANAPR() {
        return loanAPR.getText();
    }

    public String getTotalInterestPayable() {
        return totalInterest.getText();
    }

    public String getTotalPayment() {
        return totalAmount.getText();
    }
    
   
   	public void pdfClick(WebDriver driver) {
   		JavascriptExecutor js = (JavascriptExecutor) driver;
   		js.executeScript("arguments[0].scrollIntoView(true);", pdf);
   		pdf.click();
   	}
   	@FindBy(linkText = "Download Excel")
   	WebElement excel;
   	public void excelClick(WebDriver driver) {
   		JavascriptExecutor js = (JavascriptExecutor) driver;
   		js.executeScript("arguments[0].scrollIntoView(true);", excel);
   		excel.click();
   	}
   	@FindBy(linkText = "Share")
   	WebElement share;
   	public void shareClick(WebDriver driver) {
   		JavascriptExecutor js = (JavascriptExecutor) driver;
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
