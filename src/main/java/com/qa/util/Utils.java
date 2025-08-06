package com.qa.util;

import com.github.javafaker.Faker;

import io.restassured.path.json.JsonPath;


public class Utils {

	public static JsonPath rawToJson(String response)
	{
		JsonPath js1 =new JsonPath(response);
		return js1;
	}
	
	 public static String getItemName(){
	        return new Faker().commerce().productName();
	    }
}
