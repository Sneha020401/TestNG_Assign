package Backup;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.BaseSetup;
import utils.ExcelUtils;
public class SeleAdvance_Assign2 extends BaseSetup {
	public SeleAdvance_Assign2() {
		String htmlName = "SeleAdv_Assign2.html";
		BaseSetup.initializeExtentReport(htmlName);
	}

	@BeforeMethod
	public void setupClass() {
		System.out.println("WebDriver initialized for the class.");
		OpenBrowser(driver, properties.getProperty("url1"));
	}

	@AfterMethod
	public void tearDownMethod() {
		System.out.println("Test method completed.");
	}

	@Override
	public void captureScreenshot(String methodName, boolean isPass){
		String screenshotStatus = isPass ? "Pass" : "Fail";
		String filename = methodName + "_" + screenshotStatus + ".png";

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/Advance_Assign2/" + methodName + ".png";
			File scdestn = new File(screenshotPath);


			FileUtils.copyFile(screenshot, scdestn);
			if (isPass) {
				exttest.pass(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
			} else {
				exttest.fail(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
			}
		} catch (IOException e) {
			exttest.fail(methodName, MediaEntityBuilder.createScreenCaptureFromPath("./test-output/screenshots/Basic_Assign/" + methodName + "_fail.png").build());
			e.printStackTrace();
		}

	}

	@Override
	public void captureBufferedImage(String methodName, boolean isPass) throws IOException{
		String screenshotStatus = isPass ? "Pass" : "Fail";
		String filename = methodName + "_" + screenshotStatus + ".png";

		try {
			BufferedImage src = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			String screenshotPath = "./test-output/screenshots/Advance_Assign2/" + methodName + ".png";
			File buffdestn = new File(screenshotPath);
			ImageIO.write(src, "png", buffdestn);
			if (isPass) {
				exttest.pass(filename, MediaEntityBuilder.createScreenCaptureFromPath(buffdestn.getAbsolutePath()).build());
			} else {
				exttest.fail(filename, MediaEntityBuilder.createScreenCaptureFromPath(buffdestn.getAbsolutePath()).build());
			}

		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void captureScreenshot(String methodName){
		String filename = methodName + "_" + "_info.png";
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/Advance_Assign2/" + methodName + ".png";
			File scdestn = new File(screenshotPath);
			FileUtils.copyFile(screenshot, scdestn);

			exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
		}
		catch (IOException e) {
			exttest.fail(methodName, MediaEntityBuilder.createScreenCaptureFromPath("./test-output/screenshots/Basic_Assign/" + methodName + "_fail.png").build());
			e.printStackTrace();
		}

	}

	@Override
	public void captureBufferedImage(String methodName) throws IOException{
		String filename = methodName + "_" + "info.png";

		try {
			BufferedImage src = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			String screenshotPath = "./test-output/screenshots/Advance_Assign2/" + methodName + ".png";
			File buffdestn = new File(screenshotPath);
			ImageIO.write(src, "png", buffdestn);
			exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(buffdestn.getAbsolutePath()).build());

		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void loginTest() throws InterruptedException, IOException {
		createTest("Login Test", "Validating login functionality with multiple users");
		exttest.info("Test started for login functionality");
		OpenBrowser(driver, properties.getProperty("url1"));

		String excelFilePath = properties.getProperty("excelFilePath");
		List<List<String>> data = ExcelUtils.readExcelData(excelFilePath, "user");

		for (int i = 0; i < data.size(); i++) {
			List<String> row = data.get(i);
			String usern = row.get(0);
			String paword = row.get(1);
			String fname = row.get(2);

			driver.findElement(By.linkText("Sign In")).click();
			Thread.sleep(2000);

			driver.findElement(By.name("username")).sendKeys(usern);
			WebElement pword = driver.findElement(By.name("password"));
			pword.clear();
			pword.sendKeys(paword);
			driver.findElement(By.name("signon")).click();

			String expected = "Welcome " + fname + "!";
			String actual = driver.findElement(By.id("WelcomeContent")).getText();
			try {
				Assert.assertEquals(actual, expected, "Login failed for user: " + usern);
				exttest.pass("Login successful for user: " + usern);

				if (usern.equals(row.get(0))) {
					captureScreenshot("login_success_" + fname, true);
					Thread.sleep(3000);

					ExcelUtils.writeStatusToExcel(excelFilePath, "user", i + 1, 15, "Login Success");
				}
			}
			catch (AssertionError e) {
				exttest.fail("Login failed for user: " + usern);
				captureScreenshot("login_failure_" + fname,false);
			}
			driver.findElement(By.linkText("Sign Out")).click();
			Thread.sleep(2000);
		}

		ExcelUtils.closeExcelFile(excelFilePath);
		tearDown();
	}
}
