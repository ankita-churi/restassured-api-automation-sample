package com.qa.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseConfig;
import com.qa.setup.PayloadBuilder;
import com.qa.util.ExtentListener;
import com.qa.util.ExtentTestManager;
import com.qa.util.Utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Listeners(ExtentListener.class)
public class GetItemsTests2 {
	
//	@Test(priority = 1)
//	public void getItemsNegative() {
//		RequestSpecification reqSpec  =  
//				RestAssured.given()
//				.baseUri(BaseConfig.BASE_URL )
//				.basePath("/items")
//				.header("Content-Type","Application/json");
//		
//		Response res = reqSpec.get();
//		
//		res.then().assertThat().statusCode(204);
//		
//		ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
//		ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
//
//	}
//	
	
	@Test(priority = 2)
	public void getItems() {
		
		String item_name = Utils.getItemName();
		String payload = PayloadBuilder.addUpdateItem(item_name,50,"furniture");

		
		RequestSpecification reqSpec  =  
				RestAssured.given()
				.baseUri(BaseConfig.BASE_URL )
				.basePath("/items")
				.header("Content-Type","Application/json")
				.body(payload);
	
		Response res = reqSpec.post(); 
	        res.then().assertThat().statusCode(201);
		
	        RequestSpecification reqSpec2  =  
					RestAssured.given()
					.baseUri(BaseConfig.BASE_URL )
					.basePath("/items")
					.header("Content-Type","Application/json");
	        Response res2 = reqSpec2.get(); 
	        res2.then().assertThat().statusCode(200);
		
	    
	        JsonPath jsonPath = res2.jsonPath();
	        List<Map<String, Object>> resList = jsonPath.getList("$");

	        for (Map<String, Object> item : resList) {
	            Assert.assertTrue(item.containsKey("id"));
	            Assert.assertTrue(item.containsKey("name"));
	            Assert.assertTrue(item.containsKey("price"));
	            Assert.assertTrue(item.containsKey("category"));
	        }

		ExtentTestManager.getTest().info("Status Code: " + res2.getStatusCode());
		ExtentTestManager.getTest().info("Response Body: " + res2.asPrettyString());
}

	
    
    
}
