package com.opencart.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencart.qa.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver= driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	//Send Keys Method/function
	@Step("Entering the value: {1} using Byy locator: {0} ")
	public  void doSendKeys(By locator,String value) {
		//FE + action 
		doClear(locator);
		getElement(locator).sendKeys(value);
		}
	
	//Send Keys Method/function with wait
	@Step("Entering the value: {1} using Byy locator: {0} ")
	public  void doSendKeys(By locator,String value , long timeOut) {
			//FE + action 
			doClear(locator);
			getElement(locator , timeOut).sendKeys(value);
			}
		
	//Clear method
	public void doClear(By locator) {
			getElement(locator).clear();
		}
	
	
	//Click method/function
	@Step(" Clicking on the element using By locator: {0}")
	public  void doClick(By locator) {
		getElement(locator).click();
	}
	
	//click with wait 
	@Step(" Clicking on the element using By locator: {0}")
	public  void doClick(By locator , long timeOut) {
		getElement(locator , timeOut ).click();
	}
	
	
	//Get Text method/function
	@Step(" Get text of the element using By locator: {0}")
	public String doElementGetText(By locator)  {
		return getElement(locator).getText();
	}
	
	//Is element displayed
	@Step(" Element is displayed using By locator: {0}")
	public boolean isElementDisplayed(By locator)  {
		try {
			return getElement(locator).isDisplayed();
		}
		catch(NoSuchElementException e){
			System.out.println("Element is not found using this locator  : "+ locator );
			e.printStackTrace();
			return false;
		}
		}
	
	
	//For single element
	@Step(" Find element using By locator: {0}")
	public WebElement getElement(By locator)  {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)){
			jsUtil.flash(element);
		}
		return element;
       }
	
	//getElement with wait
	public  WebElement  getElement(By locator ,long timeOut) {
			try {
				return driver.findElement(locator);
			}
			catch(NoSuchElementException e) {
				System.out.println("Element is not find using : " +locator);
				e.printStackTrace();
				//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
				//return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				//in case of above 2 line simply we call 
				return waitForElementVisible(locator, timeOut);
			}
		}
	
	//For Multiple Element
	@ Step(" Find elements using By locator: {0}")
	public  List<WebElement> getElements(By loacator) {
		return driver.findElements(loacator);
	}
	
	//For each loop util
	public  List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List <String>eleTextList = new ArrayList<String>();//vc=10; pc=0;
		for( WebElement e : eleList) {
			String text = e.getText();
			if(text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}
	
	// For locator
	public  int totalcount(By locator) {
		 return getElements(locator).size();
	}
	
	//For GetAttribute 
	public String getElementAttribute(By locator, String attrName ) {
		return getElement(locator).getAttribute(attrName);
	}
	
	//****************** Drop Down Utils **********************
	
	private Select getSelect(By locator) {
		return new Select(getElement(locator));
	}
	
	public void doDropdownSelectByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
	}
	
	public void doDropdownSelectByVisbleText(By locator, String visibleText) {
		getSelect(locator).selectByVisibleText(visibleText);
	}
	
	public void doDropdownSelectByValue(By locator, String value) {
		getSelect(locator).selectByValue(value);
	}
	
	public int getDroDownOptionsCount(By locator) {
		return getSelect(locator).getOptions().size();
	}
	
	public  List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getSelect(locator).getOptions();
		List<String> optionsTextList = new ArrayList<String>();//pc=0 , vc= 10 []
		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
		
	}
	
	//****************** Actions class Utils **********************
	
	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();;
	}
	
	public void doActionsSendKeys(By locator , String value) {
		act.sendKeys(getElement(locator), value).perform();
	}
	
	public void handleMenuItemLevel2(By parentLocator, By childLocator) {
		act.moveToElement(getElement(parentLocator)).perform();
		doClick(childLocator);
		
	}
	
    public void handleMenuItemLevel3(By menu1, By menu2, By menu3) throws InterruptedException {
		doClick(menu1);
		act.moveToElement(getElement(menu2)).perform();
		doClick(menu3);
	
	}
	
    public void handleMenuItemLevel4(By menu1, By menu2, By menu3, By menu4) throws InterruptedException {
		doClick(menu1);
		act.moveToElement(getElement(menu2)).perform();
		act.moveToElement(getElement(menu3)).perform();
		doClick(menu4);
	}
    
	public void sendKeysWithPause(By locator, String value, long pauseTime) {
		char val[] = value.toCharArray();
		for(char e : val) {
			act.sendKeys(getElement(locator), String.valueOf(e)).pause(pauseTime).perform();
		}
	}
	
	//*********************Wait Utils***********************************
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return 
	 */
	public List<WebElement> waitForAllElementsPresence(By locator ,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try{
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e) {
			return Collections.emptyList();//[]
		}
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return 
	 */
	public List<WebElement> waitForAllElementsVisible(By locator ,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try{
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e) {
			return Collections.emptyList();//[]
		}
		
	}
	
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void waitForElementReadyAndClick(By locator , long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step(" Waiting for the element to be presence : {0} with timeout: {1}")
	public  WebElement waitForElementPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	//Promptly recommended
	@Step(" Waiting for the element to be visible : {0} with timeout: {1}")
	public  WebElement waitForElementVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public  WebElement waitForElementVisible(By locator, long timeOut , long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.pollingEvery(Duration.ofSeconds(pollingTime))
               .ignoring(NoSuchElementException.class)
                   .withMessage("-----element is not found -----" + locator);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public   WebElement waitForElementVisibleWithFluentWait(By locator, long timeOut , long pollingTime) {
		Wait <WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                  .pollingEvery(Duration.ofSeconds(pollingTime))
                     .ignoring(NoSuchElementException.class)
                       .withMessage("-----element is not found -----" + locator);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public  String waitForTitleContains(String titleValue , long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		
		try{
			wait.until(ExpectedConditions.titleContains(titleValue));
			return driver.getTitle();
		}catch(TimeoutException e) {
			System.out.println(titleValue + "is not found");
			e.printStackTrace();
			return null;
		}
	}
	
	    //We need to provide full title
		public  String waitForTitleIs(String titleValue , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			
			try{
				wait.until(ExpectedConditions.titleIs(titleValue));
				return driver.getTitle();
			}catch(TimeoutException e) {
				System.out.println(titleValue + "is not found");
				e.printStackTrace();
				return null;
			}
		}
	
		/**
		 * An expectation for the URL of the current page to contain specific text.
		 * @param UrlValue
		 * @param timeOut
		 * @return
		 */
		//For partial url
		public  String waitForUrlContains(String UrlValue , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			try{
				wait.until(ExpectedConditions.urlContains(UrlValue));
				
			}catch(TimeoutException e) {
				System.out.println(UrlValue + "is not found");
				e.printStackTrace();
			}
			return driver.getCurrentUrl();
		}
		
		//For full url 
		public  String waitForUrlToBe(String UrlValue , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			try{
				wait.until(ExpectedConditions.urlToBe(UrlValue));
				
			}catch(TimeoutException e) {
				System.out.println(UrlValue + "is not found");
				e.printStackTrace();
			}
			return driver.getCurrentUrl();
		}
		
		//Private common method for below 3 method (js popup) - 1.accept the alert, 2.dismiss the alert & 3.enter the value and accept the alert
		private Alert  waitForAlert(long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		//For js popup - accept the alert
		public  String waitForJSAlertAndAccept(long timeOut) {
			Alert alt = waitForAlert(timeOut);
			String text = alt.getText();
			alt.accept();
			return text;
		}
		
		//For js popup - dismiss the alert
		public  String waitForJSAlertAndDismiss(long timeOut) {
			Alert alt = waitForAlert(timeOut);
			String text = alt.getText();
			alt.dismiss();
			return text;
		}
		
		//For js prompt popup - enter the value and accept
		public  String waitForJSPromptAlertAndEnterValue(String value , long timeOut) {
			Alert alt = waitForAlert(timeOut);
			String text = alt.getText();
			alt.sendKeys(value);
			alt.accept();
			return text;
		}
		
		//Wait & Frame using By Loactor
		public  void waitForFrameAndSwitchToIt(By frameLocator , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}

		//Wait & Frame using frame index
		public  void waitForFrameAndSwitchToIt(int frameIndex , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		
		//Wait & Frame using name or id
		public  void waitForFrameAndSwitchToIt(String frameIdOrName , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
		}
		
		//Wait & Frame using webelement
		public  void waitForFrameAndSwitchToIt(WebElement frameElement , long timeOut) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}
	
	
}
