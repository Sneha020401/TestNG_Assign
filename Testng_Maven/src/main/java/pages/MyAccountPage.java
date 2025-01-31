package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Const;

public class MyAccountPage {
    private WebDriver driver;



    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void viewOrderHistory() {
        driver.findElement(Const.orderHistoryLink).click();
    }
    public void myacctClick() {
        driver.findElement(Const.myaccountBtn).click();
    }
    public void myorderClick() {
        driver.findElement(Const.myorders).click();
    }
    public List<String> getAllOrderIds() {
    	List<String> orderIds = new ArrayList<>();
    	List<WebElement> orderLinks = driver.findElements(By.xpath("//tr/td/a[contains(@href, 'orderId')]"));
        for (WebElement orderLink : orderLinks ) {
            String orderId = orderLink.getText();
            orderIds .add(orderId);
        }
        return orderIds;
    }
    public boolean verifyOrderId(OrderConfirmationPage opage) {
    	String orpageid = opage.getOrderId();
        List<String> allOrderIds = getAllOrderIds();
        return allOrderIds.contains(orpageid);
    }
    public void clickLastOrderId() {
        WebElement lastOrderLink = driver.findElement(Const.lastorderLink);
        lastOrderLink.click();
    }



}
