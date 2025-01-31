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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseSetup;


public class SeleAdv_Assign1 extends BaseSetup {

	@Test

	public void fillRegistrationForm() throws IOException, InterruptedException {
		OpenBrowser(driver, properties.getProperty("url1"));


		String excelFilePath = properties.getProperty("excelFilePath");
		FileInputStream fis = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int noofrows=sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(int i=1;i<=noofrows;i++) {
			driver.findElement(By.linkText("Sign In")).click();
			driver.findElement(By.linkText("Register Now!")).click();
			Thread.sleep(2000);
			Row row = sheet.getRow(i);
			String usern = row.getCell(0).getStringCellValue();
			String password = row.getCell(1).getStringCellValue();
			String fname = row.getCell(2).getStringCellValue();
			String lname = row.getCell(3).getStringCellValue();
			String email = row.getCell(4).getStringCellValue();
			int phone = (int) row.getCell(5).getNumericCellValue();
			String add1 = row.getCell(6).getStringCellValue();
			String add2 = row.getCell(7).getStringCellValue();
			String city = row.getCell(8).getStringCellValue();
			String state = row.getCell(9).getStringCellValue();
			int zip = (int) row.getCell(10).getNumericCellValue();
			String country = row.getCell(11).getStringCellValue();
			String lang =row.getCell(12).getStringCellValue().toLowerCase();
			//lang = lang.toLowerCase();
			String fav =row.getCell(13).getStringCellValue().toUpperCase();
			//fav = fav.toUpperCase();

			driver.findElement(By.name("username")).sendKeys(usern);
			WebElement pword=driver.findElement(By.name("password"));
			pword.sendKeys(password);
			WebElement repeat=driver.findElement(By.name("repeatedPassword"));
			repeat.sendKeys(password);
			if (pword.getAttribute("value").equals(repeat.getAttribute("value")))  {
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
				WebElement paword=driver.findElement(By.name("password"));
				paword.clear();
				paword.sendKeys(password);
				driver.findElement(By.name("signon")).click();
				String expected = "Welcome " +fname +"!";
				String actual = driver.findElement(By.id("WelcomeContent")).getText();
				Assert.assertEquals(actual, expected, "Login invalid");
				if (row.getCell(0).getStringCellValue().equals(usern)) {
					captureScreenshot("register_success_" +fname);
					Thread.sleep(2000);
					Cell successCell = row.createCell(15, CellType.STRING);
					if (successCell != null) {
						successCell.setCellValue("");  
					}
					successCell.setCellValue("Registration Success");
				}


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
