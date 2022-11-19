package com.bookStore.step_definitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class CreateUserSteps {

    String url = "https://demoqa.com";

    @Test
    public void test() {

        Response response = RestAssured.get(url);

        System.out.println(response.statusCode());

        response.prettyPrint();
    }
}
