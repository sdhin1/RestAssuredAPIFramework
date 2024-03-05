package com.spotify.oauth2.api;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.response.Response;

public class RestResource {
	
	public static Response post(String path, String token, Object requestPlaylist) {
		
		return given(getRequestSpec()).
				body(requestPlaylist).
				auth().oauth2(token).
		when().
			post(path).
		then().
			spec(getResponseSpec()).
			extract().
			response();
		
	}
	
	public static Response postAccount(Map<String, String> formParams) {
		
		return given(getAccountRequestSpec()).
				formParams(formParams).
				log().all().
			when().
				post("/api/token").
			then().
				spec(getResponseSpec()).
				extract().
				response();
		
	}
	
	public static Response get(String path, String token) {
		
		return given(getRequestSpec()).
				auth().oauth2(token).
		when().
			get(path).
		then().
			spec(getResponseSpec()).
			extract().
			response();
		
	}
	
	public static Response put(String path, String token, Object requestPlaylist) {
		
		return given(getRequestSpec()).
			body(requestPlaylist).
			auth().oauth2(token).
		when().
			put(path).
		then().
			spec(getResponseSpec()).
			extract().
			response();
	
		
	}

}
