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

public class AllowNotificationsScreen extends MobilePageBase{
	
	@FindBy(xpath = "//android.widget.TextView[contains(@text, 'Allow notifications')]")
	@CacheLookup
	private static WebElement allowNotificationsTitle; 
	
	@FindBy(xpath = "//android.widget.Button[contains(@text, 'Allow')]")
	@CacheLookup
	private static WebElement btnAllow; 
	
	@FindBy(xpath = "//android.widget.Button[contains(@text, 'Later')]")
	@CacheLookup
	private static WebElement btnLater; 

	private AppiumDriver driver;
	private static final Logger logger = LogManager.getLogger(AllowNotificationsScreen.class);

		public AllowNotificationsScreen(AppiumDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);	
			logger.debug("Loaded Allow Notifications Screen");
		}
		
		public void allowNotifications() {
			clickElement(btnAllow);
		}
		
		public DashboardScreen allowNotificationsLater() {
			logger.debug("Allow Notifications Later and Continue");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(allowNotificationsTitle));	
			clickElement(btnLater);
			return new DashboardScreen(driver);
		}
		
		public boolean VerifyallowNotificationsScreenLoaded() {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(allowNotificationsTitle));	
			logger.debug("Check visibility of page : Allow Notifications");
			return verifyElementIsDiaplayed(allowNotificationsTitle);
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
