package airhealth.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import mobile.functions.MobilePageBase;

public class EnterPostcodeScreen extends MobilePageBase {

	@FindBy(xpath = "//android.widget.TextView[contains(@text, 'Enter your postcode')]")
	@CacheLookup
	private static WebElement enterPostcodeTitle;

	@FindBy(xpath = "//android.widget.EditText")
	@CacheLookup
	private static WebElement enterPostcodeTextfield;

	@FindBy(xpath = "//android.widget.Button[contains(@text, 'Continue')]")
	@CacheLookup
	private static WebElement btnContinue;

	@FindBy(xpath = "//android.widget.TextView[@text=\"No results found.\"]")
	@CacheLookup
	public static WebElement noResultsFound;

	private AppiumDriver driver;
	private static final Logger logger = LogManager.getLogger(EnterPostcodeScreen.class);

	public EnterPostcodeScreen(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.debug("Loaded Enter Postcode");
	}

	public boolean verifyEnterPasscodePageLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(enterPostcodeTitle));
		logger.debug("Check visibility of page : Enter your postcode");
		return verifyElementIsDiaplayed(enterPostcodeTitle);
	}

	public boolean verifyContinueButtonIsDiaplayed() {
		logger.debug("Check visibility of button : Continue");
		return verifyElementIsDiaplayed(btnContinue);
	}

	public boolean verifyEnterLocationTextfieldIsDiaplayed() {
		logger.debug("Check visibility of text field : Enter Location");
		return verifyElementIsDiaplayed(enterPostcodeTextfield);
	}

	public void enterLocation(String postcode) {
		logger.debug("Search for location : {}", postcode);
//			clickElement(enterPostcodeTextfield);
		enterText(enterPostcodeTextfield, postcode);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.or(
				ExpectedConditions.numberOfElementsToBeMoreThan(By.className("android.widget.Button"), 3),
				ExpectedConditions.visibilityOf(noResultsFound)));
		List<WebElement> searchResults = driver.findElements(By.className("android.widget.Button"));
		logger.debug("Search Results Captured :{} Records", searchResults.size() - 3);
		if (searchResults.size() > 1) {
			for (WebElement result : searchResults) {
				String regex = ".*\\b" + postcode + "\\b.*";
				if (result.getText().matches(regex)) {
					logger.debug("First matching record selected: {}", result.getText());
					result.click();				
					break;
				}
			}
		} else {
			System.out.println("No Search Results");
			logger.debug("No Results Found");
		}
	}

	public AllowNotificationsScreen continueToNextScreen() {
		logger.debug("Enter location and navigate to View Allow Notifications screen");
		clickElement(btnContinue);
		return new AllowNotificationsScreen(driver);
	}

}
