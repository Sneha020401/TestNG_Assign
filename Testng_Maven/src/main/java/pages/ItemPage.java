package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import utils.Const;

public class ItemPage {
	private WebDriver driver;



	public ItemPage(WebDriver driver) {
		this.driver = driver;
	}
	public void addItemToCart() throws InterruptedException {
        driver.findElement(Const.addItemCart).click();
        Thread.sleep(2000);
    }
	public void selectItem(String itId) {
		if(itId.equals("EST-8")) {
			driver.findElement(Const.itemID).click();
		}else {
			System.out.println("itemID invalid");
		}
	}

	public void verifyItemId(String iteid) {
		String itemexpected=driver.findElement(Const.itemID).getText();
		Assert.assertEquals(iteid, itemexpected, "ItemId is mismatch");
	}

	public String getItemid() {
		WebElement itemvalue = driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[26]/td/table/tbody/tr[2]/td[1]/a"));
		String itemidvalue = itemvalue.getText();
		return itemidvalue;
	}
}
