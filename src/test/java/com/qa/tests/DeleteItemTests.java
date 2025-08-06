
package com.qa.tests;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;
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
public class DeleteItemTests extends BaseService {

    @Test
    public void deleteinvalidItem() {
        Response res = deleteRequest("/items/123");
        res.then().assertThat().log().all().statusCode(404).body("error", equalTo("Not found"));

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }

    @Test
    public void deleteValidItem() {
        String payload = PayloadBuilder.addUpdateItem("sofa", 100, "furniture");
        Response createRes = postRequest(payload, "/items");
        createRes.then().log().all().statusCode(201);

        JsonPath jp = Utils.rawToJson(createRes.asString());
        String item_id = jp.getString("id");

        Response res = deleteRequest("/items/" + item_id);
        res.then().assertThat().log().all().statusCode(200);

        JsonPath jp1 = Utils.rawToJson(res.asString());
        String actual_message = jp1.getString("message");
        Assert.assertEquals(actual_message, "Item deleted successfully");
        String actual_id = jp1.getString("deletedId");
        Assert.assertEquals(actual_id, item_id);

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }
}
