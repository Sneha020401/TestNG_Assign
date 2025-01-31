package Backup;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;

import base.BaseSetup;



public class Sele_Basic_Assign_1 extends BaseSetup{
	public Sele_Basic_Assign_1() {
		String htmlName = "Sele_BasicAssign1.html";
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
		String screenshotStatus = isPass ? "Pass" : "Fail";
		String filename = methodName + "_" + screenshotStatus + ".png";

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/Basic_Assign1/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign1/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign1/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign1/" + methodName + ".png";
			File buffdestn = new File(screenshotPath);
			ImageIO.write(src, "png", buffdestn);
			exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(buffdestn.getAbsolutePath()).build());

		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void T1_WebPageTitle() throws InterruptedException {
		createTest("T1_WebPageTitle", "Validating Title");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Practice Page";
		exttest.info( "actualTitle: "+actualTitle);
		exttest.info("expectedTitle: "+expectedTitle);
		try{
			Assert.assertEquals(actualTitle,expectedTitle);
			captureScreenshot("T1_WebPageTitle",true);
		}catch (AssertionError e) {
			exttest.fail("Title mismatch on the webpage");
			captureScreenshot("T1_WebPageTitle",false);
		}
	}
	@Test(priority = 2)
	public void T2_radioButtonSelection() throws InterruptedException {
		createTest("T2_radioButtonSelection", "Validating radioButton");
		WebElement radioButton2 = driver.findElement(By.xpath("//input[@value=\"radio2\"]"));
		radioButton2.click();
		try{
			Assert.assertTrue(radioButton2.isSelected());
			exttest.pass("radioButton isSelected");
			captureScreenshot("T2_radioButtonSelection" , true);
		}catch (AssertionError e) {
			exttest.fail("Radio Button 2 not selected");
			captureScreenshot("T2_radioButtonSelection" , false);
		}
	}

	@Test(priority = 3)
	public void T3_suggestionClassTest() throws InterruptedException {
		createTest("T3_suggestionClassTest", "Validating SuggestionSelection");

		WebElement suggestBox = driver.findElement(By.id("autocomplete"));
		suggestBox.sendKeys("Ind");
		Thread.sleep(2000);
		WebElement suggestion = driver.findElement(By.xpath("//li[contains(., 'India')]//div[text()='India']"));
		Thread.sleep(2000);
		exttest.info("Expected Value: "+suggestion.getText());
		try{
			Assert.assertTrue(suggestion.getText().contains("India"));
			exttest.pass("Suggestion matched Value: " +suggestion.getText());
		}catch(AssertionError e) {
			exttest.fail("Suggestion mismatch and expected value: " +suggestion.getText());
		}

		suggestion.click();
		try{

			Assert.assertTrue(suggestBox.getAttribute("value").equals("India"));
			exttest.pass("Suggested Value Selected: "+suggestBox.getAttribute("value"));
			captureScreenshot("T3_suggestionClassTest" , true);
		}catch(AssertionError e) {
			exttest.fail("Suggested value not selected");
			captureScreenshot("T3_suggestionClassTest" , false);
		}
	}

	@Test(priority = 4)
	public void T4_dropdownSelection() throws InterruptedException {
		createTest("T4_dropdownSelection", "Validating dropDownSelection");

		Select dropdown = new Select(driver.findElement(By.id("dropdown-class-example")));
		dropdown.selectByVisibleText("Option2");
		Thread.sleep(2000);
		try{
			Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Option2", "Option not selected");
			exttest.pass("Option is selected: "+dropdown.getFirstSelectedOption().getText());
			captureScreenshot("T4_dropdownSelection" , true);
		}catch(AssertionError e) {
			exttest.fail("Option not selected");
			captureScreenshot("T4_dropdownSelection" , false);
		}
	}
	@Test(priority = 5)
	public void T5_checkboxSelection() throws InterruptedException {
		createTest("T5_checkboxSelection", "Validating CheckBoxSelection");

		WebElement checkbox1 = driver.findElement(By.id("checkBoxOption1"));
		checkbox1.click();
		try{
			Assert.assertTrue(checkbox1.isSelected(), "Checkbox 1 not selected");
			exttest.pass("Checkbox1 is selected");
			captureScreenshot("T5_checkboxSelection1" , true);
		}catch(AssertionError e) {
			exttest.fail("Checkbox1 not selected");
			captureScreenshot("T5_checkboxSelection1" , false);
		}

		WebElement checkbox2 = driver.findElement(By.id("checkBoxOption2"));
		checkbox2.click();
		try{
			Assert.assertTrue(checkbox2.isSelected(), "Checkbox 2 not selected");
			exttest.pass("Checkbox2 is selected");
			captureScreenshot("T5_checkboxSelection2" , true);
		}catch(AssertionError e) {
			exttest.fail("Checkbox2 not selected");
			captureScreenshot("T5_checkboxSelection2" , false);
		}
	}

	@Test(priority = 6)
	public void T6_switchWindow() throws InterruptedException {
		createTest("T6_switchWindow", "Validating switchWindow");
		driver.findElement(By.id("openwindow")).click();
		String mainWindow = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(mainWindow)) {
				driver.switchTo().window(windowHandle);
				Thread.sleep(5000);
				break;
			}
		}

		try{
			Assert.assertTrue(driver.getCurrentUrl().contains("https://www.qaclickacademy.com/"));
			exttest.pass("Switch Window Success and CurrentUrl: "+driver.getCurrentUrl());
			captureScreenshot("T6_switchWindow", true);
		}catch(AssertionError e) {
			exttest.fail("Switch Window Failed");
			captureScreenshot("T6_switchWindow", false);
		}
		Thread.sleep(3000);
		driver.close();
		driver.switchTo().window(mainWindow);
	}

	@Test(priority = 7)
	public void T7_switchTab() throws InterruptedException {
		createTest("T7_switchTab", "Validating switchTab");
		Thread.sleep(3000);
		driver.findElement(By.id("opentab")).click();
		String mainWindow = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(mainWindow)) {
				driver.switchTo().window(windowHandle);
				Thread.sleep(3000);
				break;
			}
		}
		try{
			Assert.assertTrue(driver.getCurrentUrl().contains("https://www.qaclickacademy.com/") );
			exttest.pass("Switch Tab Success");
			captureScreenshot("T7_switchTab" , true);
		}catch(AssertionError e) {
			exttest.fail("Switch tab Failed");
			captureScreenshot("T7_switchTab" , false);
		}

		driver.close();
		Thread.sleep(3000);
		driver.switchTo().window(mainWindow);
	}

	@Test(priority = 8)
	public void T8_switchAlertAccept() throws InterruptedException, AWTException, IOException{
		createTest("T8_switchAlertAccept", "Validating AlertAccept");
		driver.findElement(By.id("name")).sendKeys("Sneha");
		driver.findElement(By.id("alertbtn")).click();
		Thread.sleep(3000);
		exttest.info("Alert message captured");
		captureBufferedImage("T8_switchAlertMessage");
		Thread.sleep(3000);
		Alert alerttoswitch = driver.switchTo().alert();
		String msg = driver.switchTo().alert().getText();
		alerttoswitch.accept();
		exttest.info("Alert Message: "+msg);
		System.out.println("Alert accept Message - "+msg);
		try{
			Assert.assertTrue(msg.contains("Sneha"));
			exttest.pass("Alert Success");
			captureScreenshot("T8_switchAlertAccept",true);
		}catch(AssertionError e) {
			exttest.fail("Alert Fail");
			captureScreenshot("T8_switchAlertAccept" , false);
		}
	}

	@Test(priority = 9)
	public void T9_switchAlertDismiss() throws InterruptedException, IOException {
		createTest("T9_switchAlertDismiss", "Validating SwitchAlertDismiss");
		driver.findElement(By.id("name")).sendKeys("Sneha");
		driver.findElement(By.id("confirmbtn")).click();
		Thread.sleep(5000);
		exttest.info("Alert Dismiss Message captured");
		captureBufferedImage("T9_switchAlertDismiss");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String msgdiss = driver.switchTo().alert().getText();
		Thread.sleep(2000);
		alert.dismiss();
		exttest.info("Alert Dismiss Message: "+msgdiss);
		System.out.println("Alert dismiss Message - "+msgdiss);
		try{
			Assert.assertTrue(msgdiss.contains("Sneha") );
			exttest.pass("Alert Dismiss Success");
			captureScreenshot("T9_switchAlertDismiss" , true);
		}catch(AssertionError e) {
			exttest.fail("Alert Dismiss Fail");
			captureScreenshot("T9_switchAlertDismiss" , false);
		}

	}

	@Test(priority = 10)
	public void T10_checkboxDeselection() throws InterruptedException {
		createTest("T10_checkboxDeselection", "Validating CheckboxDeselection");

		WebElement checkbox1 = driver.findElement(By.id("checkBoxOption1"));
		checkbox1.click();
		try{
			Assert.assertTrue(checkbox1.isSelected());
			exttest.pass("Checkbox1 is selected");
			captureScreenshot("T10_checkboxSelected",true);
		}catch(AssertionError e) {
			exttest.fail("Checkbox1 not selected");
			captureScreenshot("T10_checkboxNotSelected",false);
		}
		checkbox1.click();
		//captureScreenshot("T10_checkboxDeselected",true);
		try{
			Assert.assertFalse(checkbox1.isSelected() );
			exttest.pass("Checkbox1 successfully Deselected");
			captureScreenshot("T10_checkboxDeselection" , true);
		}catch(AssertionError e) {
			exttest.fail("Checkbox1 not Deselected");
			captureScreenshot("T10_checkboxDeselection" , false);
		}

	}

	@Test(priority = 11)
	public void T11_dropdownVerification() throws InterruptedException, IOException {
		createTest("T11_dropdownVerification", "Validating dropDown");
		Thread.sleep(3000);
		WebElement drp = driver.findElement(By.xpath("//*[@name='dropdown-class-example']"));
		Thread.sleep(2000);
		drp.click();

		Select drpdwn = new Select(drp);
		exttest.info("DrpDown Options captured");
		captureBufferedImage("T11_dropdownOptions");
		Thread.sleep(3000);
		List<WebElement> drpOptions = drpdwn.getOptions();

		String expectedOptions[] = {"Select","Option1", "Option2", "Option3"};
		exttest.info("Expected Options to be present: \nSelect, \nOption1, \n Option2, \nOption3" );

		for (int i=0 ;i<drpOptions.size();i++) {
			String actualOptions = drpOptions.get(i).getText();
			System.out.println(actualOptions);
			try{
				Assert.assertEquals(actualOptions,expectedOptions[i]);
				exttest.pass("Expected Options Found: "+actualOptions);
				//captureScreenshot("T11_dropdownVerification" , true);
			}catch(AssertionError e) {
				exttest.fail("Expected option not found: " + actualOptions);
				captureScreenshot("T11_dropdownVerification" , false);
			}
		}


	}

	@Test(priority = 12)
	public void T12_mouseOverAction() throws InterruptedException, IOException {
		createTest("T12_mouseOverAction", "Validating mouseOver");

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,1200)");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement mouseHoverButton = driver.findElement(By.id("mousehover"));
		Actions action = new Actions(driver);
		action.moveToElement(mouseHoverButton).perform();
		WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Top")));
		exttest.info("MouseOverAction Captured");
		captureBufferedImage("T12_mouseOverAction");
		option.click();
		try{
			Assert.assertTrue(driver.getCurrentUrl().contains("top"));
			exttest.pass("Mouse Hover action Passed moved to Top");
			captureScreenshot("T12_mouseOverAction" , true);
		}catch(AssertionError e) {
			exttest.fail("Mouse Hover action failed");
			captureScreenshot("T12_mouseOverAction" , false);
		}
	}

	@Test(priority = 13)
	public void T13_iframeHandling() throws InterruptedException {
		createTest("T13_iframeHandling", "Validating IFrame");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,1350)");
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = wait1.until(ExpectedConditions.presenceOfElementLocated(By.id("courses-iframe")));
		driver.switchTo().frame(iframe);
		exttest.pass("IFrame switch success");
		JavascriptExecutor ifr = (JavascriptExecutor)driver;
		ifr.executeScript("window.scrollBy(0,280)");
		String iframeText = driver.findElement(By.xpath("//*[@id='carousel-example-generic']/div/div/div/div/h2/span/strong")).getText();
		System.out.println("IFrame Teext: " +iframeText);
		try{
			Assert.assertTrue(iframeText.contains("Learn"));
			exttest.pass("Iframe Text Match and Value is: "+iframeText);
			captureScreenshot("T13_iframeHandling" , true);
		}catch(AssertionError e) {
			exttest.fail("Iframe text mismatch and expected Value is: " +iframeText);
			captureScreenshot("T13_iframeHandling" , false);
		}
		driver.switchTo().defaultContent();

	}

	@Test(priority = 14)
	public void T14_webTableFixedHeader() throws InterruptedException {
		createTest("T14_webTableFixedHeader", "Validating WebTable");
		JavascriptExecutor ifr = (JavascriptExecutor)driver;
		ifr.executeScript("window.scrollBy(10,490)");

		//14.1 to print all values:-
		WebElement table = driver.findElement(By.xpath("//*[@class='tableFixHead']"));
		exttest.info("WebTableFixedHeader");
		captureScreenshot("T14.1_webTableFixedHeader");
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		exttest.info("Web Table Fixed Header Cell Value:");
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			if (!cells.isEmpty()) {
				String rowContent = cells.get(0).getText();
				for (int i = 1; i < cells.size(); i++) {
					rowContent += "\t" + cells.get(i).getText();
				}
				exttest.info(rowContent);
			}
		}
		//		for (WebElement row : rows) {
		//			List<WebElement> cells = row.findElements(By.tagName("td"));
		//			for (int i = 0; i < cells.size(); i++) {
		//				exttest.info(cells.get(i).getText());
		//				System.out.print(cells.get(i).getText());
		//				exttest.info("Web Table Fixed Header cell Value: ")
		//				if (i < cells.size() - 1) {
		//					exttest.info("\t");
		//					System.out.print("\t");
		//				}
		//			}
		//			exttest.info("");
		//			System.out.println();
		//		}

		//14.2 to find expect name present in table:-
		String[] expectedNames = {"Alex", "Ivory", "Raymond", "Ronaldo", "Smith"};
		List<WebElement> nameColumn = driver.findElements(By.xpath("//*[@class='tableFixHead']//tr/td[1]"));
		for (String expectedName : expectedNames) {
			boolean nameFound = false;
			for (WebElement name : nameColumn) {
				String actualName = name.getText();
				if (actualName.equals(expectedName)) {
					nameFound = true;
					break;
				}
			}
			try{
				Assert.assertTrue(nameFound);
				exttest.pass("Expected Name" + expectedName + "Found");
			}catch(AssertionError e) {
				exttest.fail("Expected name not found in the table: " + expectedName);
				captureScreenshot("T14_webTableFixedHeader" , false);
			}
		}

		Thread.sleep(2000);

		List<WebElement> amounts = driver.findElements(By.xpath("//*[@class='tableFixHead']//tr/td[4]"));
		int totalAmount = 0;
		for (WebElement amount : amounts) {
			String amountval = amount.getText();
			totalAmount += Integer.parseInt(amountval);
		}
		try{
			Assert.assertEquals(totalAmount, 296, "Total amount mismatch");
			exttest.info("Total Amount Calculated from Amount Column: "+totalAmount);

			WebElement ele = driver.findElement(By.xpath("//*[@class='totalAmount']"));
			exttest.info("Amount Displayed in Table: "+ele.getText());
			JavascriptExecutor amthigh = (JavascriptExecutor) driver;
			amthigh.executeScript("arguments[0].style.backgroundColor = 'yellow';", ele);
			Thread.sleep(2000);
			captureScreenshot("T14.3_webTableAmount" , true);
		}catch(AssertionError e) {
			exttest.fail("Total amount mismatch");
			captureScreenshot("T14.3_webTableAmount" , false);
		}

	}
}
