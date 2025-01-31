package Assignments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.BaseSetup;
import utils.Const;
import utils.ExcelUtils;

public class SeleAdvance_Assign1 extends BaseSetup {
	public SeleAdvance_Assign1() {
		String htmlName = "SeleAdvAssign1.html";
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
			String screenshotPath = "./test-output/screenshots/Advance_Assign1/" + methodName + ".png";
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
	public void captureScreenshot(String methodName){
		String filename = methodName + "_" + "_info.png";
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/Advance_Assign1/" + methodName + ".png";
			File scdestn = new File(screenshotPath);
			FileUtils.copyFile(screenshot, scdestn);

			exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
		}
		catch (IOException e) {
			exttest.fail(methodName, MediaEntityBuilder.createScreenCaptureFromPath("./test-output/screenshots/Basic_Assign/" + methodName + "_fail.png").build());
			e.printStackTrace();
		}

	}



	@Test
	public void fillRegistrationForm() throws IOException, InterruptedException {
		createTest("Fill Registration Form", "Filling the registration form and verifying login");

		exttest.info("Test started for filling the registration form");

		OpenBrowser(driver, properties.getProperty("url1"));
		String excelFilePath = properties.getProperty("excelFilePath");
		List<List<String>> data = ExcelUtils.readExcelData(excelFilePath, "user");

		for (List<String> rowData : data) {
			if (rowData.size() < 14) continue;

			String usern = rowData.get(0);
			String password = rowData.get(1);
			String fname = rowData.get(2);
			String lname = rowData.get(3);
			String email = rowData.get(4);
			String phone = rowData.get(5);
			String add1 = rowData.get(6);
			String add2 = rowData.get(7);
			String city = rowData.get(8);
			String state = rowData.get(9);
			int zip = Integer.parseInt(rowData.get(10));
			String country = rowData.get(11);
			String lang = rowData.get(12).toLowerCase();
			String fav = rowData.get(13).toUpperCase();


			driver.findElement(Const.SignIn).click();
			driver.findElement(Const.ResgisterNow).click();
			Thread.sleep(2000);
			exttest.pass("Navigated into Registration Page");
			exttest.info("Filling out the registration form for user: " + usern);
			driver.findElement(Const.UserName).sendKeys(usern);
			WebElement pword = driver.findElement(Const.PassWord);
			pword.sendKeys(password);
			WebElement repeat = driver.findElement(Const.RepeatPassword);
			repeat.sendKeys(password);

			if (pword.getAttribute("value").equals(repeat.getAttribute("value"))) {
				driver.findElement(Const.FirstName).sendKeys(fname);
				driver.findElement(Const.LastName).sendKeys(lname);
				driver.findElement(Const.EmailID).sendKeys(email);
				driver.findElement(Const.PhoneID).sendKeys(String.valueOf(phone));
				driver.findElement(Const.Address1ID).sendKeys(add1);
				driver.findElement(Const.Address2ID).sendKeys(add2);
				driver.findElement(Const.CityID).sendKeys(city);
				driver.findElement(Const.StateID).sendKeys(state);
				driver.findElement(Const.ZipID).sendKeys(String.valueOf(zip));
				driver.findElement(Const.CountryID).sendKeys(country);

				WebElement lg = driver.findElement(Const.LangPref);
				Select langpref = new Select(lg);
				langpref.selectByVisibleText(lang);
				Thread.sleep(2000);

				WebElement favc = driver.findElement(Const.FavCategory);
				Select favcate = new Select(favc);
				favcate.selectByVisibleText(fav);
				Thread.sleep(2000);

				WebElement chk1 = driver.findElement(Const.ListOption);
				chk1.click();
				Assert.assertTrue(chk1.isSelected(), "Checkbox 1 not selected");
				WebElement chk2 = driver.findElement(Const.BannerOption);
				chk2.click();
				Assert.assertTrue(chk2.isSelected(), "Checkbox 2 not selected");
				Thread.sleep(2000);

				driver.findElement(Const.RegisterButton).click();
				exttest.pass("Registration Form submitted for user: " + usern);
				exttest.info("Verification of Registration Form for user: " + usern);
				driver.findElement(Const.SignIn).click();
				driver.findElement(Const.UserName).sendKeys(usern);
				WebElement paword = driver.findElement(Const.PassWord);
				paword.clear();
				paword.sendKeys(password);
				driver.findElement(Const.LoginButton).click();

				String expected = "Welcome " + fname + "!";
				String actual = driver.findElement(Const.RegisterConfirmMessage).getText();
				try {
					Assert.assertEquals(actual, expected);
					if (rowData.get(0).equals(usern)) {
						exttest.pass("Registration successfully verified for user: " + usern);
						captureScreenshot("register_success_" + fname,true);

						Thread.sleep(2000);

						int rowIndex = data.indexOf(rowData) + 1;
						ExcelUtils.writeStatusToExcel(excelFilePath, "user", rowIndex, 14, "Registration Success");
					}
				}
				catch (AssertionError e) {
					exttest.fail("Registration failed for user: " + usern);
					captureScreenshot("register_failure_" + fname);
				}
			}
			driver.findElement(Const.SignOutBtn).click();
			Thread.sleep(2000);
			//exttest.pass("SignOut successfully for user: " + usern);
		}
		FileInputStream fis = new FileInputStream(excelFilePath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		fis.close();
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		wb.write(fos);
		fos.close();
		wb.close();

		tearDown();
	}
}
