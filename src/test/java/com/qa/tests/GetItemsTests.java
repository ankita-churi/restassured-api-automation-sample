
package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseService;
import com.qa.setup.PayloadBuilder;
import com.qa.util.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

@Listeners(ExtentListener.class)
public class GetItemsTests extends BaseService {

//    @Test(priority = 1)
//    public void getItemsNegative() {
//        Response res = getRequest("/items");
//        res.then().assertThat().statusCode(204);
//
//        ExtentTestManager.getTest().info("Status Code: " + res.getStatusCode());
//        ExtentTestManager.getTest().info("Response Body: " + res.asPrettyString());
//    }

    @Test(priority = 2)
    public void getItems() {
        String item_name = Utils.getItemName();
        String payload = PayloadBuilder.addUpdateItem(item_name, 50, "furniture");

        Response res = postRequest(payload, "/items");
        res.then().assertThat().statusCode(201);

        Response res2 = getRequest("/items");
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
