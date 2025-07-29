package com.qa.setup;
import org.json.JSONObject;

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
	


	public static String addUpdateItem(String name, double price, String category) {
	    JSONObject json = new JSONObject();
	    json.put("name", name);
	    json.put("price", price);
	    json.put("category", category);
	    return json.toString();
	}
	
}

