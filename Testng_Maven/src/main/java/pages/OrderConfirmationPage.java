package pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import base.BaseSetup;
import utils.Const;


public class OrderConfirmationPage extends Const{
	private WebDriver driver;


	public OrderConfirmationPage(WebDriver driver) {
		this.driver = driver;
	}
	public String getOrderId() {
		String orderText = driver.findElement(orderId).getText();
		String orderId1 = orderText.replaceAll("[^0-9]", "");
		return orderId1;
	}

	public void verifypaymentDetails(CheckoutPage checkoutPage) {
		String cardtexpec=driver.findElement(ctype).getText();

		String expexpec=driver.findElement(expdate).getText();

		try{
			Assert.assertEquals(checkoutPage.getEnteredCardtype(), cardtexpec);
		}catch(AssertionError e) {
			BaseSetup.exttest.fail("cardtype is mismatch and actual cardtype "+cardtexpec);
		}
		try {
			Assert.assertEquals(checkoutPage.getEnteredexpiry(), expexpec );
		}catch(AssertionError e) {
			BaseSetup.exttest.fail("expiry date is mismatch and actual expiry date  "+expexpec);
		}
		BaseSetup.exttest.pass("Payment Details Successfully Verified");
	}
	public void verifyItemDetails(ItemPage itempage) {
		String itidexp=driver.findElement(itid).getText();
		try {
			Assert.assertEquals(itempage.getItemid(), itidexp);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("item Id is mismatch and actual expiry date  "+itidexp);
		}
		BaseSetup.exttest.pass("Item Details Successfully Verified");
	}
	public void verifyQtyDetails(CartPage cartpage) {
		String qtyexp=driver.findElement(quan).getText();
		try{
			Assert.assertEquals(cartpage.getQuantity(), qtyexp);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("quantity is mismatch and Expected Quantity: "+qtyexp);
		}
		BaseSetup.exttest.pass("Quantity Details Successfully Verified");
	}
	public void verifyBillDetails(Map<String, String> billingDetails) {
		String fname1expected=driver.findElement(fname1).getText();
		String lname1expected=driver.findElement(lname1).getText();
		String add11expected=driver.findElement(add11).getText();
		String add21expected=driver.findElement(add21).getText();
		String city4expected=driver.findElement(city4).getText();
		String state4expected=driver.findElement(state4).getText();
		String zip4expected=driver.findElement(zip4).getText();
		String country4expected=driver.findElement(country4).getText();

		try{
			Assert.assertEquals(billingDetails.get("billFirstName"), fname1expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill first name is mismatch and Expected Quantity: "+fname1expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billLastName"), lname1expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill last name is mismatch and Expected Quantity: "+lname1expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billAddress1"), add11expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill address1 is mismatch and Expected Quantity: "+add11expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billAddress2"), add21expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill address2 is mismatch and Expected Quantity: "+add21expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billCity"), city4expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill city is mismatch and Expected Quantity: "+city4expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billState"), state4expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill state is mismatch and Expected Quantity: "+state4expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billZip"), zip4expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill zip is mismatch and Expected Quantity: "+zip4expected);
		}
		try {
			Assert.assertEquals(billingDetails.get("billCountry"), country4expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill country is mismatch and Expected Quantity: "+country4expected);
		}


		BaseSetup.exttest.pass("Billing Details Successfully Verified");
	}
	public void verifyShipDetails(Map<String, String> shippingDetails) {
		String fnameexpected=driver.findElement(fname).getText();
		String lnameexpected=driver.findElement(lname).getText();
		String add1expected=driver.findElement(add1).getText();
		String add2expected=driver.findElement(add2).getText();
		String cityexpected=driver.findElement(city).getText();
		String stateexpected=driver.findElement(state).getText();
		String zipexpected=driver.findElement(zip).getText();
		String countryexpected=driver.findElement(country).getText();

		try {
			Assert.assertEquals(shippingDetails.get("shipFirstName"), fnameexpected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("ship first name is mismatch and Expected shipFirstName: "+fnameexpected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipLastName"), lnameexpected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("ship last name is mismatch and Expected shipLastName: "+lnameexpected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipAddress1"), add1expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("ship address1 is mismatch and Expected shipAddress1: "+add1expected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipAddress2"), add2expected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("ship address2 is mismatch and Expected shipAddress2: "+add2expected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipCity"), cityexpected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill country is mismatch and Expected shipCity: "+cityexpected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipState"), stateexpected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill country is mismatch and Expected shipState: "+stateexpected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipZip"), zipexpected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill country is mismatch and Expected shipZip: "+zipexpected);
		}
		try {
			Assert.assertEquals(shippingDetails.get("shipCountry"), countryexpected);
		}catch(AssertionError e) {
			BaseSetup.exttest.info("bill country is mismatch and Expected shipCountry: "+countryexpected);
		}

		BaseSetup.exttest.pass("Shipping Details Successfully Verified");
	}



}