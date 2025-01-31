package Assignments;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.BaseSetup;
import utils.Const;
import utils.ExcelUtils;

public class Sele_Basic_Assign2 extends BaseSetup {
	public Sele_Basic_Assign2() {
		String htmlName = "SeleBasicAssign2.html";
		BaseSetup.initializeExtentReport(htmlName);
	}

	@BeforeMethod
	public void setupClass() {
		System.out.println("WebDriver initialized for the class.");
		OpenBrowser(driver, properties.getProperty("url2"));
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign2/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign2/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign2/" + methodName + ".png";
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
			String screenshotPath = "./test-output/screenshots/Basic_Assign2/" + methodName + ".png";
			File buffdestn = new File(screenshotPath);
			ImageIO.write(src, "png", buffdestn);
			exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(buffdestn.getAbsolutePath()).build());

		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void T1_SelectMenu() throws InterruptedException, Exception {

		createTest("T1_SelectMenu", "Validating Select Menu Option");



		exttest.info("Select menu");
		driver.findElement(By.linkText("Selectmenu")).click();
		exttest.pass("Navigated to Select menu");


		exttest.info("Selecting menu using MenuOptions Sheet");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,350)");

		WebDriverWait waitSel = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = driver.findElement(By.xpath("//*[@class='demo-frame']"));
		driver.switchTo().frame(iframe);

		String FilePath = properties.getProperty("excelFilePath");
		String sheetName = "MenuOptions";
		List<List<String>> menuOptions = ExcelUtils.readExcelData(FilePath, sheetName);

		for (int i = 0; i < menuOptions.size(); i++)  {
			List<String> row = menuOptions.get(i);
			String speed = row.get(0);
			String file = row.get(1);
			String number = row.get(2);
			String title = row.get(3);
			try {

				WebElement speedSelectElement = driver.findElement(By.xpath("//*[contains(@id,'speed-button')]"));
				speedSelectElement.click();
				WebElement speedSel = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'speed-menu')]")));
				WebElement speedOption = speedSel.findElement(By.xpath("//*[contains(@role,'option') and text()='"+speed+"']"));
				speedOption.click();
				WebElement speedselectedText = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'ui-selectmenu-text') and text()='"+speed+"']")));
				String speedOptionText = speedselectedText.getText();
				Thread.sleep(2000);
				try{
					Assert.assertEquals(speedOptionText, speed);
				}catch(AssertionError e) {
					exttest.fail("Speed option mismatch");
					captureScreenshot("T1_speedSelect",false);
				}

				WebElement fileSelectElement = waitSel.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'files-button')]")));
				fileSelectElement.click();
				WebElement fileSel = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'files-menu')]")));
				WebElement fileOption = fileSel.findElement(By.xpath("//*[contains(@role,'option') and text()='"+file+"']"));
				fileOption.click();
				WebElement fileselectedText = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'ui-selectmenu-text') and text()='"+file+"']")));
				String fileOptionText = fileselectedText.getText();
				Thread.sleep(2000);
				try {
					Assert.assertEquals(fileOptionText, file);
				}
				catch(AssertionError e) {
					exttest.fail("File option mismatch");
					captureScreenshot("T1_fileSelect",false);
				}

				WebElement numberSelectElement = waitSel.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'number-button')]")));
				numberSelectElement.click();
				WebElement numberSel = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'number-menu')]")));
				WebElement numberOption = numberSel.findElement(By.xpath("//*[contains(@role,'option') and text()='"+number+"']"));
				numberOption.click();
				WebElement numberselectedText = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'ui-selectmenu-text') and text()='"+number+"']")));
				String numberOptionText = numberselectedText.getText();
				Thread.sleep(2000);
				try {
					Assert.assertEquals(numberOptionText, number);
				}
				catch(AssertionError e) {
					exttest.fail("Number option mismatch");
					captureScreenshot("T1_numSelect",false);
				}

				WebElement titleSelectElement = waitSel.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'salutation-button')]")));
				titleSelectElement.click();
				WebElement titleSel = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'salutation-menu')]")));
				WebElement titleOption = titleSel.findElement(By.xpath("//*[contains(@role,'option') and text()='"+title+"']"));
				titleOption.click();
				WebElement titleselectedText = waitSel.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'ui-selectmenu-text') and text()='"+title+"']")));
				String titleOptionText = titleselectedText.getText();
				Thread.sleep(2000);
				try {
					Assert.assertEquals(titleOptionText, title);
				}
				catch(AssertionError e) {
					exttest.fail("Title option mismatch");
					captureScreenshot("T1_titleSelect",false);
				}

				exttest.info("<b>Selected options:</b> " + "<br>" +
						"<b>Speed:</b> " + speedOptionText + "<br>" +
						"<b>File:</b> " + fileOptionText + "<br>" +
						"<b>Number:</b> " + numberOptionText + "<br>" +
						"<b>Salutation:</b> " + titleOptionText);

				captureScreenshot("Row_" + (i + 1)+"_SelectOptions",true);
				ExcelUtils.writeStatusToExcel(FilePath, "MenuOptions", i + 1, 4, "Pass");
			} catch (Exception e) {

				ExcelUtils.writeStatusToExcel(FilePath, "MenuOptions", i + 1, 4, "Fail");
				captureScreenshot("Failure_Row_" + (i + 1),false);
				e.printStackTrace();
			}
		}
	}

	@Test
	public void T2_DatePicker() throws InterruptedException {

		createTest("T2_DatePicker", "Validating Date");
		exttest.info("DatePicker Menu");
		WebElement datepickerLink = driver.findElement(By.linkText("Datepicker"));
		datepickerLink.click();
		exttest.pass("Navigated to datePicker Menu");
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='demo-frame']")));
		driver.switchTo().frame(iframe);

		exttest.info("Given Date: 12/25/2024");
		WebElement dateInput = driver.findElement(Const.DateInput);
		dateInput.sendKeys("12/25/2024");
		exttest.info("Expected Date: December/25/2024");

		WebElement date = driver.findElement(Const.Date);
		String dateTxt = date.getText();

		WebElement month = driver.findElement(Const.Month);
		String monthTxt = month.getText();

		WebElement year = driver.findElement(Const.Year);
		String yearTxt = year.getText();

		String actualDate = monthTxt + "/" +dateTxt +"/" +yearTxt;
		exttest.info("Actual Date: "+actualDate);

		System.out.println(dateTxt);
		System.out.println(monthTxt);
		System.out.println(yearTxt);

		try{
			Assert.assertEquals(actualDate, "December/25/2024");
			exttest.pass("Date is Matched");
			captureScreenshot("T2_DatePicker" ,true);
		}catch(AssertionError e) {
			System.out.println("Date is mismatch");
			captureScreenshot("T2_DatePicker" ,false);
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(Const.Date));
		dateInput.clear();
		exttest.info("Date is Cleared");
		captureScreenshot("Datecleared" ,true);

		wait.until(ExpectedConditions.elementToBeClickable(date));
		date.click();
		exttest.info("Default Date is clicked");
		captureScreenshot("DateClicked" ,true);
		dateInput.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(Const.Date));
		dateInput.clear();

		WebDriverWait waitdate = new WebDriverWait(driver, Duration.ofSeconds(40));

		for (int i = 0; i < 3; i++) {
			WebElement next= waitdate.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class,'next')]")));
			next.click();
		}

		WebElement dateFuture = driver.findElement(By.xpath("//*[contains(@class,'ui-state-default') and text()='"+dateTxt+"']"));
		String dateFutureTxt = dateFuture.getText();
		String monthFuture = driver.findElement(Const.Month).getText();
		String yearFuture = driver.findElement(Const.Year).getText();
		String actualFutureDate = monthFuture +"/" +dateFutureTxt +"/" +yearFuture;

		exttest.info("Month after 3 months forward: " + monthFuture);
		exttest.info("Year after 3 months forward: " + yearFuture);
		exttest.info("Future Date picked: " +actualFutureDate );
		try{
			Assert.assertEquals(actualFutureDate, "March/25/2025");
			exttest.pass("Future Date is Matched");
			captureScreenshot("T2_FutureDatePicker" ,true);
		}catch(AssertionError e) {
			System.out.println("Future Date is mismatch");
			captureScreenshot("T2_FutureDatePicker" ,false);
		}

		waitdate.until(ExpectedConditions.elementToBeClickable(dateFuture));
		dateFuture.click();
		captureScreenshot("FutureDateClicked" ,true);
		dateInput.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(Const.Date));
		dateInput.clear();

		for (int i = 0; i < 3; i++) {
			WebElement pastselect= waitdate.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class,'ui-icon ui-icon-circle-triangle-w')]")));
			pastselect.click();

		}

		WebElement datePast = driver.findElement(By.xpath("//*[contains(@class,'ui-state-default') and text()='"+dateTxt+"']"));
		String datePastTxt = datePast.getText();
		String monthPast = driver.findElement(Const.Month).getText();
		String yearPast = driver.findElement(Const.Year).getText();
		String actualPastDate = monthPast +"/" +datePastTxt +"/" +yearPast;

		exttest.info("Month before 3 months backward: " + monthPast);
		exttest.info("Year before 3 months backward: " + yearPast);
		exttest.info("Past Date picked: " +actualPastDate );
		try{
			Assert.assertEquals(actualPastDate, "December/25/2024");
			exttest.pass("Past Date is Matched");
			captureScreenshot("T2_PastDatePicker" ,true);
		}catch(AssertionError e) {
			System.out.println("Past Date is mismatch");
			captureScreenshot("T2_PastDatePicker" ,false);
		}
		waitdate.until(ExpectedConditions.elementToBeClickable(datePast));
		datePast.click();
		captureScreenshot("PastDateClicked" ,true);



	}

	@Test
	public void T3_TooltipMenu() throws IOException, InterruptedException  {

		createTest("T3_TooltipMenu", "Validating Tooltip Menu");
		exttest.info("<b>Tooltip menu</b>");
		driver.findElement(By.linkText("Tooltip")).click();
		exttest.pass("Navigated to Tooltip menu");

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,350)");

		WebDriverWait waitTool = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = waitTool.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='demo-frame']")));
		driver.switchTo().frame(iframe);

		exttest.info("Hoover over the element");
		WebElement tooltipHover = waitTool.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='age']")));
		Actions action = new Actions(driver);
		action.moveToElement(tooltipHover).perform();
		//		Thread.sleep(4000);
		captureBufferedImage("T3_TooltipMenu",true);
		exttest.pass("Tootltip hooverActionted");

		waitTool.until(ExpectedConditions.visibilityOf(tooltipHover));
		String toolTipTxt = driver.findElement(By.xpath("//*[@role='log']")).getText();
		System.out.print(toolTipTxt);


		try {
			Assert.assertEquals(toolTipTxt, "We ask for your age only for statistical purposes.");
			exttest.pass("Tooltip contains expected text: "+toolTipTxt);
		}
		catch(AssertionError e) {
			exttest.fail("Tooltip is not as expected and expected Test is: "+toolTipTxt);
			captureBufferedImage("T3_TooltipMenu",false);
		}

	}

	@Test
	public void T4_TabsMenu() throws IOException, InterruptedException  {

		createTest("T4_TabsMenu", "Validating Tabs Menu");
		exttest.info("<b>Tabs menu</b>");
		driver.findElement(By.linkText("Tabs")).click();
		exttest.pass("Navigated to Tabs menu");

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,300)");

		WebDriverWait waitTab = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = waitTab.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='demo-frame']")));
		driver.switchTo().frame(iframe);

		waitTab.until(ExpectedConditions.visibilityOfElementLocated(By.id("tabs")));
		String tabName ="";
		for (int i = 1; i <= 3; i++) {
			WebElement tab = waitTab.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ui-id-"+i+"']")));
			tab.click();

			WebElement selectedTab = waitTab.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby='ui-id-"+i+"' and @aria-selected='true']")));
			if (selectedTab != null) {
				WebElement Tabname = waitTab.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby='ui-id-"+i+"']/a")));
				tabName = Tabname.getText();
				exttest.pass("Tabname is : "+tabName);
			}
			WebElement content = waitTab.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tabs-" + i + "']/p")));
			if (!content.getText().isEmpty()) {
				String contentTxt = content.getText();
				//exttest.info(tabName + " contains text: " +contentTxt );
				try {
					Assert.assertTrue(contentTxt.contains("Curabitur"));
					exttest.info("<b>Given unique value:</b> Curabitur");
					exttest.pass("<b>"+tabName+"</b>"+ " contains unique text");
					captureScreenshot("T3_TabsMenu_Tab"+i, true);
				}catch(AssertionError e) {
					exttest.fail(tabName + " not having expected text and actual Text is "+contentTxt);
					captureScreenshot("T3_TabsMenu_Tab"+i, false);
				}
			}Thread.sleep(2000);
		}
	}

	@Test
	public void T5_DroppableMenu() throws IOException, InterruptedException  {

		createTest("T5_DroppableMenu", "Validating Drag and Drop action");
		exttest.info("<b>Droppable Menu</b>");
		driver.findElement(By.linkText("Droppable")).click();
		exttest.pass("Navigated to Droppable menu");

		WebDriverWait waitDrop = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='demo-frame']")));
		driver.switchTo().frame(iframe);

		WebElement drop = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.id("droppable")));

        WebElement drag = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.id("draggable")));
        exttest.info("Before Drag and Drop");
        captureScreenshot("Before_dragNdrop");
        Actions actions = new Actions(driver);
        actions.dragAndDrop(drag, drop).perform();
     //   exttest.pass("dragged and dropped");
        WebElement dropped = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='droppable']/p[text()='Dropped!']")));
        String droppedText = dropped.getText();

        try {
        	Assert.assertEquals(droppedText, "Dropped!");
        	exttest.pass("Drag and Drop action performed successfully");
        	captureScreenshot("T5_DroppableMenu",true);
        }catch(AssertionError e) {
        	exttest.fail("Drag and Drop action not performed");
        	captureScreenshot("T5_DroppableMenu",false);
        }
	}

	@Test
	public void T6_AccordionMenu() throws IOException, InterruptedException  {

		createTest("T6_AccordionMenu", "Validating Tabs Menu");
		exttest.info("<b> Accordion Menu</b>");
		driver.findElement(By.linkText("Accordion")).click();
		exttest.pass("Navigated to Accordion menu");
		JavascriptExecutor jse4 = (JavascriptExecutor)driver;
		jse4.executeScript("window.scrollBy(0,500)");

		WebDriverWait waitDrop = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement iframe = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='demo-frame']")));
		driver.switchTo().frame(iframe);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,630)");

//		List<WebElement> accSections = waitDrop.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='accordion']//h3[contains(@class, 'ui-accordion-header')]")));
//		for (WebElement section : accSections) {

		waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.id("accordion")));
			for(int i=1 ; i<=7; i+=2) {

				WebElement selec = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ui-id-"+i+"']")));
				selec.click();
				System.out.println("i : "+(i));
	            WebElement activeSection = waitDrop.until(ExpectedConditions.visibilityOf(selec));
	            boolean activechk = activeSection.getAttribute("class").contains("ui-state-active");
	            try {
	            	Assert.assertTrue(activechk);
	            	exttest.pass("<b>" + selec.getText() +"</b>" +" is highlighted");
	            }catch(AssertionError e) {
	            	exttest.fail("<b>" + selec.getText() +"</b>" + " is not highlighted");
	            }
	            JavascriptExecutor jse1 = (JavascriptExecutor)driver;
	            jse1.executeScript("window.scrollTo(0, document.body.scrollHeight);");

	            WebElement sectionContent = waitDrop.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ui-id-"+(i+1)+"']/p")));
	            String contentText = sectionContent.getText();
	            System.out.println("i+1 : "+(i+1));
	            Thread.sleep(3000);
	            try {
	            	Assert.assertTrue(contentText.contains("et"));
	            	exttest.pass("<b>" + selec.getText() + " contain the text: </b>"+ "<br>"+contentText);
	            	//Thread.sleep(2000);
	            	captureScreenshot("T6_Accordion_"+selec.getText(), true);
	            	 exttest.pass("Section " + selec.getText() + " validated successfully.");
	            }catch(AssertionError e) {
	            	exttest.fail("<b>" + selec.getText() + " does not contain the text and expected text: </b>"+"<br>"+contentText);
	            	captureScreenshot("T6_Accordion_"+selec.getText(), false);
	            	exttest.fail("Section " + selec.getText() + " not validated successfully.");
	            }



	           JavascriptExecutor jse2 = (JavascriptExecutor)driver;
	           jse2.executeScript("window.scrollTo(0, document.body.scrollHeight);");


	}
}
}