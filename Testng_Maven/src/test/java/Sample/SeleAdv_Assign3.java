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


public class SeleAdv_Assign3 extends BaseSetup {

	@Test
	public void verifyUserDetails() throws InterruptedException, IOException {
		OpenBrowser(driver, properties.getProperty("url1"));
		String excelFilePath = properties.getProperty("excelFilePath");
		FileInputStream fis = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int noofrows=sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(int i=1;i<=noofrows;i++) {


			driver.findElement(By.linkText("Sign In")).click();
			Row row = sheet.getRow(i);
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
			String lang =row.getCell(12).getStringCellValue();
			String fav =row.getCell(13).getStringCellValue();
			String usern = row.getCell(0).getStringCellValue();
			String paword = row.getCell(1).getStringCellValue();
			driver.findElement(By.name("username")).sendKeys(usern);
			Thread.sleep(2000);
			WebElement pword=driver.findElement(By.name("password"));
			pword.clear();
			pword.sendKeys(paword);
			Thread.sleep(2000);
			driver.findElement(By.name("signon")).click();
			Thread.sleep(2000);
			driver.findElement(By.linkText("My Account")).click();
			String unameDis = driver.findElement(By.xpath("//*[@id=\"Catalog\"]/form/table[1]/tbody/tr[1]/td[2]")).getText();
			String fnameDis = driver.findElement(By.name("account.firstName")).getAttribute("value");
			String lnameDis = driver.findElement(By.name("account.lastName")).getAttribute("value");
			String emailDis = driver.findElement(By.name("account.email")).getAttribute("value");
			String phoneDis = driver.findElement(By.name("account.phone")).getAttribute("value");
			String add1Dis = driver.findElement(By.name("account.address1")).getAttribute("value");
			String add2Dis = driver.findElement(By.name("account.address2")).getAttribute("value");
			String cityDis = driver.findElement(By.name("account.city")).getAttribute("value");
			String stateDis = driver.findElement(By.name("account.state")).getAttribute("value");
			String zipDis = driver.findElement(By.name("account.zip")).getAttribute("value");
			String countryDis = driver.findElement(By.name("account.country")).getAttribute("value");
			String langDis = driver.findElement(By.name("account.languagePreference")).getAttribute("value");
			
			String favDis = driver.findElement(By.name("account.favouriteCategoryId")).getAttribute("value");
			Assert.assertEquals(unameDis, usern, "Username mismatch");
			
			
			Assert.assertEquals(fnameDis, fname, "First Name mismatch");
			Assert.assertEquals(lnameDis, lname, "Last Name mismatch");
			Assert.assertEquals(emailDis, email, "Email mismatch");
			Assert.assertEquals(phoneDis, String.valueOf(phone), "Phone mismatch");
			Assert.assertEquals(add1Dis, add1, "Address1 mismatch");
			Assert.assertEquals(add2Dis, add2, "Address2 mismatch");
			Assert.assertEquals(cityDis, city, "City mismatch");
			Assert.assertEquals(stateDis, state, "State mismatch");
			Assert.assertEquals(zipDis, String.valueOf(zip), "Zip mismatch");
			Assert.assertEquals(countryDis, country, "Country mismatch");
			Assert.assertEquals(langDis, lang, "Lang Pref mismatch");
			Assert.assertEquals(favDis, fav, "Favourite Category mismatch");
			WebElement listchecked = driver.findElement(By.name("account.listOption"));
			boolean listverify = listchecked.isSelected();
			Assert.assertTrue(listverify, "The 'Enable MyList' checkbox is not selected.");

			WebElement bannerchecked = driver.findElement(By.name("account.listOption"));
			boolean bannerverify = bannerchecked.isSelected();
			Assert.assertTrue(bannerverify, "The 'Enable MyBanner' checkbox is not selected.");
			Thread.sleep(2000);
			if (row.getCell(0).getStringCellValue().equals(usern)) {
				captureScreenshot("verify_success_" +usern);
				Thread.sleep(2000);
				Cell successCell = row.createCell(17, CellType.STRING);
				if (successCell != null) {
	                successCell.setCellValue("");  
	            }
				successCell.setCellValue("Verification Success");
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
