package airhealth.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import airhealth.pages.AllowGPSScreen;
import airhealth.pages.AllowNotificationsScreen;
import airhealth.pages.DashboardScreen;
import airhealth.pages.EnterPostcodeScreen;
import airhealth.pages.SplashScreen;
import airhealth.pages.TermsAndConditionsScreen;
import mobile.functions.BaseClass;
import mobile.utilities.ExtentReportListener;

public class OnboardingTest extends BaseClass {

	@BeforeClass(description = "Launching the app")
	public void launchApp() throws InterruptedException {
		ExtentReportListener.initializeExtentReport("test-output/ExtentReports/OnboardingTestReport.html");
		initializeDriver("airhealthConfig.properties");
		ExtentTest test1 = ExtentReportListener.createTest("launchApp");
		test1.log(Status.INFO, "Starting the application...");

		SplashScreen splashScreen = new SplashScreen(driver);
		splashScreen.getAppDetails(driver);

		boolean continueButtonIsDisplayed = splashScreen.verifyContinueButtonIsDiaplayed();
		Assert.assertTrue(continueButtonIsDisplayed, "Continue button is not displayed.");
		test1.log(Status.PASS, "Continue button is displayed.");

		boolean iAlreadyHaveAnAccountButtonIsDiaplayed = splashScreen.verifyIAlreadyHaveAnAccountButtonIsDiaplayed();
		Assert.assertTrue(iAlreadyHaveAnAccountButtonIsDiaplayed, "I already have an account button is not displayed.");
		test1.log(Status.PASS, "I already have an account button is displayed.");
	}

	@Test(description = "View Terms and Conditions", priority = 1)
	public void viewTermsAndConditionsTest() {
		ExtentTest test2 = ExtentReportListener.createTest("viewTermsAndConditionsTest");
		SplashScreen splashScreen = new SplashScreen(driver);

		TermsAndConditionsScreen termsAndConditionsScreen = splashScreen.continueToNextScreen();

		boolean termsAndConditionsPageLoaded = termsAndConditionsScreen.verifyTermsAndConditionsPageLoaded();
		Assert.assertTrue(termsAndConditionsPageLoaded, "Terms and Conditions page is not displayed.");
		test2.log(Status.PASS, "Terms and Conditions Screen is loaded.");

		boolean continueButtonIsDisplayed = termsAndConditionsScreen.verifyContinueButtonIsDiaplayed();
		Assert.assertTrue(continueButtonIsDisplayed, "Continue button is not displayed.");
		test2.log(Status.PASS, "Continue button is displayed.");

	}

	@Test(description = "View Allow GPS", priority = 2, dependsOnMethods = "viewTermsAndConditionsTest")
	public void viewGPSAllowTest() {
		ExtentTest test3 = ExtentReportListener.createTest("viewGPSAllowTest");
		TermsAndConditionsScreen termsAndConditionsScreen = new TermsAndConditionsScreen(driver);

		AllowGPSScreen allowGPSScreen = termsAndConditionsScreen.continueToNextScreen();

		boolean allowGPSScreenLoaded = allowGPSScreen.verifyAllowGPSScreenLoaded();
		Assert.assertTrue(allowGPSScreenLoaded, "Allow GPS Screen is not displayed.");
		test3.log(Status.PASS, "Allow GPS Screen is loaded.");

		boolean allowButtonIsDisplayed = allowGPSScreen.verifyAllowButtonIsDiaplayed();
		Assert.assertTrue(allowButtonIsDisplayed, "Allow button is not displayed.");
		test3.log(Status.PASS, "Allow button is displayed.");

		boolean laterButtonIsDisplayed = allowGPSScreen.verifyLaterButtonIsDiaplayed();
		Assert.assertTrue(laterButtonIsDisplayed, "Later button is not displayed.");
		test3.log(Status.PASS, "Later button is displayed.");

	}

	@Test(description = "View Enter Postcode", priority = 3, dependsOnMethods = "viewGPSAllowTest")
	public void viewEnterPasswordTest() {
		ExtentTest test4 = ExtentReportListener.createTest("viewEnterPasswordTest");
		AllowGPSScreen allowGPSScreen = new AllowGPSScreen(driver);

		EnterPostcodeScreen enterPostcodeScreen = allowGPSScreen.allowGPSLater();

		boolean enterPostcodeScreenLoaded = enterPostcodeScreen.verifyEnterPasscodePageLoaded();
		Assert.assertTrue(enterPostcodeScreenLoaded, "Enter Passcode Screen is not displayed.");
		test4.log(Status.PASS, "Enter Passcode Screen is loaded.");

		boolean continueButtonIsDisplayed = enterPostcodeScreen.verifyContinueButtonIsDiaplayed();
		Assert.assertTrue(continueButtonIsDisplayed, "Continue button is not displayed.");
		test4.log(Status.PASS, "Continue button is displayed.");

		boolean enterLocationTextfieldIsDisplayed = enterPostcodeScreen.verifyEnterLocationTextfieldIsDiaplayed();
		Assert.assertTrue(enterLocationTextfieldIsDisplayed, "Enter Location Text Field is not displayed.");
		test4.log(Status.PASS, "Enter Location Text Field is displayed.");
	}

	@Test(description = "View Allow Notifications", priority = 4, dependsOnMethods = "viewEnterPasswordTest")
	public void viewAllowNotificationsTest() {
		ExtentTest test5 = ExtentReportListener.createTest("viewAllowNotificationsTest");
		EnterPostcodeScreen enterPostcodeScreen = new EnterPostcodeScreen(driver);
		enterPostcodeScreen.enterLocation("3083");
		AllowNotificationsScreen allowNotificationsScreen = enterPostcodeScreen.continueToNextScreen();

		boolean allowNotificationsScreenIsLoaded = allowNotificationsScreen.VerifyallowNotificationsScreenLoaded();
		Assert.assertTrue(allowNotificationsScreenIsLoaded, "Allow Notifications Screen is not displayed.");
		test5.log(Status.PASS, "Enter Passcode Screen is loaded.");

		boolean continueButtonIsDisplayed = allowNotificationsScreen.verifyAllowButtonIsDiaplayed();
		Assert.assertTrue(continueButtonIsDisplayed, "Allow button is not displayed.");
		test5.log(Status.PASS, "Allow button is displayed.");

		boolean enterLocationTextfieldIsDisplayed = allowNotificationsScreen.verifyLaterButtonIsDiaplayed();
		Assert.assertTrue(enterLocationTextfieldIsDisplayed, "Later button is not displayed.");
		test5.log(Status.PASS, "Later button is displayed.");

	}

	@Test(description = "View Dashboard", priority = 5, dependsOnMethods = "viewAllowNotificationsTest")
	public void viewDashboardTest() {
		ExtentTest test6 = ExtentReportListener.createTest("viewDashboardTest");
		AllowNotificationsScreen allowNotificationsScreen = new AllowNotificationsScreen(driver);
		DashboardScreen dashboardScreen = allowNotificationsScreen.allowNotificationsLater();

		boolean dashboardIsLoaded = dashboardScreen.verifyDashboardLoaded();
		Assert.assertTrue(dashboardIsLoaded, "Dashboard Screen is not displayed.");
		test6.log(Status.PASS, "Dashboard Screen is loaded.");

		boolean dashboardLocationIsLoaded = dashboardScreen.verifyDashboardLocation("Kingsbury, Victoria");
		Assert.assertTrue(dashboardLocationIsLoaded, "Correct Dashboard loaded.");
		test6.log(Status.PASS, "Dashboard for 'Kingsbury, Victoria' is displayed.");

	}

	@AfterClass
	public void closeApp() {
		ExtentReportListener.flushExtentReports();
//		teardownDevice();
	}

}
