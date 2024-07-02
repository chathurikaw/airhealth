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

public class TermsAndConditionsScreen extends MobilePageBase{

@FindBy(xpath = "//android.widget.TextView[contains(@text, 'Terms and conditions')]")
@CacheLookup
private static WebElement termsAndConditionsTitle; 


@FindBy(xpath = "//android.widget.Button[contains(@text, 'Continue')]")
@CacheLookup
private static WebElement btnContinue; 

private AppiumDriver driver;
private static final Logger logger = LogManager.getLogger(TermsAndConditionsScreen.class);

	public TermsAndConditionsScreen(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);	
		logger.debug("Loaded Terms and Conditions Screen");
	}

public AllowGPSScreen continueToNextScreen() {
	logger.debug("View Allow GPS");
	clickElement(btnContinue);
	return new AllowGPSScreen(driver);
}

public boolean verifyTermsAndConditionsPageLoaded() {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOf(termsAndConditionsTitle));	
	logger.debug("Check visibility of page : Terms and Conditions");
	return verifyElementIsDiaplayed(termsAndConditionsTitle);
}

public boolean verifyContinueButtonIsDiaplayed() {
	logger.debug("Check visibility of button : Continue");
	return verifyElementIsDiaplayed(btnContinue);
}

}
