package Sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseSetup;


public class SeleAdv_Assign2 extends BaseSetup {

    @Test
    public void loginTest() throws InterruptedException, IOException {

        OpenBrowser(driver, properties.getProperty("url1"));
        String excelFilePath = properties.getProperty("excelFilePath");
		FileInputStream fis = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int noofrows=sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(int i=1;i<=noofrows;i++) {
        driver.findElement(By.linkText("Sign In")).click();
        Row row = sheet.getRow(i);
		String usern = row.getCell(0).getStringCellValue();
		String paword = row.getCell(1).getStringCellValue();
		driver.findElement(By.name("username")).sendKeys(usern);
		Thread.sleep(2000);
		WebElement pword=driver.findElement(By.name("password"));
		pword.clear();
		pword.sendKeys(paword);
		Thread.sleep(2000);
        driver.findElement(By.name("signon")).click();
        String fname = row.getCell(2).getStringCellValue();
        String expected = "Welcome " +fname +"!";
        String actual = driver.findElement(By.id("WelcomeContent")).getText();
        Assert.assertEquals(actual, expected, "Login invalid");
        if (row.getCell(0).getStringCellValue().equals(usern)) {
        	captureScreenshot("login_success_" +fname);
            Thread.sleep(2000);
			Cell successCell = row.createCell(16, CellType.STRING);
			if (successCell != null) {
                successCell.setCellValue("");  
            }
			successCell.setCellValue("Login Success");
		}
        
        driver.findElement(By.linkText("Sign Out")).click();
		Thread.sleep(2000);
    }
		fis.close();

		FileOutputStream fos = new FileOutputStream(excelFilePath);
		wb.write(fos);
		fos.close();
		wb.close();
		tearDown();
}
}

