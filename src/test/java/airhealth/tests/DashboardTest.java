package airhealth.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import airhealth.pages.AllowNotificationsScreen;
import airhealth.pages.DashboardScreen;
import airhealth.pages.EnterPostcodeScreen;
import airhealth.pages.SearchLocationScreen;
import airhealth.pages.SplashScreen;
import io.appium.java_client.AppiumDriver;
import mobile.utilities.ExtentReportListener;
import mobile.functions.BaseClass;
import mobile.functions.MobilePageBase;

public class DashboardTest extends BaseClass {

	@BeforeClass(description = "Launching the app and proceed to dashboard")
	public void launchApp() throws InterruptedException {
		ExtentReportListener.initializeExtentReport("test-output/ExtentReports/DashboardTestReport.html");
		initializeDriver("airhealthConfig.properties");
		ExtentTest test1 = ExtentReportListener.createTest("launchApp");
		test1.log(Status.INFO, "Starting the application...");

		SplashScreen splashScreen = new SplashScreen(driver);
		splashScreen.getAppDetails(driver);
		
		EnterPostcodeScreen enterPostcodeScreen = splashScreen.continueToNextScreen().continueToNextScreen().allowGPSLater();
		enterPostcodeScreen.enterLocation("3083");
		AllowNotificationsScreen allowNotificationsScreen = enterPostcodeScreen.continueToNextScreen();
		allowNotificationsScreen.allowNotificationsLater();
		test1.log(Status.PASS, "Dashboard for 'Kingsbury, Victoria' is displayed.");
	}

	@Test(description = "view Forcaste Data", priority = 1)
	public void viewForecastDataTest() {
		ExtentTest test = ExtentReportListener.createTest("viewForecastDataTest");
		test.log(Status.INFO, "Starting view Forcaste Data test.");

		DashboardScreen dashboardScreen = new DashboardScreen(driver);
		
		dashboardScreen.getForecastDayCount();
	}
	
	
	@Ignore
	@Test(description = "Select the location from drop down", priority = 1)
	public void locationSelectTest() {
		ExtentTest test = ExtentReportListener.createTest("locationSelectTest");
		test.log(Status.INFO, "Starting location selection test.");

		DashboardScreen dashboardScreen = new DashboardScreen(driver);
		SearchLocationScreen searchLocationScreen = dashboardScreen.openLocationSearch();
		test.log(Status.INFO, "Opened location search screen.");

		dashboardScreen = searchLocationScreen.searchLocation("Kingsbury");
		test.log(Status.INFO, "Searched for location: Kingsbury.");

		boolean isLocationDisplayed = dashboardScreen.verifyDashboardLocation("Kingsbury, Victoria");
		Assert.assertTrue(isLocationDisplayed, "Correct Dashboard loaded.");
		test.log(Status.PASS, "Dashboard for 'Kingsbury, Victoria' is displayed.");

	}

	@Ignore
	@Test(description = "view Allergens Data", priority = 2, dependsOnMethods = "locationSelectTest")
	public void viewAllergenDataTest() {
		ExtentTest test = ExtentReportListener.createTest("viewAllergenDataTest");
		test.log(Status.INFO, "Starting view Allergens Data test.");

		DashboardScreen dashboardScreen = new DashboardScreen(driver);
		dashboardScreen.viewCurrentConditionsDetails("Allergens");
		test.log(Status.INFO, "View Allergens Data");
		
		boolean isAllergensDataDisplayed = dashboardScreen.verifyConditionDataLoaded("Allergens");
		Assert.assertTrue(isAllergensDataDisplayed, "Correct Dashboard loaded.");
		test.log(Status.PASS, "Dashboard details for 'Allergens Data' is displayed.");
		
		dashboardScreen.getForecastDayCount("Allergens");
	}

	

	@AfterClass
	public void closeApp() {
		ExtentReportListener.flushExtentReports();
//		teardownDevice();
	}

}