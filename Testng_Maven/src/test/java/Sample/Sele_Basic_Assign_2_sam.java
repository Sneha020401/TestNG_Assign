package Sample;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseSetup;



public class Sele_Basic_Assign_2_sam extends BaseSetup{
	public Sele_Basic_Assign_2_sam() {
		String htmlName = "Test.html";
		BaseSetup.initializeExtentReport(htmlName);
	}

	@BeforeMethod
	public void setupClass() {
		System.out.println("WebDriver initialized for the class.");
		OpenBrowser(driver, properties.getProperty("url"));
	}

	@AfterMethod
	public void tearDownMethod() {
		System.out.println("Test method completed.");
	}

	@Override
	public void captureScreenshot(String methodName, boolean isPass){
		String screenshotPath = "./test-output/screenshots/Basic_Assign_sam/" + methodName + ".png";
		super.captureScreenshot(methodName, isPass);
	}

	@Override
	public void captureBufferedImage(String methodName, boolean isPass) throws IOException{
		String screenshotPath = "./test-output/screenshots/Basic_Assign_sam/" + methodName + ".png";
		super.captureBufferedImage(methodName, isPass);
	}

	@Override
	public void captureScreenshot(String methodName){
		String screenshotPath = "./test-output/screenshots/Basic_Assign_sam/" + methodName + ".png";
		super.captureScreenshot(methodName);
	}

	@Override
	public void captureBufferedImage(String methodName) throws IOException{
		String screenshotPath = "./test-output/screenshots/Basic_Assign_sam/" + methodName + ".png";
		super.captureBufferedImage(methodName);
	}

	@Test(priority = 1)
	public void T1WebPageTitle() throws InterruptedException {
		createTest("T1_WebPageTitle", "Validating Title");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Practice Page";
		exttest.info( "actualTitle: "+actualTitle);
		exttest.info("expectedTitle: "+expectedTitle);
		try{
			Assert.assertEquals(actualTitle,expectedTitle);
			captureScreenshot("T1WebPageTitle",true);
		}catch (AssertionError e) {
			exttest.fail("Title mismatch on the webpage");
			captureScreenshot("T1WebPageTitle",false);
		}
	}
	@Test(priority = 2)
	public void T2radioButtonSelection() throws InterruptedException {
		createTest("T2_radioButtonSelection", "Validating radioButton");
		WebElement radioButton2 = driver.findElement(By.xpath("//input[@value=\"radio2\"]"));
		radioButton2.click();
		try{
			Assert.assertTrue(radioButton2.isSelected());
			exttest.pass("radioButton isSelected");
			captureScreenshot("T2radioButtonSelection" , true);
		}catch (AssertionError e) {
			exttest.fail("Radio Button 2 not selected");
			captureScreenshot("T2radioButtonSelection" , false);
		}
	}

}
