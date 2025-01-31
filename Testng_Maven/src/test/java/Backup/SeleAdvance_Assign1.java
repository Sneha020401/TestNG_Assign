package Backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseSetup;
import utils.ExcelUtils;

public class SeleAdvance_Assign1 extends BaseSetup {
	@Override
	public void captureScreenshot(String methodName){
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotFolder = "screenshots/Advance_Assign1/";
		String screenshotPath = screenshotFolder + methodName + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".png";
		try {
			FileUtils.copyFile(screenshot, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.captureScreenshot(methodName);

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
			exttest.info("Filling out the registration form for user: " + usern);

			driver.findElement(By.linkText("Sign In")).click();
			driver.findElement(By.linkText("Register Now!")).click();
			Thread.sleep(2000);


			driver.findElement(By.name("username")).sendKeys(usern);
			WebElement pword = driver.findElement(By.name("password"));
			pword.sendKeys(password);
			WebElement repeat = driver.findElement(By.name("repeatedPassword"));
			repeat.sendKeys(password);

			if (pword.getAttribute("value").equals(repeat.getAttribute("value"))) {
				driver.findElement(By.name("account.firstName")).sendKeys(fname);
				driver.findElement(By.name("account.lastName")).sendKeys(lname);
				driver.findElement(By.name("account.email")).sendKeys(email);
				driver.findElement(By.name("account.phone")).sendKeys(String.valueOf(phone));
				driver.findElement(By.name("account.address1")).sendKeys(add1);
				driver.findElement(By.name("account.address2")).sendKeys(add2);
				driver.findElement(By.name("account.city")).sendKeys(city);
				driver.findElement(By.name("account.state")).sendKeys(state);
				driver.findElement(By.name("account.zip")).sendKeys(String.valueOf(zip));
				driver.findElement(By.name("account.country")).sendKeys(country);

				WebElement lg = driver.findElement(By.name("account.languagePreference"));
				Select langpref = new Select(lg);
				langpref.selectByVisibleText(lang);
				Thread.sleep(2000);

				WebElement favc = driver.findElement(By.name("account.favouriteCategoryId"));
				Select favcate = new Select(favc);
				favcate.selectByVisibleText(fav);
				Thread.sleep(2000);

				WebElement chk1 = driver.findElement(By.name("account.listOption"));
				chk1.click();
				Assert.assertTrue(chk1.isSelected(), "Checkbox 1 not selected");
				WebElement chk2 = driver.findElement(By.name("account.bannerOption"));
				chk2.click();
				Assert.assertTrue(chk2.isSelected(), "Checkbox 2 not selected");
				Thread.sleep(2000);

				driver.findElement(By.name("newAccount")).click();
				driver.findElement(By.linkText("Sign In")).click();
				driver.findElement(By.name("username")).sendKeys(usern);
				WebElement paword = driver.findElement(By.name("password"));
				paword.clear();
				paword.sendKeys(password);
				driver.findElement(By.name("signon")).click();

				String expected = "Welcome " + fname + "!";
				String actual = driver.findElement(By.id("WelcomeContent")).getText();
				try {
					Assert.assertEquals(actual, expected, "Resiter invalid");            
					if (rowData.get(0).equals(usern)) {
						captureScreenshot("register_success_" + fname);
						exttest.pass("User " + usern + " registered successfully");
						Thread.sleep(2000);

						int rowIndex = data.indexOf(rowData) + 1; 
						ExcelUtils.writeStatusToExcel(excelFilePath, "user", rowIndex, 14, "Registration Success");
					}
				}
				catch (AssertionError e) {
					exttest.fail("Register failed for user: " + usern);
					captureScreenshot("register_failure_" + fname);
				}
			}
			driver.findElement(By.linkText("Sign Out")).click();
			Thread.sleep(2000);
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
