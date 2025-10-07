package com.qa.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
public class UpdateItemTests2 {

	    static String item_id;

	    @BeforeClass
	    public static void setupItem() {
	        String create_item_response = given()
	            .header("Content-Type","application/json")
	            .body(PayloadBuilder.addUpdateItem("pencil",50,"stationary"))
	        .when()
	            .post(BaseConfig.BASE_URL + "/items")
	        .then()
	        	.assertThat()
	            .statusCode(201)
	            .extract()
	            .asString();

	        JsonPath jp = Utils.rawToJson(create_item_response);
	        item_id = jp.getString("id");
	        System.out.println("Created item_id: " + item_id);
	    }

	 
	@Test
    public void updateItemWithInvalidId() {
        Response res = given()
            .header("Content-Type","Application/json")
            .pathParam("item_id", 123)
            .body(PayloadBuilder.addUpdateItem("stool",50,"furniture"))
        .when().put(BaseConfig.BASE_URL + "/items/{item_id}")
        .then().log().all().statusCode(404).body("error",equalTo("Item not found")).extract().response();
           
        
        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }
	
	 
	@Test
	public void updateItemWithoutkName() {
		Response res = given()
	        .header("Content-Type","Application/json")
	        .pathParam("item_id", item_id)
	        .body(PayloadBuilder.addUpdateItem("",10,"tools"))
	        .when().put(BaseConfig.BASE_URL + "/items/{item_id}")
	        .then().log().all().statusCode(400).body("error",equalTo("Item name must not be an empty string")).extract().response();
	        
	        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
			ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
	           
	    }
	@Test
	public void updateValidItem() {
      
			Response res =  
			given()
	        .header("Content-Type","Application/json")
	        .pathParam("item_id", item_id)
	        .body(PayloadBuilder.addUpdateItem("Drill",30,"machines"))
	        .when().put(BaseConfig.BASE_URL + "/items/{item_id}")
	        .then().log().all().statusCode(200).extract().response();
          
			 ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
			 ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
			 
			 String update_item_response = res.asString();
			 JsonPath jp = Utils.rawToJson(update_item_response);
			 String actual_name = jp.getString("name");
			 Assert.assertEquals(actual_name, "Drill");
    }
	
	@AfterClass
	public void deleteItem() {
		//cleanup
				Response res = given().header("Content-Type","Application/json").pathParam("item_id", item_id)
				.when().delete(BaseConfig.BASE_URL + "/items/{item_id}")
				.then().log().all().statusCode(200).extract().response();
				
				 ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
				 ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
				
	}
	

}
