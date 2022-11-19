package com.bookStore.step_definitions;

import com.bookStore.utilities.ConfigurationReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class CreateUserSteps {
    Response response;
    String requestUsername;
    String requestPassword;
    String responseUserID;
    File outFile;
    PrintWriter output;

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
    public void user_captures_status_code_userID_username_information() throws FileNotFoundException {
        // De-serialize response object to get UserID
        Map<Object, Object> responseMap = response.as(Map.class);
        responseUserID = (String) responseMap.get("userID");
        System.out.println("responseUserID = " + responseUserID);

        //Writing UserID to a regular resources file
        outFile = new File("C:\\Users\\avnur\\IdeaProjects\\demoQA-BookStore-API\\src\\test\\resources\\userID.out");
        if (outFile.exists()) {
            outFile.delete();
        }
        output = new PrintWriter(outFile);
        output.println(responseUserID);
        output.close(); // This step crucial to actually finalize writing function
    }

    @Then("User verifies status code, username and userID is NOT null")
    public void user_verifies_status_code_username_and_userID_is_NOT_null() throws FileNotFoundException {
        // Read the file for userID
        outFile = new File("C:\\Users\\avnur\\IdeaProjects\\demoQA-BookStore-API\\src\\test\\resources\\userID.out");
        Scanner scanner = new Scanner(outFile);
        String userID = scanner.next();
        System.out.println("userID = " + userID);
    }

}
