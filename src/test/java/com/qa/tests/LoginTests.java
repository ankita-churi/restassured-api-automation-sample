package com.qa.tests;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.models.requests.LoginRequest;
import com.qa.models.response.LoginResponse;
import com.qa.setup.LoginService;
import com.qa.util.ExtentListener;

import io.restassured.response.Response;

@Listeners(ExtentListener.class)
public class LoginTests {

    @Test
    public void testValidLogin() {
    	LoginRequest loginRequest = new LoginRequest("admin", "secret");
    	LoginService loginService = new LoginService();
    	Response response = loginService.login(loginRequest);
    	System.out.println(response.asPrettyString());

    	Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");
    	// Deserialize to POJO
    	LoginResponse loginResponse = response.as(LoginResponse.class);
    	Assert.assertEquals(loginResponse.getMessage(), "Login successful", "Unexpected message");
    }
    
    @Test
    public void testInvalidLogin() {
    	
    	LoginRequest loginRequest = new LoginRequest("admin","xyz");
    	LoginService loginService = new LoginService();
    	Response response = loginService.login(loginRequest);
    	System.out.println(response.asPrettyString());
    	Assert.assertEquals(response.getStatusCode(), 401, "Unexpected status code");
    	// Deserialize to POJO
    	LoginResponse loginResponse = response.as(LoginResponse.class);
    	Assert.assertEquals(loginResponse.getError(), "Invalid credentials", "Unexpected message");

    }
}