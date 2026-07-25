package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.CsvUtil;
import com.opencart.qa.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserRegTestData() {
		return new Object[][] {
			{"Swati", "Sharma", "8597589645","Swati@123","yes"},
			{"Nitin", "Bansal", "8597559648","Nitin@123","no"},
			{"Piyush", "Patil", "8597789648","Piyush@123","yes"},
		};
	}
	
	@DataProvider
	public Object[][] getUserRegExcelTestData(){
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@DataProvider
	public Object[][] getUserRegCSVTestData(){
		return CsvUtil.csvData(AppConstants.REGISTER_SHEET_NAME1);
	}
	
	
    @Test(dataProvider="getUserRegCSVTestData")
    public void userRegisterTest(String firstName, String lastName,String telephone, String password, String subcribe) {
    	Assert.assertTrue(registerPage.userRegistration(firstName, lastName, telephone,password,subcribe));
    }
    
    
    

	
	
	
	
	
	
	
	
}
