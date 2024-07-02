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

public class SplashScreen extends MobilePageBase{
	
@FindBy(xpath = "//android.widget.Button[@text=\"PROCEED TO DASHBOARD\"]")
@CacheLookup
private static WebElement btnProceedToDashboard;

@FindBy(xpath = "//android.widget.TextView[contains(@text, 'App Version')]")
@CacheLookup
private static WebElement appVersion;

@FindBy(xpath = "//android.widget.TextView[contains(@text, 'Build Number')]")
@CacheLookup
private static WebElement buildNumber; 

@FindBy(xpath = "//android.widget.Button[contains(@text, 'Continue')]")
@CacheLookup
private static WebElement btnContinue; 

@FindBy(xpath = "//android.widget.Button[contains(@text, 'I already have an account')]")
@CacheLookup
private static WebElement btnSignIn; 

private AppiumDriver driver;
private static final Logger logger = LogManager.getLogger(SplashScreen.class);

	public SplashScreen(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);	
		logger.info("Onboarding Screen Loaded");
	}
	
	public void getAppDetails(AppiumDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(appVersion));	
		logger.debug(appVersion.getText());
		logger.debug(buildNumber.getText());
	}	
	
	
	public TermsAndConditionsScreen continueToNextScreen() {
		logger.debug("View Terms and Conditions");
		clickElement(btnContinue);
		return new TermsAndConditionsScreen(driver);
	}
	
	public void signIn() {
		clickElement(btnSignIn);
	}
	
	public boolean verifyContinueButtonIsDiaplayed() {
		logger.debug("Check visibility of button : Continue");
		return verifyElementIsDiaplayed(btnContinue);
	}
	
	public boolean verifyIAlreadyHaveAnAccountButtonIsDiaplayed() {
		logger.debug("Check visibility of button : I already have an account");
		return verifyElementIsDiaplayed(btnSignIn);
	}
	
	
	
}
