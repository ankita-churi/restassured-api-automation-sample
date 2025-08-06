
package com.qa.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseService;
import com.qa.setup.PayloadBuilder;
import com.qa.util.ExtentListener;
import com.qa.util.ExtentTestManager;
import com.qa.util.Utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Listeners(ExtentListener.class)
public class UpdateItemTests extends BaseService {

    static String item_id;

    @BeforeClass
    public void setupItem() {
        String payload = PayloadBuilder.addUpdateItem("pencil", 50, "stationary");
        Response res = postRequest(payload, "/items");
        res.then().assertThat().statusCode(201);

        JsonPath jp = Utils.rawToJson(res.asString());
        item_id = jp.getString("id");
        System.out.println("Created item_id: " + item_id);
    }

    @Test
    public void updateItemWithInvalidId() {
        String payload = PayloadBuilder.addUpdateItem("stool", 50, "furniture");
        Response res = putRequest(payload, "/items/123");
        res.then().log().all().statusCode(404).body("error", equalTo("Item not found"));

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }

    @Test
    public void updateItemWithoutName() {
        String payload = PayloadBuilder.addUpdateItem("", 10, "tools");
        Response res = putRequest(payload, "/items/" + item_id);
        res.then().log().all().statusCode(400).body("error", equalTo("Item name must not be an empty string"));

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }

    @Test
    public void updateValidItem() {
        String payload = PayloadBuilder.addUpdateItem("Drill", 30, "machines");
        Response res = putRequest(payload, "/items/" + item_id);
        res.then().log().all().statusCode(200);

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());

        JsonPath jp = Utils.rawToJson(res.asString());
        String actual_name = jp.getString("name");
        Assert.assertEquals(actual_name, "Drill");
    }

    @AfterClass
    public void deleteItem() {
        Response res = deleteRequest("/items/" + item_id);
        res.then().log().all().statusCode(200);

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }
}
