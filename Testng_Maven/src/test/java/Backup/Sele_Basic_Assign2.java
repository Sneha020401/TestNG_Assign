package Backup;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseSetup;
import utils.ExcelUtils;

public class Sele_Basic_Assign2 extends BaseSetup {
	@Override
	public void captureScreenshot(String methodName){
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotFolder = "screenshots/Basic_Assign2/";
		String screenshotPath = screenshotFolder + methodName + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".png";
		try {
			FileUtils.copyFile(screenshot, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@BeforeMethod
	public void before() {
		WebDriver driver = getBrowser();
		OpenBrowser(driver, properties.getProperty("url2"));
	}
	@AfterMethod
	public void close() {
		tearDown();
	}
	@Test
    public void testDatePicker() {


        WebElement datepickerLink = driver.findElement(By.linkText("Datepicker"));
        datepickerLink.click();

       driver.findElement(By.xpath("//*[@class='hasDatepicker']")).sendKeys("12/25/2025");
        //dateInput.

        String datepick = driver.findElement(By.xpath("//*[@class='ui-state-default ui-state-active']")).getText();
       // Assert.assertEquals(dateInput.getAttribute("value"), datepick);
        captureScreenshot("testDatePicker");

    }

    @Test
    public void testSelectMenuOptions() throws Exception {

        OpenBrowser(driver, properties.getProperty("url2"));
       // WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        String FilePath = properties.getProperty("excelFilePath");

        List<List<String>> menuOptions = ExcelUtils.readExcelData(FilePath, "MenuOptions");

        for (int i = 0; i < menuOptions.size(); i++) {
            List<String> row = menuOptions.get(i);

            String speed = row.get(0);
            String file = row.get(1);
            String number = row.get(2);
            String title = row.get(3);

            try {

            	driver.findElement(By.linkText("Selectmenu")).click();
            	JavascriptExecutor jse = (JavascriptExecutor)driver;
        		jse.executeScript("window.scrollBy(0,350)");
        		System.out.println("working before");
                WebElement speedSelectElement = driver.findElement(By.xpath("//*[@id=\"speed-button\"]/span[1]"));
                speedSelectElement.click();
                Thread.sleep(2000);
                System.out.println("working");
                Select speedSelect = new Select(speedSelectElement);
                Thread.sleep(2000);
                speedSelect.selectByVisibleText(speed);

                WebElement fileSelectElement = driver.findElement(By.xpath("//*[@id='file-button']/span[2]"));
                fileSelectElement.click();
                Select fileSelect = new Select(fileSelectElement);
                fileSelect.selectByVisibleText(file);

                WebElement numberField = driver.findElement(By.xpath("//*[@id='number-button']/span[2]"));
                numberField.click();
                Select numSelect = new Select(numberField);
                numSelect.selectByVisibleText(number);

                WebElement titleField = driver.findElement(By.xpath("//*[@id='title-button']/span[2]"));
                titleField.click();
                Select titleSelect = new Select(titleField);
                titleSelect.selectByVisibleText(title);

                captureScreenshot("Row_" + (i + 1)+"_SelectOptions");

                Assert.assertEquals(speedSelect.getFirstSelectedOption().getText(), speed, "Speed option mismatch");
                Assert.assertEquals(fileSelect.getFirstSelectedOption().getText(), file, "File option mismatch");
                Assert.assertEquals(numSelect.getFirstSelectedOption().getText(), number, "Number field mismatch");
                Assert.assertEquals(titleSelect.getFirstSelectedOption().getText(), title, "Title field mismatch");

                ExcelUtils.writeStatusToExcel(FilePath, "MenuOptions", i + 1, 4, "Pass");
            } catch (Exception e) {
                // In case of failure, update Excel with "Fail" and capture the screenshot
                ExcelUtils.writeStatusToExcel(FilePath, "MenuOptions", i + 1, 4, "Fail");
                captureScreenshot("Failure_Row_" + (i + 1));
                e.printStackTrace();
                throw e;
            }
        }

        driver.quit();
    }
}
