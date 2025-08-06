package com.qa.setup;

import com.qa.models.requests.LoginRequest;

import io.restassured.response.Response;

public class LoginService extends BaseService{

	
	private static final  String BASE_PATH = "/login";
	
	public Response login(LoginRequest payload) {
		return postRequest(payload, BASE_PATH);
	}
	
}
