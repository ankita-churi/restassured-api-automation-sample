package com.qa.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseConfig;
import com.qa.setup.PayloadBuilder;
import com.qa.util.ExtentListener;
import com.qa.util.ExtentTestManager;
import com.qa.util.Utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


@Listeners(ExtentListener.class)
public class CreateItemTests {
	@Test
    public void createItemWithBlankName() {
        
		Response res = 
			 given()
            .header("Content-Type","Application/json")
            .body(PayloadBuilder.addUpdateItem("",11,"stationary"))
            .when().post(BaseConfig.BASE_URL + "/items")
            .then().assertThat().log().all().statusCode(400).body("error",equalTo("Item name is required and must be a non-empty string"))
            .extract().response();
		
		 ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		 ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
           
    }
	
	@Test
	public void createValidItem() {
        
		Response res =  
			 given()
            .header("Content-Type","Application/json")
            .body(PayloadBuilder.addUpdateItem("Book",11,"stationary"))
            .when().post(BaseConfig.BASE_URL + "/items")
            .then().assertThat().statusCode(201).extract().response();
        
		String create_item_res_str = res.asString();

		 ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		 ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
        
        JsonPath jp = Utils.rawToJson(create_item_res_str);
		String item_id = jp.getString("id");
		System.out.println("ID is : "+ item_id);
        
		//cleanup
			given().header("Content-Type","Application/json").pathParam("item_id", item_id)
			.when().delete(BaseConfig.BASE_URL + "/items/{item_id}")
			.then().assertThat().log().all().statusCode(200);
			
			
    }
	
	

}
