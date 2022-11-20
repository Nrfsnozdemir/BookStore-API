package com.bookStore.step_definitions;

import com.bookStore.pages.ResponseApiPage;
import com.bookStore.utilities.ConfigurationReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.*;

public class AddBookSteps {
    Response response;
    String isbn = "9781491904244";

    @When("a POST request is sent for adding book")
    public void aPOSTRequestIsSentForAddingBook() {
        baseURI = ConfigurationReader.getProperty("baseUrl");
        basePath = ConfigurationReader.getProperty("apiAddBook");
        String token = GetUserInfoSteps.token;
        System.out.println("token = " + token);
        response = given().header("Authorization", token)
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(new ResponseApiPage().addBookRequestBody("9781449365035"))
                .when().post();
        response.prettyPrint();
    }

    @Then("Status code is {int} and book added is verified")
    public void statusCodeIsAndBookAddedIsVerified(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
        String actualIsbn = response.jsonPath().getString("books[0].isbn");
        Assert.assertEquals(isbn, actualIsbn);
    }
}
