package com.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserApiSteps {
    private String baseUrl;
    private Map<String, Object> requestBody;
    private Response response;

    @Given("the API base URL is:")
    public void the_api_base_url_is(String url) {
        this.baseUrl = url.trim();
    }

    @Given("I have a request body:")
    public void i_have_a_request_body(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        for (Map<String, String> row : dataTable.asMaps()) {
            requestBody.put("title", row.get("title"));
            requestBody.put("body", row.get("body"));
            requestBody.put("userId", Integer.parseInt(row.get("userId")));
        }
    }

    @When("I send POST request to:")
    public void i_send_post_request_to(String endpoint) {
        response = given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(endpoint.trim());
    }

    @Then("the response status code should be OK")
    public void the_response_status_code_should_be_ok() {
        assertThat(response.getStatusCode()).isEqualTo(201);
    }


}
