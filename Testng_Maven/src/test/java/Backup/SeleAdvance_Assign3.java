package Backup;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseSetup;
import utils.ExcelUtils;

public class SeleAdvance_Assign3 extends BaseSetup{
	@Override
	public void captureScreenshot(String methodName){
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotFolder = "screenshots/Advance_Assign3/";
		String screenshotPath = screenshotFolder + methodName + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".png";
		try {
			FileUtils.copyFile(screenshot, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.captureScreenshot(methodName);

	}
	@Test
    public void verifyUserDetails() throws InterruptedException, IOException {

        OpenBrowser(driver, properties.getProperty("url1"));

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

            driver.findElement(By.linkText("Sign In")).click();
            Thread.sleep(2000);

            driver.findElement(By.name("username")).sendKeys(usern);
            WebElement pword = driver.findElement(By.name("password"));
            pword.clear();
            pword.sendKeys(paword);
            driver.findElement(By.name("signon")).click();
            Thread.sleep(2000);

            driver.findElement(By.linkText("My Account")).click();
            Thread.sleep(2000);

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
            Assert.assertEquals(phoneDis, phone, "Phone mismatch");
            Assert.assertEquals(add1Dis, add1, "Address1 mismatch");
            Assert.assertEquals(add2Dis, add2, "Address2 mismatch");
            Assert.assertEquals(cityDis, city, "City mismatch");
            Assert.assertEquals(stateDis, state, "State mismatch");
            Assert.assertEquals(zipDis, zip, "Zip mismatch");
            Assert.assertEquals(countryDis, country, "Country mismatch");
            Assert.assertEquals(langDis, lang, "Lang Pref mismatch");
            Assert.assertEquals(favDis, fav, "Favourite Category mismatch");

            // Verify 'Enable MyList' checkbox
            WebElement listchecked = driver.findElement(By.name("account.listOption"));
            boolean listverify = listchecked.isSelected();
            Assert.assertTrue(listverify, "The 'Enable MyList' checkbox is not selected.");

            // Verify 'Enable MyBanner' checkbox
            WebElement bannerchecked = driver.findElement(By.name("account.listOption"));
            boolean bannerverify = bannerchecked.isSelected();
            Assert.assertTrue(bannerverify, "The 'Enable MyBanner' checkbox is not selected.");

            if (usern.equals(row.get(0))) {
                captureScreenshot("verify_success_" + usern);
                Thread.sleep(2000);

                ExcelUtils.writeStatusToExcel(excelFilePath, "user", i + 1, 16, "Verification Success");
            }

            driver.findElement(By.linkText("Sign Out")).click();
            Thread.sleep(2000);
        }

        ExcelUtils.closeExcelFile(excelFilePath);
        tearDown();
    }
}

