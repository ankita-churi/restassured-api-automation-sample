package com.qa.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseConfig;
import com.qa.setup.PayloadBuilder;

import com.qa.util.*;
import io.restassured.response.Response;

@Listeners(ExtentListener.class)
public class GetItemsTests {
	
	@Test(priority = 1)
	public void getItemsNegative() {
		Response res =  given().log().all()
			.header("Content-Type","Application.json")
		.when().get(BaseConfig.BASE_URL + "/items")
		.then().assertThat().log().all().statusCode(204)
		.extract().response();
		
		ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());

	}
	
	
	@Test(priority = 2)
	public void getItems() {
		
			given()
	            .header("Content-Type","Application/json")
	            .body(PayloadBuilder.addUpdateItem("table",50,"furniture"))
	        .when()
	            .post(BaseConfig.BASE_URL + "/items")
	        .then().assertThat().statusCode(201).extract().response().asString();
			
			
		Response res = given().log().all()
		.header("Content-Type","Application.json")
		.when().get(BaseConfig.BASE_URL + "/items")
		.then().assertThat().log().all().statusCode(200).extract().response();
		
		ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
}

	
    
    
}
