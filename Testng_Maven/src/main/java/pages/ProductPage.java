package pages;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utils.Const;

public class ProductPage {
    private WebDriver driver;


    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectProduct(String productname) {
    	switch (productname) {
        case "Bulldog":
        	driver.findElement(Const.bulldog).click();
        	break;
        case "Poodle":
        	driver.findElement(Const.poodle).click();
        	break;
        case "Dalmation":
        	driver.findElement(Const.dalmation).click();
        	break;
        case "Golden_Retriever":
        	driver.findElement(Const.goldenRetriever).click();
        	break;
        case "Labrador_Retriever":
        	driver.findElement(Const.labradorRetriever).click();
        	break;
        case "Chihuahua":
        	driver.findElement(Const.chihuahua).click();
        	break;
        default:
        	break;
        }
    }
    public void VerifyProductID(String proid, String exppr) {

    	Assert.assertEquals(proid, exppr, "ProductId is mismatch");
    }
}
