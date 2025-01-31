package base;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseSetup {
	public WebDriver driver;
	protected WebDriverWait wait;
	protected Properties properties;
	protected static ExtentReports extent;
	public static ExtentTest exttest;

	protected static void initializeExtentReport(String htmlName) {
			if (extent == null) {
				ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/" +htmlName);
				sparkReporter.config().setDocumentTitle("Automation Test Report");
				sparkReporter.config().setReportName("Automation Test Results");
				sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);
				extent = new ExtentReports();
				extent.attachReporter(sparkReporter);
			}

	}


	@BeforeMethod
	//@BeforeClass
	public WebDriver getBrowser() {

		if (driver == null) {
			try {
				properties = new Properties();
				FileInputStream file = new FileInputStream("src/test/resources/config.properties");
				properties.load(file);

				String browser = properties.getProperty("browser");
//				ChromeOptions options = new ChromeOptions();
//				options.addArguments("--start-maximized"); // Maximize window
//				options.addArguments("--disable-notifications"); // Disable notifications
//				options.addArguments("--remote-allow-origins=*");
				switch (browser.toLowerCase()) {
				case "chrome":
					System.setProperty("webdriver.chrome.driver","C:\\Users\\sneha.p14\\chromedriver.exe" );
					driver = new ChromeDriver();
					break;
				case "edge":
					System.setProperty("webdriver.edge.driver","C:\\Users\\sneha.p14\\msedgedriver.exe" );
					driver = new EdgeDriver();
					break;
				default:
					driver = new ChromeDriver();
					break;
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		}
		return driver;
	}


	public void OpenBrowser(WebDriver driver, String url) {
		driver.get(url);
	}
	public void captureScreenshot(String methodName, boolean isPass){
		String screenshotStatus = isPass ? "Pass" : "Fail";
	    String filename = methodName + "_" + screenshotStatus + ".png";
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/" + methodName + "_" + ".png";
			File scdestn = new File(screenshotPath);
			FileUtils.copyFile(screenshot, scdestn);
			if (isPass) {
	            exttest.pass(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
	        } else {
	            exttest.fail(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
	        }
		} catch (IOException e) {
	        e.printStackTrace();
		}
	}
	public void captureBufferedImage(String methodName, boolean isPass) throws IOException {
		String screenshotStatus = isPass ? "Pass" : "Fail";
	    String filename = methodName + "_" + screenshotStatus + ".png";
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rec = new Rectangle(screensize);
			BufferedImage src = robot.createScreenCapture(rec);
			String screenshotPath = "./test-output/screenshots/" + methodName + "_" + ".png";
			File scbuffdestn = new File(screenshotPath);
			ImageIO.write(src, "png", scbuffdestn);
			//exttest.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(scbuffdestn.getAbsolutePath()).build());
			if (isPass) {
	            exttest.pass(filename, MediaEntityBuilder.createScreenCaptureFromPath(scbuffdestn.getAbsolutePath()).build());
	        } else {
	            exttest.fail(filename, MediaEntityBuilder.createScreenCaptureFromPath(scbuffdestn.getAbsolutePath()).build());
	        }
		} catch (AWTException e) {
	        e.printStackTrace();
		}
	}
	public void captureScreenshot(String methodName){
	//	String screenshotStatus = isPass ? "Pass" : "Fail";
	    String filename = methodName + "_" + "info.png";
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/" + methodName + "_" + ".png";
			File scdestn = new File(screenshotPath);
			FileUtils.copyFile(screenshot, scdestn);
	            exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
		} catch (IOException e) {
	        e.printStackTrace();
		}
	}
	public void captureBufferedImage(String methodName) throws IOException {
//		String screenshotStatus = isPass ? "Pass" : "Fail";
	    String filename = methodName + "_" + "info.png";
		try {
			Robot robot = new Robot();
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rec = new Rectangle(screensize);
			BufferedImage src = robot.createScreenCapture(rec);
			String screenshotPath = "./test-output/screenshots/" + methodName + "_" + ".png";
			File scbuffdestn = new File(screenshotPath);
			ImageIO.write(src, "png", scbuffdestn);
			//exttest.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(scbuffdestn.getAbsolutePath()).build());
	            exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(scbuffdestn.getAbsolutePath()).build());

		} catch (AWTException e) {
	        e.printStackTrace();
		}
	}

	//new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()))
	@AfterMethod
	//@AfterClass
	public void tearDown() {

		if (driver != null) {
	        driver.quit();
	        driver = null; // Ensure the driver is nullified after quitting
	    }
	    if (exttest != null) {
	        extent.flush(); // Ensure extent reports are flushed properly
	    }
	}


	public void exttest(ExtentTest test) {
        exttest = test;  // Assign the passed ExtentTest to the class-level variable
    }
	public void createTest(String testName, String description) {
        exttest(extent.createTest(testName, description));
    }




}




