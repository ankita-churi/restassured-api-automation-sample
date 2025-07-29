package com.qa.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
public class DeleteItemTests {
	@Test
    public void deleteinvalidItem() {
        
		Response res = given()
            .header("Content-Type","Application/json")
        .when().delete(BaseConfig.BASE_URL + "/items" + "/123")
        .then().assertThat().log().all().statusCode(404).body("error",equalTo("Not found")).extract().response();

		 ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		 ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
           
    }
	
	@Test
	public void deleteValidItem() {
		
        String create_item_response = 
        given()
            .header("Content-Type","Application/json")
            .body(PayloadBuilder.addUpdateItem("sofa",100,"furniture"))
        .when().post(BaseConfig.BASE_URL + "/items")
        .then().log().all().statusCode(201).extract().response().asString();
        
        JsonPath jp = Utils.rawToJson(create_item_response);
		String item_id = jp.getString("id");
		System.out.println("ID is : "+ item_id);
		
		Response res = given().header("Content-Type","Application/json").pathParam("item_id", item_id)
		.when().delete(BaseConfig.BASE_URL + "/items/{item_id}")
		.then().assertThat().log().all().statusCode(200).extract().response();
        
		
		 ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
		 ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
            
    }
	
	
	

}
