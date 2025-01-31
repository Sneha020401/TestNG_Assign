package pages;

import org.openqa.selenium.WebDriver;

import utils.Const;

public class CartPage {
    private WebDriver driver;

    private String quantityvalue;


    public CartPage(WebDriver driver)  {
        this.driver = driver;
    }


    public void increaseQuantity(int countneed) throws InterruptedException {
        driver.findElement(Const.quantityf).clear();
        driver.findElement(Const.quantityf).sendKeys(String.valueOf(countneed));
        quantityvalue = Integer.toString(countneed);
        Thread.sleep(2000);
    }
    public void updatedCart() throws InterruptedException{
        driver.findElement(Const.updateCart).click();
        Thread.sleep(2000);
    }

    public void proceedToCheckout() throws InterruptedException{
        driver.findElement(Const.checkout).click();
        Thread.sleep(2000);
    }

    public void verifyQuantity(int expectedQuantity) {
        String quantity = driver.findElement(Const.quantityf).getAttribute("value");
        assert Integer.parseInt(quantity) == expectedQuantity : "Quantity mismatch";
    }
    public String getQuantity() {
    	return quantityvalue;
    }
}
