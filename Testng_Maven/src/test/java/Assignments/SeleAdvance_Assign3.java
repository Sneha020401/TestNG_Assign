package Assignments;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.BaseSetup;
import utils.Const;
import utils.ExcelUtils;

public class SeleAdvance_Assign3 extends BaseSetup{
	public SeleAdvance_Assign3() {
		String htmlName = "SeleAdvAssign3.html";
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
			String screenshotPath = "./test-output/screenshots/Advance_Assign3/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Advance_Assign3/" + methodName + ".png";
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
	public void verifyUserDetails() throws InterruptedException, IOException {

		OpenBrowser(driver, properties.getProperty("url1"));
		createTest("Verify Test", "Verifying User Details");
		exttest.info("Test started for verifying");
		String excelFilePath = properties.getProperty("excelFilePath");
		List<List<String>> data = ExcelUtils.readExcelData(excelFilePath, "user");

		for (int i = 0; i < data.size(); i++) {
			List<String> row = data.get(i);
			String usern = row.get(0);
			String paword = row.get(1);
			String fname = row.get(2);
			String lname = row.get(3);
			String email = row.get(4);
			String phone = row.get(5);
			String add1 = row.get(6);
			String add2 = row.get(7);
			String city = row.get(8);
			String state = row.get(9);
			String zip = row.get(10);
			String country = row.get(11);
			String lang = row.get(12);
			String fav = row.get(13);

			exttest.info("Verification process for user: "+usern);
			driver.findElement(Const.SignIn).click();
			Thread.sleep(2000);
			exttest.info("Logged in");
			driver.findElement(Const.UserName).sendKeys(usern);
			WebElement pword = driver.findElement(Const.PassWord);
			pword.clear();
			pword.sendKeys(paword);
			driver.findElement(Const.LoginButton).click();
			Thread.sleep(2000);

			driver.findElement(Const.myaccountBtn).click();
			Thread.sleep(2000);
			exttest.info("Navigated to My Account Page");
			String unameDis = driver.findElement(Const.UsernameDisplay).getText();
			String fnameDis = driver.findElement(Const.FirstName).getAttribute("value");
			String lnameDis = driver.findElement(Const.LastName).getAttribute("value");
			String emailDis = driver.findElement(Const.EmailID).getAttribute("value");
			String phoneDis = driver.findElement(Const.PhoneID).getAttribute("value");
			String add1Dis = driver.findElement(Const.Address1ID).getAttribute("value");
			String add2Dis = driver.findElement(Const.Address2ID).getAttribute("value");
			String cityDis = driver.findElement(Const.CityID).getAttribute("value");
			String stateDis = driver.findElement(Const.StateID).getAttribute("value");
			String zipDis = driver.findElement(Const.ZipID).getAttribute("value");
			String countryDis = driver.findElement(Const.CountryID).getAttribute("value");
			String langDis = driver.findElement(Const.LangPref).getAttribute("value");
			String favDis = driver.findElement(Const.FavCategory).getAttribute("value");

			exttest.info("Verifying User Details");
			try {
				Assert.assertEquals(unameDis, usern);
				exttest.pass("Username Matched");
			}catch(AssertionError e) {
				exttest.fail("Username mismatch and Actual UserName is: "+unameDis + "and Expected UserName is: " +usern);
			}
			try {
				Assert.assertEquals(fnameDis, fname);
				exttest.pass("First Name Matched");
			}catch(AssertionError e) {
				exttest.fail("First Name mismatch and Actual FirstName is: "+fnameDis + "and Expected FirstName is: " +fname);
			}
			try {
				Assert.assertEquals(lnameDis, lname);
				exttest.pass("Last Name Matched");
			}catch(AssertionError e) {
				exttest.fail("Last Name mismatch and Actual Lastname is: "+lnameDis + "and Expected Lastname is: " +lname);
			}
			try {
				Assert.assertEquals(emailDis, email);
				exttest.pass("Email Matched");
			}catch(AssertionError e) {
				exttest.fail("Email mismatch and Actual email is: "+emailDis + "and Expected email is: " +email);
			}
			try {
				Assert.assertEquals(phoneDis, phone);
				exttest.pass("Phone Matched");
			}catch(AssertionError e) {
				exttest.fail("Phone mismatch and Actual phone is: "+phoneDis + "and Expected phone is: " +phone);
			}
			try {
				Assert.assertEquals(add1Dis, add1);
				exttest.pass("Address1 Matched");
			}catch(AssertionError e) {
				exttest.fail("Address1 mismatch and Actual address1 is: "+add1Dis + "and Expected address1 is: " +add1);
			}
			try {
				Assert.assertEquals(add2Dis, add2);
				exttest.pass("Address2 Matched");
			}catch(AssertionError e) {
				exttest.fail("Address2 mismatch and Actual address2 is: "+add2Dis + "and Expected address2 is: " +add2);
			}
			try {
				Assert.assertEquals(cityDis, city);
				exttest.pass("City Matched");
			}catch(AssertionError e) {
				exttest.fail("City mismatch and Actual city is: "+cityDis + "and Expected city is: " +city);
			}
			try {
				Assert.assertEquals(stateDis, state);
				exttest.pass("State Matched");
			}catch(AssertionError e) {
				exttest.fail("State mismatch and Actual State is: "+stateDis + "and Expected State is: " +state);
			}
			try {
				Assert.assertEquals(zipDis, zip);
				exttest.pass("Zip Matched");
			}catch(AssertionError e) {
				exttest.fail("Zip mismatch and Actual Zip is: "+zipDis + "and Expected Zip is: " +zip);
			}
			try {
				Assert.assertEquals(countryDis, country);
				exttest.pass("Country Matched");
			}catch(AssertionError e) {
				exttest.fail("Country mismatch and Actual Country is: "+countryDis + "and Expected Country is: " +country);
			}
			try {
				Assert.assertEquals(langDis, lang);
				exttest.pass("Lang Pref Matched");
			}catch(AssertionError e) {
				exttest.fail("Lang Pref mismatch and Actual Lang is: "+langDis + "and Expected Lang is: " +lang);
			}
			try {
				Assert.assertEquals(favDis, fav);
				exttest.pass("Favourite Category Matched");
			}catch(AssertionError e) {
				exttest.fail("Favourite Category mismatch and Actual favourite_category is: "+favDis + "and Expected favourite_category is: " +fav);
			}

			WebElement listchecked = driver.findElement(Const.ListOption);
			boolean listverify = listchecked.isSelected();
			try {
				Assert.assertTrue(listverify);
			}catch(AssertionError e) {
				System.out.println("The 'Enable MyList' checkbox is not selected.");
			}

			WebElement bannerchecked = driver.findElement(Const.BannerOption);
			boolean bannerverify = bannerchecked.isSelected();

			try{
				Assert.assertTrue(bannerverify);
			}catch(AssertionError e) {
				System.out.println("The 'Enable MyBanner' checkbox is not selected.");
			}
			if (usern.equals(row.get(0))) {
				exttest.pass("Verification is Success for user: "+usern);
				captureScreenshot("verify_success_" + usern);
				Thread.sleep(2000);

				ExcelUtils.writeStatusToExcel(excelFilePath, "user", i + 1, 16, "Verification Success");
			}

			driver.findElement(Const.SignOutBtn).click();
			Thread.sleep(2000);
		}

		ExcelUtils.closeExcelFile(excelFilePath);
		tearDown();
	}
}

