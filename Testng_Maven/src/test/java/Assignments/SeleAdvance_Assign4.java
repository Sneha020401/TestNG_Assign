package Assignments;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.devtools.v107.page.model.BackForwardCacheNotRestoredReason;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;

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
	public SeleAdvance_Assign4() {
		String htmlName = "SeleAdvAssign4.html";
		BaseSetup.initializeExtentReport(htmlName);
	}

	@BeforeMethod
	public void setupClass() {
		System.out.println("WebDriver initialized for the class.");
		OpenBrowser(driver, properties.getProperty("url1"));
	}

	@AfterMethod
	public void tearDownMethod() {
		System.out.println("Test method completed.");
	}

	@Override
	public void captureScreenshot(String methodName, boolean isPass){
		String screenshotStatus = isPass ? "Pass" : "Fail";
		String filename = methodName + "_" + screenshotStatus + ".png";

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/Advance_Assign4/" + methodName + ".png";
			File scdestn = new File(screenshotPath);


			FileUtils.copyFile(screenshot, scdestn);
			if (isPass) {
				exttest.pass(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
			} else {
				exttest.fail(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
			}
		} catch (IOException e) {
			exttest.fail(methodName, MediaEntityBuilder.createScreenCaptureFromPath("./test-output/screenshots/Basic_Assign/" + methodName + "_fail.png").build());
			e.printStackTrace();
		}

	}

	@Override
	public void captureScreenshot(String methodName){
		String filename = methodName + "_" + "_info.png";
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = "./test-output/screenshots/Advance_Assign4/" + methodName + ".png";
			File scdestn = new File(screenshotPath);
			FileUtils.copyFile(screenshot, scdestn);

			exttest.info(filename, MediaEntityBuilder.createScreenCaptureFromPath(scdestn.getAbsolutePath()).build());
		}
		catch (IOException e) {
			exttest.fail(methodName, MediaEntityBuilder.createScreenCaptureFromPath("./test-output/screenshots/Basic_Assign/" + methodName + "_fail.png").build());
			e.printStackTrace();
		}

	}



	@Test
	public void checkout() throws InterruptedException {
		createTest("Checkout Process for Pet Store Website", "Order product and verifying order details");
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

		exttest.info("Home Page of Pet Store");
		captureScreenshot("homePage");
		homePage.clickLogin();

		exttest.pass("Navigated to Login Page");
		captureScreenshot("loginPage");
		loginPage.login("Snake0119", "abcd123");


		exttest.pass("Navigated to Category Page");
		captureScreenshot("categoryPage");
		categoryPage.selectCategory("dogs");

		exttest.info("Selected category: Dogs");
		exttest.pass("Navigated to Product Page");

		captureScreenshot("productPage");
		String actualprd = driver.findElement(By.xpath("//*[@id='Catalog']/table/tbody/tr[3]/td/a")).getText();

		Thread.sleep(2000);
		productPage.selectProduct("Poodle");
		exttest.info("Selected product: Poodle");
		exttest.info("ProductID Page");

		exttest.info("Actual Product Id: "+actualprd);

		String expectedprd = driver.findElement(By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td[2]")).getText();
		exttest.info("Expected Product Id: "+expectedprd);
		productPage.VerifyProductID(actualprd,expectedprd);
		exttest.pass("Product Id successfully Verified");


		captureScreenshot("productIDPage");
		exttest.pass("Navigated to Item Page");

		itemPage.selectItem("EST-8");
		exttest.info("Selected Item: EST-8");
		captureScreenshot("itemPage");
		itemPage.addItemToCart();
		exttest.pass("Item Successfully added to cart");

		exttest.pass("Navigated to Cart Page");
		//cartPage.addProductToCart();
		captureScreenshot("cartPage");
		cartPage.increaseQuantity(2);
		cartPage.verifyQuantity(2);
		cartPage.updatedCart();
		cartPage.proceedToCheckout();

		exttest.info("Checkout Page");
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
		exttest.info("Verification of Order details");
		exttest.info("Order Confirmation Page");
		captureScreenshot("orderConfirmationPage");
		String actualmsg = driver.findElement(By.xpath("//*[@id='Content']/ul/li")).getText();
		String expectedmsg = "Thank you, your order has been submitted.";
		exttest.info("Order Verification Message: "+actualmsg);
		Assert.assertEquals(actualmsg, expectedmsg, "order not placed");
		exttest.pass("Order successfully placed");

		orderConfirmationPage.verifypaymentDetails(checkoutPage);
		orderConfirmationPage.verifyBillDetails(billingDetails);
		orderConfirmationPage.verifyShipDetails(shippingDetails);
		orderConfirmationPage.verifyItemDetails(itemPage);
		orderConfirmationPage.verifyQtyDetails(cartPage);
		exttest.pass("Order Successfully verified");

		Thread.sleep(2000);
		myAccountPage.myacctClick();
		exttest.pass("Navigated to My Account Page");
		captureScreenshot("myAccountPage");

		myAccountPage.myorderClick();
		exttest.pass("Navigated to My Order Page");
		captureScreenshot("myOrderPage");


		myAccountPage.clickLastOrderId();
		exttest.pass("Navigated to My order details Page");
		captureScreenshot("orderedDetailsPage");


		orderConfirmationPage.verifypaymentDetails(checkoutPage);
		orderConfirmationPage.verifyBillDetails(billingDetails);
		orderConfirmationPage.verifyShipDetails(shippingDetails);
		orderConfirmationPage.verifyItemDetails(itemPage);
		orderConfirmationPage.verifyQtyDetails(cartPage);
		exttest.pass("Ordered Details Successfully Validated");

		driver.findElement(By.linkText("Sign Out"));


	}
}
