package Sample;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.BaseSetup;
import utils.ExcelUtils;
public class SeleAdvance_Assign2 extends BaseSetup {
	@Override
	public void captureScreenshot(String methodName){
		String Passfilename = methodName;
		String FailfileName = methodName;
		try {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotFolder = "screenshots/Advance_Assign2/";
		String screenshotPath = screenshotFolder + methodName + ".png";
		File scdestn = new File(screenshotPath);

		FileUtils.copyFile(screenshot, scdestn);
		exttest.pass(Passfilename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
		} catch (IOException e) {
			exttest.fail(FailfileName, MediaEntityBuilder.createScreenCaptureFromPath("./test-output/screenshots/Basic_Assign/" + methodName +"fail.png").build());
			e.printStackTrace();
		}
		super.captureScreenshot(methodName);

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
					captureScreenshot("login_success_" + fname);
					Thread.sleep(3000);

					ExcelUtils.writeStatusToExcel(excelFilePath, "user", i + 1, 15, "Login Success");
				}
			}
			catch (AssertionError e) {
				exttest.fail("Login failed for user: " + usern);
				captureScreenshot("login_failure_" + fname);
			}
			driver.findElement(By.linkText("Sign Out")).click();
			Thread.sleep(2000);
		}

		ExcelUtils.closeExcelFile(excelFilePath);
		tearDown();
	}
}
