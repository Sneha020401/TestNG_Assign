package Backup;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.devtools.v107.page.model.BackForwardCacheNotRestoredReason;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseSetup;
import pages.CartPage;
import pages.CategoryPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.ItemPage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.OrderConfirmationPage;
import pages.ProductPage;

public class SeleAdvance_Assign4 extends BaseSetup {
	@Override
	public void captureScreenshot(String methodName){
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotFolder = "screenshots/Advance_Assign4/";
		String screenshotPath = screenshotFolder + methodName + "_" + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".png";
		try {
			FileUtils.copyFile(screenshot, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.captureScreenshot(methodName);

	}

    @Test
    public void checkout() throws InterruptedException {
    	OpenBrowser(driver, properties.getProperty("url1"));
    	
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        ItemPage itemPage = new ItemPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver);

        captureScreenshot("homePage");
        homePage.clickLogin();

        
        captureScreenshot("loginPage");
        loginPage.login("Snake0119", "abcd123");
        
        captureScreenshot("categoryPage");
        categoryPage.selectCategory("dogs");
        
        captureScreenshot("productPage");
        String actualprd = driver.findElement(By.xpath("//*[@id='Catalog']/table/tbody/tr[3]/td/a")).getText();
        
        Thread.sleep(2000);
        productPage.selectProduct("Poodle");
        
        
        String expectedprd = driver.findElement(By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td[2]")).getText();
        productPage.VerifyProductID(actualprd,expectedprd);
        captureScreenshot("productIDPage");
        
		itemPage.selectItem("EST-8");
		captureScreenshot("itemPage");
		itemPage.addItemToCart();

        //cartPage.addProductToCart();
		captureScreenshot("cartPage");
        cartPage.increaseQuantity(2);
        cartPage.verifyQuantity(2);
        cartPage.updatedCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.enterCardType("MasterCard");
        checkoutPage.enterCardNumber("234 9999 9231 9999");
        checkoutPage.enterExpiryDate("11/2024");
        
        checkoutPage.billDetails("Sneha", "P", "Buddhar ext", "east", "chennai", "Tamil Nadu", "789654", "India");
        Map<String, String> billingDetails = checkoutPage.getBillingDetails();
        Thread.sleep(2000);
        checkoutPage.clickship();
        checkoutPage.clickContinue();
        
        checkoutPage.shipDetails("Sneha", "P", "gandhi ext", "west", "adayar", "Tamil Nadu", "989454", "India");
        Thread.sleep(2000);
                
        
        Map<String, String> shippingDetails = checkoutPage.getShippingDetails();
        
        Thread.sleep(2000);
        
        checkoutPage.clickContinue();
        captureScreenshot("checkoutPage");
        Thread.sleep(2000);
        checkoutPage.confirmClick();
        Thread.sleep(2000);
        captureScreenshot("orderConfirmationPage");
        String actualmsg = driver.findElement(By.xpath("//*[@id='Content']/ul/li")).getText();
        String expectedmsg = "Thank you, your order has been submitted.";
        Assert.assertEquals(actualmsg, expectedmsg, "order not placed");
        
        orderConfirmationPage.verifypaymentDetails(checkoutPage);
        orderConfirmationPage.verifyBillDetails(billingDetails);
        orderConfirmationPage.verifyShipDetails(shippingDetails);
        orderConfirmationPage.verifyItemDetails(itemPage);
        orderConfirmationPage.verifyQtyDetails(cartPage);
        
        
        Thread.sleep(2000);
        myAccountPage.myacctClick();
        captureScreenshot("myAccountPage");
        
        myAccountPage.myorderClick();
        captureScreenshot("myOrderPage");
        
        
        myAccountPage.clickLastOrderId();
        captureScreenshot("orderedDetailsPage");
        
        
        orderConfirmationPage.verifypaymentDetails(checkoutPage);
        orderConfirmationPage.verifyBillDetails(billingDetails);
        orderConfirmationPage.verifyShipDetails(shippingDetails);
        orderConfirmationPage.verifyItemDetails(itemPage);
        orderConfirmationPage.verifyQtyDetails(cartPage);
        
        driver.findElement(By.linkText("Sign Out"));

        
    }
}
