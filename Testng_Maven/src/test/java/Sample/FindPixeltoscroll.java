package Sample;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.BaseSetup;

public class FindPixeltoscroll extends BaseSetup{
	  WebDriver driver = getBrowser();
		
  @Test

	  public void getButtonPosition() {
	  OpenBrowser(driver, properties.getProperty("url"));
		    // Find the button element you want to track
		    WebElement button = driver.findElement(By.id("mousehover"));

		    // Use JavascriptExecutor to get the element's position
		    JavascriptExecutor jse = (JavascriptExecutor) driver;

		    // Execute script to get the position (top and left) of the button
		    @SuppressWarnings("unchecked")
			Map<String, Object> position = (Map<String, Object>) jse.executeScript(
		            "var rect = arguments[0].getBoundingClientRect();" + 
		            "return {top: rect.top, left: rect.left};", button);
		    // Get the top and left positions (in pixels)
		    Double topPosition = (Double) position.get("top");
		    Double leftPosition = (Double) position.get("left");

		    System.out.println("Button's pixel position (top): " + topPosition);
		    System.out.println("Button's pixel position (left): " + leftPosition);
		}

  }

