package airhealth.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import mobile.functions.MobilePageBase;

public class AllowGPSScreen extends MobilePageBase{

	@FindBy(xpath = "//android.widget.TextView[contains(@text, 'Allow GPS')]")
	@CacheLookup
	private static WebElement allowGPSTitle;

	@FindBy(xpath = "//android.widget.Button[contains(@text, 'Allow')]")
	@CacheLookup
	private static WebElement btnAllow; 
	
	@FindBy(xpath = "//android.widget.Button[contains(@text, 'Later')]")
	@CacheLookup
	private static WebElement btnLater; 

	private AppiumDriver driver;
	private static final Logger logger = LogManager.getLogger(TermsAndConditionsScreen.class);

		public AllowGPSScreen(AppiumDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);	
			logger.debug("Loaded Allow GPS Screen");
		}

	public void allowGPS() {
		clickElement(btnAllow);
	}
	
	public EnterPostcodeScreen allowGPSLater() {
		logger.debug("Allow GPS Later and Continue");
		clickElement(btnLater);
		return new EnterPostcodeScreen(driver);
	}

	public boolean verifyAllowGPSScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(allowGPSTitle));	
		logger.debug("Check visibility of page : Allow GPS");
		return verifyElementIsDiaplayed(allowGPSTitle);
	}

	public boolean verifyAllowButtonIsDiaplayed() {
		logger.debug("Check visibility of button : Continue");
		return verifyElementIsDiaplayed(btnAllow);
	}
	
	public boolean verifyLaterButtonIsDiaplayed() {
		logger.debug("Check visibility of button : Later");
		return verifyElementIsDiaplayed(btnLater);
	}

}
