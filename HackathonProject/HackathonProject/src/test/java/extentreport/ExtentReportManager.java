package extentreport;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;

	public void onStart(ITestContext context) {
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/myReport.html");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Computer Name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Testing Team", "Team L");
		extent.setSystemInfo("OS", "Windows11");
		extent.setSystemInfo("Browser name", "Chrome");
	}

	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test case PASSED is: " + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		ExtentTest test = extentTest.get();
		test.log(Status.FAIL, "Test case FAILED is: " + result.getName());
		test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable());

		// Capture screenshot
		WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + result.getName() + ".png";
		try {
			FileUtils.copyFile(srcFile, new File(screenshotPath));
			test.addScreenCaptureFromPath(screenshotPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, "Test case SKIPPED is: " + result.getName());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
