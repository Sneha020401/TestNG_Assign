package pages;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Const;


public class HomePage {
    private WebDriver driver;


  //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogin() {

        WebElement loginButtonElement = driver.findElement(Const.SignIn);
        loginButtonElement.click();
    }

    public void navigateToCatalog() {
    	WebElement catlogEle=driver.findElement(Const.catalogLink);
    	catlogEle.click();
    }
    public void navigateToCart() {
    	WebElement cartEle=driver.findElement(Const.cartLink);
    	cartEle.click();
    }
}