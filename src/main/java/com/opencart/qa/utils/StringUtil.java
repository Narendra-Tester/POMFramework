package com.opencart.qa.utils;

public class StringUtil {

	public static String getRandomEmailId() {
		return "auto"+System.currentTimeMillis()+"@nal.com";
	}
	
	//After multiple trial to much test data are genarted in the DB
	//That time we need to delete that data from the DB and we need to fire a query 
	//delete * from tablename where email like 'auto%';
	
	
	
	
}
