package pages;



import org.openqa.selenium.WebDriver;

import utils.Const;

public class CategoryPage {
    private WebDriver driver;



    public CategoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectCategory(String catname) {
        switch (catname) {
        case "fish":
        	driver.findElement(Const.fishLink).click();
        	break;
        case "dogs":
        	driver.findElement(Const.dogsLink).click();
        	break;
        case "reptiles":
        	driver.findElement(Const.reptilesLink).click();
        	break;
        case "cats":
        	driver.findElement(Const.castsLink).click();
        	break;
        case "birds":
        	driver.findElement(Const.birdsLink).click();
        	break;
        default:
        	break;
        }

    }


}

