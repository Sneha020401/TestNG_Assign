package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Const;

public class LoginPage {
    private WebDriver driver;



    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(Const.UserName).sendKeys(username);

        WebElement paword = driver.findElement(Const.PassWord);
        	paword.clear();
        paword.sendKeys(password);
        driver.findElement(Const.LoginButton).click();
    }
}

