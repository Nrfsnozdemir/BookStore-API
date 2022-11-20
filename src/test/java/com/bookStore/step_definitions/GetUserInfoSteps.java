package com.bookStore.step_definitions;

import com.bookStore.pages.ResponseApiPage;
import com.bookStore.utilities.BookStoreApiUtils;
import com.bookStore.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class GetUserInfoSteps {

    Response response;
    String requestUsername;
    public static String token;
    String responseUserID;
    String responseUserName;
    int responseStatusCode;
    List<Map<String, Object>> books;
    List<String> allBooksISBN_List;
    List<String> userBooksISBN_List;

    @When("Generate token request is sent to related end point")
    public void generateTokenRequestIsSentToRelatedEndPoint() {
        baseURI = ConfigurationReader.getProperty("baseUrl");
        basePath = ConfigurationReader.getProperty("apiGenerateToken");
        Response responseToken = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(new ResponseApiPage().getRequestBody()) // Body comes from POM
                .when().post(); // End point comes from Before method
        JsonPath jsonPath = responseToken.jsonPath();
        token = "Bearer " + jsonPath.getString("token");
        System.out.println("token = " + token);
    }

    @When("User sends GET request to receive user information")
    public void userSendsGETRequestToReceiveUserInformation() {
        baseURI = ConfigurationReader.getProperty("baseUrl");
        basePath = ConfigurationReader.getProperty("apiUser");
        String userID = BookStoreApiUtils.readFromFile();
        response = given().accept(ContentType.JSON)
                .and().header("Authorization", token)
                .and().get("/" + userID);
    }

    @And("User captures status code, userID, username and books information for GET")
    public void userCapturesStatusCodeUserIDUsernameAndBooksInformationForGET() {
        Map<Object, Object> responseMap = response.as(Map.class);
        responseUserID = (String) responseMap.get("userID");
        responseStatusCode = response.statusCode();
        responseUserName = (String) responseMap.get("username");
        books = (List<Map<String, Object>>) responseMap.get("books");
    }

    @And("User sends GET request to receive all books information")
    public void userSendsGETRequestToReceiveAllBooksInformation() {
        baseURI = ConfigurationReader.getProperty("baseUrl");
        basePath = ConfigurationReader.getProperty("apiAddBook");
        Response responseBooks = given().accept(ContentType.JSON)
                .and().get();
        JsonPath jsonPath = responseBooks.jsonPath();
        allBooksISBN_List = new ArrayList<>();
        allBooksISBN_List = jsonPath.getList("books.isbn");
    }

    @Then("User verifies status code, username and books information")
    public void userVerifiesStatusCodeUsernameAndBooksInformation() {
        assertEquals(201, responseStatusCode);
        assertEquals(requestUsername, responseUserName);
        System.out.println("books.size() = " + books.size());
        JsonPath jsonPath = response.jsonPath();
        userBooksISBN_List = new ArrayList<>();
        userBooksISBN_List = jsonPath.getList("books.isbn");
        assertTrue(allBooksISBN_List.containsAll(userBooksISBN_List));

    }

}
