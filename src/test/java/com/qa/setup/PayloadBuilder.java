package com.qa.setup;

public class PayloadBuilder {

	
	public static String validLogin() {
		return "{\r\n"
				+ "  \"username\": \"admin\",\r\n"
				+ "  \"password\": \"secret\"\r\n"
				+ "}";
		
	}
	
	public static String invalidLogin() {
		return "{\r\n"
				+ "  \"username\": \"invalid\",\r\n"
				+ "  \"password\": \"secret\"\r\n"
				+ "}";
		
	}
	

	
public static String addUpdateItem(String name,int price,String category){
		
		return "{\r\n"
				+ "  \"name\":\""+name+"\",\r\n"
				+ "  \"price\": "+price+",\r\n"
				+ "  \"category\": \""+category+"\"\r\n"
				+ "}";
	}


}
