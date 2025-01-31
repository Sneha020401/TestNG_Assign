package utils;

import org.openqa.selenium.By;

public class Const {

	//Java Basic Assign2
	public static final By DateInput = By.xpath("//*[contains(@class,'has')]");
	public static final By Date = By.xpath("//*[contains(@class,'current-day')]");
	public static final By Month = By.xpath("//*[contains(@class,'ui-datepicker-month')]");
	public static final By Year = By.xpath("//*[contains(@class,'ui-datepicker-year')]");

    // Register

	//Sign IN Link
	public static final By SignIn = By.linkText("Sign In");

	// Registration page link
    public static final By ResgisterNow = By.linkText("Register Now!");

    // Sign in Fields
    public static final By UserName = By.name("username");
    public static final By PassWord = By.name("password");

    // Registration Form Fields
    public static final By RepeatPassword = By.name("repeatedPassword");
    public static final By FirstName = By.name("account.firstName");
    public static final By LastName = By.name("account.lastName");
    public static final By EmailID = By.name("account.email");
    public static final By PhoneID = By.name("account.phone");
    public static final By Address1ID = By.name("account.address1");
    public static final By Address2ID = By.name("account.address2");
    public static final By CityID = By.name("account.city");
    public static final By StateID = By.name("account.state");
    public static final By ZipID = By.name("account.zip");
    public static final By CountryID = By.name("account.country");
    public static final By LangPref = By.name("account.languagePreference");
    public static final By FavCategory = By.name("account.favouriteCategoryId");
    public static final By ListOption = By.name("account.listOption");
    public static final By BannerOption = By.name("account.bannerOption");

    // Register Button
    public static final By RegisterButton = By.name("newAccount");

    // After Registration - Account page
    public static final By RegisterConfirmMessage = By.id("WelcomeContent");


    // Login

    public static final By LoginButton = By.name("signon");

    public static final By SignOutBtn = By.linkText("Sign Out");

    public static final By LoginConfirmMsg = By.xpath("//a[contains(text(),'Sign Out')]");


    // My Account


    public static final By myaccountBtn = By.linkText("My Account");
    public static final By myorders = By.linkText("My Orders");
    public static final By orderHistoryLink = By.linkText("Order History");
    public static final By lastorderLink = By.xpath("(//tr/td/a[contains(@href, 'orderId')])[last()]");

    public static final By UsernameDisplay = By.xpath("//*[@id=\"Catalog\"]/form/table[1]/tbody/tr[1]/td[2]");


    //HomePage

    public static final  By catalogLink = By.linkText("?");
    public static final  By cartLink = By.name("img_cart");

    //CartPage
    public static final By quantityf = By.xpath("//input[@name=\"EST-8\"]");
    public static final By updateCart = By.xpath("//input[@name=\"updateCartQuantities\"]");
    public static final By checkout = By.xpath("//a[contains(text(),'Proceed to Checkout')]");

    //Category Page
    public static final By fishLink = By.xpath("//div[@id='QuickLinks']/a[1]");
    public static final By dogsLink = By.xpath("//div[@id='QuickLinks']/a[2]");
    public static final By reptilesLink = By.xpath("//div[@id='QuickLinks']/a[3]");
    public static final By castsLink = By.xpath("//div[@id='QuickLinks']/a[4]");
    public static final By birdsLink = By.xpath("//div[@id='QuickLinks']/a[5]");

    //payment details
    public static final By ctype = By.xpath("//*[@id='Catalog']/table/tbody/tr[3]/td[2]");
	public static final By expdate = By.xpath("//*[@id='Catalog']/table/tbody/tr[5]/td[2]");
	public static final By itid = By.xpath("//*[@id='Catalog']/table/tbody/tr[26]/td/table/tbody/tr[2]/td/a");
	public static final By quan = By.xpath("//*[@id='Catalog']/table/tbody/tr[26]/td/table/tbody/tr[2]/td[3]");
	public static final By orderId = By.xpath("//*[@id='Content']/table/tbody/tr[13]/td[1]/a");

	//Verify bill details
	public static final By fname1 = By.xpath("//*[@id='Catalog']/table/tbody/tr[7]/td[2]");
	public static final By lname1 = By.xpath("//*[@id='Catalog']/table/tbody/tr[8]/td[2]");
	public static final By add11 = By.xpath("//*[@id='Catalog']/table/tbody/tr[9]/td[2]");
	public static final By add21 = By.xpath("//*[@id='Catalog']/table/tbody/tr[10]/td[2]");
	public static final By city4= By.xpath("//*[@id='Catalog']/table/tbody/tr[11]/td[2]");
	public static final By state4 = By.xpath("//*[@id='Catalog']/table/tbody/tr[12]/td[2]");
	public static final By zip4 = By.xpath("//*[@id='Catalog']/table/tbody/tr[13]/td[2]");
	public static final By country4 = By.xpath("//*[@id='Catalog']/table/tbody/tr[14]/td[2]");

	//verify shipdetails
	public static final By fname = By.xpath("//*[@id='Catalog']/table/tbody/tr[16]/td[2]");
	public static final By lname = By.xpath("//*[@id='Catalog']/table/tbody/tr[17]/td[2]");
	public static final By add1 = By.xpath("//*[@id='Catalog']/table/tbody/tr[18]/td[2]");
	public static final By add2 = By.xpath("//*[@id='Catalog']/table/tbody/tr[19]/td[2]");
	public static final By city = By.xpath("//*[@id='Catalog']/table/tbody/tr[20]/td[2]");
	public static final By state = By.xpath("//*[@id='Catalog']/table/tbody/tr[21]/td[2]");
	public static final By zip = By.xpath("//*[@id='Catalog']/table/tbody/tr[22]/td[2]");
	public static final By country = By.xpath("//*[@id='Catalog']/table/tbody/tr[23]/td[2]");

	//Checkout Page
	public static final By confirmbtn = By.xpath("//div[@id=\"Catalog\"]/a");
	public static final By ContinueClick = By.xpath("//*[@name='newOrder']");
	public static final By shipdiffadd = By.xpath("//input[@name='shippingAddressRequired']");

	public static final By cardType = By.xpath("//*[@name='order.cardType']");
	public static final By cardNumber = By.xpath("//*[@name='order.creditCard']");
	public static final By expiryDate = By.xpath("//*[@name='order.expiryDate']");

	public static final By ofname1 = By.xpath("//input[@name='order.billToFirstName']");
	public static final By olname1 = By.xpath("//input[@name='order.billToLastName']");
	public static final By oadd11 = By.xpath("//input[@name='order.billAddress1']");
	public static final By oadd21 = By.xpath("//input[@name='order.billAddress2']");
	public static final By ocity4= By.xpath("//input[@name='order.billCity']");
	public static final By ostate4 = By.xpath("//input[@name='order.billState']");
	public static final By ozip4 = By.xpath("//input[@name='order.billZip']");
	public static final By ocountry4 = By.xpath("//input[@name='order.billCountry']");



	public static final By ofname = By.xpath("//input[@name='order.shipToFirstName']");
	public static final By olname = By.xpath("//input[@name='order.shipToLastName']");
	public static final By oadd1 = By.xpath("//input[@name='order.shipAddress1']");
	public static final By oadd2 = By.xpath("//input[@name='order.shipAddress2']");
	public static final By ocity = By.xpath("//input[@name='order.shipCity']");
	public static final By ostate = By.xpath("//input[@name='order.shipState']");
	public static final By ozip = By.xpath("//input[@name='order.shipZip']");
	public static final By ocountry = By.xpath("//input[@name='order.shipCountry']");

	//item
	public static final By itemID = By.xpath("//*[@id='Catalog']/table/tbody/tr[2]/td/a");
	public static final By addItemCart = By.xpath("//*[@id='Catalog']/table/tbody/tr[7]/td/a");

	//ProductId
    public static final By bulldog = By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td/a");
    public static final By poodle = By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[3]/td/a");
    public static final By dalmation = By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[4]/td/a");
    public static final By goldenRetriever = By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[5]/td/a");
    public static final By labradorRetriever = By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[6]/td/a");
    public static final By chihuahua = By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[7]/td/a");



}

