package com.qa.setup;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseService {   //wrapper for restAssured

	
	String BASE_URL = BaseConfig.BASE_URL;
	private RequestSpecification reqSpecification;
	
	public BaseService() {
		
		reqSpecification = given().baseUri(BASE_URL);
	}
	
	protected Response postRequest(Object payload, String endpoint) {
		return reqSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
	}
	
	protected Response putRequest(Object payload, String endpoint) {
	    return reqSpecification.contentType(ContentType.JSON).body(payload).put(endpoint);
	}

	protected Response getRequest(String endpoint) {
	    return reqSpecification.get(endpoint);
	}

	protected Response deleteRequest(String endpoint) {
	    return reqSpecification.delete(endpoint);
	}

}
