package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	//1. Initial driver and Element Util
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//2. Page Class Construtor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. Private By locators: Page Object
	private final By emailId = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.cssSelector("input[type='submit']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By registerLink = By.linkText("Register");
	
	//4.Public page actions/methods:
	@Step("Getting login page title....")
	public String getLoginPageTitle() {
		String actTitle = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page Title : "+actTitle);
		return actTitle;
		
	}
	
	@Step("Getting login page url....")
	public String getLoginPageURL() {
		String actURL = eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_URL, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page URL : "+actURL);
		return actURL;
	}
	
	@Step("Checking forget password link exists on the login page .... ")
	public boolean  isForgetPwdLinkExists() {
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("User is logged-in with username: {0} and password: {1}")
	public HomePage doLogin(String userName , String pwd) {
		System.out.println("App Credentials : " +userName+" : " +pwd);
		eleUtil.doSendKeys(emailId, userName, AppConstants.MEDIUM_TIME_OUT);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		//After click operation on the login page it redirect to the home page, for that we write return type is homepage
		return new HomePage(driver);
	}
	
	@Step("Navigating to the register page ....")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementReadyAndClick(registerLink, AppConstants.SHORT_TIME_OUT);
		return new RegisterPage(driver);
	}
		
	
}
