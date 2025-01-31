package pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.Const;

public class CheckoutPage {
	private WebDriver driver;


	public String billFirstName,billLastName,billAddress1,billAddress2,billCity,billState,billZip,billCountry;
	public String shipFirstName,shipLastName,shipAddress1,shipAddress2,shipCity,shipState,shipZip,shipCountry;
	public String enteredCardtype,enteredCardno,enteredExp;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterCardType(String cardtype) {
		WebElement ctype = driver.findElement(Const.cardType);
		Select catype = new Select(ctype);
		catype.selectByVisibleText(cardtype);
		enteredCardtype = cardtype;
	}
	public void enterCardNumber(String cardnum) {
		WebElement cnum = driver.findElement(Const.cardNumber);
		cnum.clear();
		cnum.sendKeys(cardnum);
		enteredCardno=cardnum+" * Fake\r\n"
				+ "		number!";
	}
	public void enterExpiryDate(String expiry) {
		WebElement exp = driver.findElement(Const.expiryDate);
		exp.clear();
		exp.sendKeys(expiry);
		enteredExp=expiry;

	}
	public void clickship() {
		driver.findElement(Const.shipdiffadd).click();
	}

	public void clickContinue() {
		driver.findElement(Const.ContinueClick).click();
	}
	public void confirmClick() {
        driver.findElement(Const.confirmbtn).click();
    }

	public String getEnteredCardtype() {
        return enteredCardtype;
    }

    public String getEnteredCardno() {
        return enteredCardno;
    }

    public String getEnteredexpiry() {
        return enteredExp;
    }

	public void billDetails(String finame,String laname,String addr1,String addr2,String city1,String state1,String zip1,String country1) {
		WebElement fstname1 = driver.findElement(Const.ofname1);
		fstname1.clear();
		fstname1.sendKeys(finame);
		billFirstName = finame;
		WebElement lstname1 = driver.findElement(Const.olname1);
		lstname1.clear();
		lstname1.sendKeys(laname);
		billLastName = finame;
		WebElement addre11 = driver.findElement(Const.oadd11);
		addre11.clear();
		addre11.sendKeys(addr1);
		billAddress1 = finame;
		WebElement addre21 = driver.findElement(Const.oadd21);
		addre21.clear();
		addre21.sendKeys(addr2);
		billAddress2 = finame;
		WebElement city31 = driver.findElement(Const.ocity4);
		city31.clear();
		city31.sendKeys(city1);
		billCity = finame;
		WebElement state31 = driver.findElement(Const.ostate4);
		state31.clear();
		state31.sendKeys(state1);
		billState = finame;
		WebElement zip31 = driver.findElement(Const.ozip4);
		zip31.clear();
		zip31.sendKeys(zip1);
		billZip = finame;
		WebElement country31 = driver.findElement(Const.ocountry4);
		country31.clear();
		country31.sendKeys(country1);
		billCountry = finame;
	}

	public void shipDetails(String finame,String laname,String addr1,String addr2,String city1,String state1,String zip1,String country1) {
		WebElement fstname = driver.findElement(Const.ofname);
		fstname.clear();
		fstname.sendKeys(finame);
		shipFirstName = finame;
		WebElement lstname = driver.findElement(Const.olname);
		lstname.clear();
		lstname.sendKeys(laname);
		shipLastName = finame;
		WebElement addre1 = driver.findElement(Const.oadd1);
		addre1.clear();
		addre1.sendKeys(addr1);
		shipAddress1 = finame;
		WebElement addre2 = driver.findElement(Const.oadd2);
		addre2.clear();
		addre2.sendKeys(addr2);
		shipAddress2 = finame;
		WebElement city3 = driver.findElement(Const.ocity);
		city3.clear();
		city3.sendKeys(city1);
		shipCity = finame;
		WebElement state3 = driver.findElement(Const.ostate);
		state3.clear();
		state3.sendKeys(state1);
		shipState = finame;
		WebElement zip3 = driver.findElement(Const.ozip);
		zip3.clear();
		zip3.sendKeys(zip1);
		shipZip = finame;
		WebElement country3 = driver.findElement(Const.ocountry);
		country3.clear();
		country3.sendKeys(country1);
		shipCountry = finame;
	}

	public Map<String, String> getBillingDetails() {
		Map<String, String> billing = new HashMap<>();

		billing.put("billFirstName", driver.findElement(Const.ofname1).getAttribute("value"));
		billing.put("billLastName", driver.findElement(Const.olname1).getAttribute("value"));
		billing.put("billAddress1", driver.findElement(Const.oadd11).getAttribute("value"));
		billing.put("billAddress2", driver.findElement(Const.oadd21).getAttribute("value"));
		billing.put("billCity", driver.findElement(Const.ocity4).getAttribute("value"));
		billing.put("billState", driver.findElement(Const.ostate4).getAttribute("value"));
		billing.put("billZip", driver.findElement(Const.ozip4).getAttribute("value"));
		billing.put("billCountry", driver.findElement(Const.ocountry4).getAttribute("value"));
		return billing;
	}
	public Map<String, String> getShippingDetails() {
		Map<String, String> shipping = new HashMap<>();
		shipping.put("shipFirstName", driver.findElement(Const.ofname).getAttribute("value"));
		shipping.put("shipLastName", driver.findElement(Const.olname).getAttribute("value"));
		shipping.put("shipAddress1", driver.findElement(Const.oadd1).getAttribute("value"));
		shipping.put("shipAddress2", driver.findElement(Const.oadd2).getAttribute("value"));
		shipping.put("shipCity", driver.findElement(Const.ocity).getAttribute("value"));
		shipping.put("shipState", driver.findElement(Const.ostate).getAttribute("value"));
		shipping.put("shipZip", driver.findElement(Const.ozip).getAttribute("value"));
		shipping.put("shipCountry", driver.findElement(Const.ocountry).getAttribute("value"));

		return shipping;
	}
}

