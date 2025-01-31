package Sample;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class NewTest {
	ExtentReports extent;
	ExtentTest exttest;

	WebDriver driver;	
	@Test
	public void f() throws InterruptedException, IOException {
		exttest = extent.createTest("Google");
		exttest.log(Status.INFO, "go to google");
		driver.get("https://www.google.com"); 
		String title = driver.getTitle();
		TakesScreenshot sc = (TakesScreenshot) driver;
		File screenshot = sc.getScreenshotAs(OutputType.FILE);
		if(title.equals("Google")) {
			exttest.log(Status.PASS, "equal");			
			exttest.log(Status.PASS, "Title is equal to 'Google'");
			File dest = new File("./test-output/screenshots/sample_pass.png");
			FileUtils.copyFile(screenshot, dest);
			// Use the absolute path for addScreenCaptureFromPath
			exttest.addScreenCaptureFromPath(dest.getAbsolutePath());
		}else {
			exttest.log(Status.FAIL, "Title is not equal to 'Google'");
			File dest = new File("./test-output/screenshots/sample_fail.png");
			FileUtils.copyFile(screenshot, dest);
			// Use the absolute path for addScreenCaptureFromPath
			exttest.addScreenCaptureFromPath(dest.getAbsolutePath());
		}
		Thread.sleep(2000);

	}
	public static ChromeDriver getChromeDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--remote-allow-origins=*");
		return new ChromeDriver(chromeOptions);
	}
	@BeforeMethod
	public void beforeMethod() throws InterruptedException {



		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/Sample.html");

		//sparkReporter.config().setDocumentTitle("Automation Test Report");
		//sparkReporter.config().setReportName("Automation Test Results");
		sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK); 
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sneha.p14\\chromedriver.exe");

		driver= getChromeDriver();
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);

	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
		extent.flush();
	}

}
