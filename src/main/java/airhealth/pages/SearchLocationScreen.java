package airhealth.pages;

import java.time.Duration;
import java.util.List;
import io.appium.java_client.android.AndroidDriver;
import mobile.functions.MobilePageBase;
import io.appium.java_client.AppiumDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchLocationScreen extends MobilePageBase {

	@FindBy(xpath = "//android.widget.EditText")
	@CacheLookup
	public static WebElement enterLocation;

	@FindBy(xpath = "//android.widget.Button[@text=\"Done\"]")
	@CacheLookup
	public static WebElement confirmLocation;
	
	@FindBy(xpath = "//android.widget.TextView[@text=\"No results found.\"]")
	@CacheLookup
	public static WebElement noResultsFound;

	public AndroidDriver driver;
    private static final Logger logger = LogManager.getLogger(SearchLocationScreen.class);

	public SearchLocationScreen(AppiumDriver driver) {
		this.driver = (AndroidDriver) driver;
		PageFactory.initElements(driver, this);
	}

	public DashboardScreen searchLocation(String location){

		logger.debug("Search for location : {}", location);
		enterText(enterLocation, location);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.or(
                 ExpectedConditions.numberOfElementsToBeMoreThan(By.className("android.widget.Button"), 2),
                 ExpectedConditions.visibilityOf(noResultsFound)
         ));
		List<WebElement> searchResults = driver.findElements(By.className("android.widget.Button"));
		logger.debug("Search Results Captured :{} Records", searchResults.size()-2);
		// Retrieve the list of search results
		// Iterate through the search results and capture their text
		if(searchResults.size()>2) {
		for (WebElement result : searchResults) {
			String regex = ".*\\b" + location + "\\b.*";
			if (result.getText().matches(regex)) {
				result.click();
				confirmLocation.click();
				break;
			}
		}
		  return new DashboardScreen(driver);
		    
		}else {
			System.out.println("No Search Results");
			logger.debug("No Results Found");
		}
		return null;
	}

}
