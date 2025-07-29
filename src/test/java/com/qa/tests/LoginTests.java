package com.qa.tests;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.setup.BaseConfig;
import com.qa.setup.PayloadBuilder;
import com.qa.util.ExtentListener;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Listeners(ExtentListener.class)
public class LoginTests {

    @Test
    public void testValidLogin() {
        given()
            .header("Content-Type","Application/json")
            .body(PayloadBuilder.validLogin())
        .when()
            .post(BaseConfig.BASE_URL + "/login")
        .then()
            .statusCode(200)
            .body("message", equalTo("Login successful"));
        
        
    }
    
    @Test
    public void testInvalidLogin() {
        given()
            .header("Content-Type","Application/json")
            .body(PayloadBuilder.invalidLogin())
        .when()
            .post(BaseConfig.BASE_URL + "/login")
        .then()
            .statusCode(401)
            .body("error", equalTo("Invalid credentials"));
    }
}