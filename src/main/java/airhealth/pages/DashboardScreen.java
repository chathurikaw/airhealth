package airhealth.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import mobile.functions.MobilePageBase;

public class DashboardScreen extends MobilePageBase {

	@FindBy(xpath = "//android.webkit.WebView[@text=\"Ionic App\"]/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.widget.Button[1]")
	@CacheLookup
	public WebElement iconSearchLocation;
	
	@FindBy(xpath = "//android.webkit.WebView[@text=\"Ionic App\"]/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.widget.Button[1]")
	@CacheLookup
	public WebElement labelCurrentConditions;

	@FindBy(xpath = "//android.webkit.WebView[@text=\"Ionic App\"]/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View")
	@CacheLookup
	public WebElement forcasteContainer;

	private AppiumDriver driver;
	private static final Logger logger = LogManager.getLogger(DashboardScreen.class);

	public DashboardScreen(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.debug("Loaded Dashboard");
	}

	public SearchLocationScreen openLocationSearch() {
		logger.debug("Navigate to Location Search screen");
		clickElement(iconSearchLocation);
		return new SearchLocationScreen(driver);
	}

	public boolean verifyDashboardLocation(String location) {
		logger.debug("Return Selected Location Element");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='" + location + "']")));
		logger.debug("Found Location Element : {}", element.getText());
		return element.isDisplayed();
	}

	public boolean verifyDashboardLoaded() {
		return verifyElementIsDiaplayed(labelCurrentConditions);
	}
	
	public boolean verifyConditionDataLoaded(String condition) {
		logger.debug("Verify {} dashboard loaded", condition);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='" + condition + "']")));
		return element.isDisplayed();
	}

	public void viewCurrentConditionsDetails(String condition) {
		logger.debug("View {} details", condition);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("android.widget.Button")));
		List<WebElement> searchResults = driver.findElements(By.className("android.widget.Button"));
		// Retrieve the list of search results
		// Iterate through the search results and capture their text
		if (searchResults.size() > 0) {
			for (WebElement result : searchResults) {
				if (result.getText().contains(condition)) {
					result.click();
					logger.debug("click {}", condition);
					break;
				}
			}
		} else {
			logger.debug("No Results Found");
		}
	}
	
	public void getForecastDayCount() {
	    logger.debug("View forecast details");

	    try {
	        // Wait for the element with the text "Upcoming forecast"
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator(
	                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"Upcoming forecast\"));")));
	        logger.debug("Navigated to Upcoming Forecast");
	        
	        WebElement a =  driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0))"));
	        System.out.println("a  -> " +a.getAttribute("class"));
	        System.out.println("a  -> " +a.getAttribute("bounds"));
	        
	        WebElement b =  driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(1))"));
	        System.out.println("b  -> " +b.getAttribute("class"));
	        System.out.println("b  -> " +b.getAttribute("bounds"));
	        
	        WebElement c =  driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(2))"));
	        System.out.println("c  -> " +c.getAttribute("class"));
	        System.out.println("c  -> " +c.getAttribute("bounds"));
	        
	        WebElement d =  driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(3))"));
	        System.out.println("d  -> " +d.getAttribute("class"));
	        System.out.println("d  -> " +d.getAttribute("bounds"));
	        try {
	        	WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
	                    "new UiScrollable(new UiSelector().scrollable(true)).setAsHorizontalList().getChildByText("
	                    + "new UiSelector().className(\"android.widget.TextView\"), \"d{2}°C\")"));
	            System.out.println(element.getClass());

	            // Perform actions with the found element
	            System.out.println("Found element: " + element.getText());

	            
	            
	        } catch (NoSuchElementException e) {
	            System.err.println("Element not found: " + e.getMessage());
	        } catch (Exception e) {
	            System.err.println("An error occurred: " + e.getMessage());
	        }
            
	        // Initialize the horizontal scroll action
	        TouchAction action = new TouchAction((PerformsTouchActions) driver);
	        Dimension size = forcasteContainer.getSize();
	        int startX = (int) (size.width * 0.9);
	        int endX = (int) (size.width * 0.1);
	        int startY = size.height / 2;

	        // Scroll horizontally within the forecast container and count the day elements
	        int dayCount = 0;
	        boolean endOfScroll = false;

	        while (!endOfScroll) {
	            // Count the number of visible day elements
	            List<WebElement> dayElements = forcasteContainer.findElements(By.xpath(".//android.widget.TextView[contains(@text, 'day')]"));
	            dayCount += dayElements.size();

	            // Scroll to the next set of elements
	            action.press(PointOption.point(startX, startY))
	                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
	                    .moveTo(PointOption.point(endX, startY))
	                    .release()
	                    .perform();

	            // Check if we have reached the end of the scroll
	            List<WebElement> newDayElements = forcasteContainer.findElements(By.xpath(".//android.widget.TextView[contains(@text, 'day')]"));
	            if (newDayElements.size() == dayElements.size()) {
	                endOfScroll = true;
	            }
	        }

	        System.out.println("Total forecast days: " + dayCount);
	    } catch (NoSuchElementException e) {
	        System.err.println("Could not find the 'Upcoming forecast' element: " + e.getMessage());
	    } catch (Exception e) {
	        System.err.println("An error occurred: " + e.getMessage());
	    }
	}
	

	public void getForecastDayCount(String condition) {
		logger.debug("View {} forecaste details", condition);
		WebElement scrollView = driver.findElement(AppiumBy.androidUIAutomator(
	            "new UiScrollable(new UiSelector().scrollable(true).instance(1))"));
	     
//		
//		 WebElement lastElement = driver.findElement(AppiumBy.androidUIAutomator(
//		            "new UiScrollable(new UiSelector().scrollable(true).instance(1))"
//		                    + ".scrollToEnd(10)"
//		                    ));
//		 
//		 System.out.println(lastElement.getAttribute("scrollable"));
//			System.out.println(lastElement.getAttribute("bounds"));
//			System.out.println(lastElement.isDisplayed());
		
		
		driver.findElement(AppiumBy.androidUIAutomator( "new UiScrollable(new UiSelector()).scrollIntoView(text(\"Learn about the categories\"));"));
		System.out.println("Scrolled to view forcaste details");
//		List<WebElement> searchResults = driver.findElements(By.className("android.widget.TextView"));
//	    System.out.println(searchResults.size());
	    
	 // Define the XPath with the regex pattern
	    String xpathPattern = "//android.widget.TextView[matches(@text, '\\d{2} [A-Z]{3}')]";

	    // Find all elements matching the pattern
	    List<WebElement> searchResults = driver.findElements(By.xpath(xpathPattern));
	 // Capture the last matching element
        WebElement lastElement = searchResults.get(searchResults.size() - 1);
        
        // Print out the details of the last matching element
        String lastDateText = lastElement.getText().trim(); // Ensure no leading/trailing spaces
        System.out.println("Element Text: " + lastDateText);
        System.out.println("Element Bounds: " + lastElement.getAttribute("bounds"));

        // Parse the date from the text
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate lastDate;
        try {
            lastDate = LocalDate.parse(lastDateText + " 2024", formatter); // Append the current year
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return;
        }
        
        LocalDate nextDate = lastDate.plusDays(1);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM");
        String nextDateText = nextDate.format(outputFormatter);

        // Print out the next date
        System.out.println("Next Date: " + nextDateText);
	    
	    for (WebElement result : searchResults) {
	    	System.out.println(result.getText());
	    }
		
		System.out.println("Scrolling");
		driver.findElement(AppiumBy.androidUIAutomator(
	            "new UiScrollable(new UiSelector().scrollable(true).instance(1)).setAsHorizontalList().scrollForward()"));
	    
	}
	
	
}
