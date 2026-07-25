package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic 100: Design login page for Open Cart Application")
@Story("LoginUS 200: Add login page feature with title, user login, url etc.....")
public class LoginPageTest extends BaseTest{
	
	@Description(" Checking Login Page Title")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("Starting Login Page Title Test");
		Assert.assertEquals(loginPage.getLoginPageTitle() ,AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description(" Checking Login Page Url....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginPageURLTest() {
		Assert.assertTrue(loginPage.getLoginPageURL().contains(AppConstants.LOGIN_PAGE_URL));
	}
	
	@Description(" Checking Forget Password Link Exists on the Login Page .....")
	@Severity(SeverityLevel.BLOCKER)
	@Issue(" Bug 9001: forget password link is missing on the login page")
	@Test
	public void forgetPwdLinkExistsTest() {
		Assert.assertTrue(loginPage.isForgetPwdLinkExists());
	}
	
	@Description(" Checking User Is Able to Login With Valid Credentials .....")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=Integer.MAX_VALUE)
	public void loginTest() {
		homePage =  loginPage.doLogin(prop.getProperty("username").trim() , prop.getProperty("password").trim());
		ChainTestListener.log("Home Page Title : "+ homePage.getHomePageTitle());
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE);
	}

}
