
package com.qa.tests;

import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseConfig;
import com.qa.setup.BaseService;
import com.qa.setup.PayloadBuilder;
import com.qa.util.ExtentListener;
import com.qa.util.ExtentTestManager;
import com.qa.util.Utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Listeners(ExtentListener.class)
public class CreateItemTests extends BaseService {

    @Test(description = "Verify that Error is received when item name is kept blank")
    public void createItemWithBlankName() {
        String payload = PayloadBuilder.addUpdateItem(null, 11, "stationary");
        Response res = postRequest(payload, "/items");
        res.then()
            .assertThat()
            .statusCode(400)
            .body("error", equalTo("Item name is required and must be a non-empty string"));

        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
    }

    @Test
    public void createValidItem() {
        String item_name = Utils.getItemName();
        String payload = PayloadBuilder.addUpdateItem(item_name, 11, "stationary");

        Response res = postRequest(payload, "/items");
        res.then().assertThat().statusCode(201);

        JsonPath jsonPath = res.jsonPath();
        Map<String, Object> resMap = jsonPath.getMap("$");

        Assert.assertTrue(resMap.containsKey("id"));
        Assert.assertTrue(resMap.containsKey("name"));
        Assert.assertTrue(resMap.containsKey("price"));
        Assert.assertTrue(resMap.containsKey("category"));
        Assert.assertEquals(resMap.get("name"), item_name);

        ExtentTestManager.getTest().info("➡️ Request URL: " + BaseConfig.BASE_URL + "/items");
        ExtentTestManager.getTest().info("Request Body: " + payload);
        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());

        String item_id = jsonPath.getString("id");

        // cleanup
        Response res_delete_item = deleteRequest("/items/" + item_id);
        res_delete_item.then().assertThat().statusCode(200);

        ExtentTestManager.getTest().info("Cleanup Status Code: " + res_delete_item.getStatusCode());
        ExtentTestManager.getTest().info("Cleanup Response Body: " + res_delete_item.asPrettyString());
    }
}
