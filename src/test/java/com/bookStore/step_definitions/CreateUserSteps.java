package com.bookStore.step_definitions;

import com.bookStore.utilities.ConfigurationReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class CreateUserSteps {
    Response response;
    String requestUsername;
    String requestPassword;

    @When("User sends a POST request to create user end point")
    public void user_sends_a_POST_request_to_create_user_end_point() {
        // Body part of the request
        requestUsername = ConfigurationReader.getProperty("userName");
        requestPassword = ConfigurationReader.getProperty("password");
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("userName", requestUsername);
        requestBody.put("password", requestPassword);

        // End point of request
        baseURI = ConfigurationReader.getProperty("baseUrl");
        basePath = ConfigurationReader.getProperty("apiUser");

        // Creating request to API
        response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestBody)
                .when().post();
    }

    @When("User captures status code, userID username information")
    public void user_captures_status_code_userID_username_information() {
        response.prettyPrint();
    }

    @Then("User verifies status code, username and userID is NOT null")
    public void user_verifies_status_code_username_and_userID_is_NOT_null() {

    }

}
