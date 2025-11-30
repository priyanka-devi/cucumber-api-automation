package com.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserApiSteps {
    private String baseUrl;
    private Map<String, Object> requestBody;
    private Response response;
    private Integer postIdToDelete;
    private Integer postIdToUpdate;

    @Given("the API base URL is:")
    public void the_api_base_url_is(String url) {
        this.baseUrl = url.trim();
    }

    @Given("I have a request body:")
    public void i_have_a_request_body(io.cucumber.datatable.DataTable dataTable) {
        requestBody = new HashMap<>();
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        requestBody.put("title", map.get("title"));
        requestBody.put("body", map.get("body"));
        requestBody.put("userId", Integer.valueOf(map.get("userId")));
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

    @Then("the response body should be the same as request body")
    public void the_response_body_should_be_the_same_as_request_body(){
        Map responseJson = response.as(Map.class);
        assertThat(responseJson.get("title")).
                as("Validate title").
                isEqualTo(requestBody.get("title"));
        assertThat(responseJson.get("body")).
                as("Validate body").
                isEqualTo(requestBody.get("body"));
        assertThat(responseJson.get("userId")).
                as("Validate userId").
                isEqualTo(requestBody.get("userId"));
    }

    @Then("the response should match the post-put JSON schema")
    public void the_response_should_match_the_post_json_schema() {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/post_schema.json"));
    }

    @When("I send GET request to endpoint:")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = given()
                .baseUri(baseUrl)
                .when()
                .get(endpoint.trim());
    }

    @Then("the id for each element in the response should not be null")
    public void the_response_should_not_be_null_for_all_data_in_post(){
        List<Map<String, Object>> posts = response.as(List.class);
        assertThat(posts).
                as("Can not be null").
                isNotEmpty();
        for (int i = 0; i < posts.size(); i++){
            Map<String, Object> post = posts.get(i);
            assertThat(post.get("id"))
                    .as("Validate id not null for index" + i)
                    .isNotNull();
        }
    }

    @Then("the response should match the list of post JSON schema")
    public void the_response_should_match_the_list_of_post_json_schema(){
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/list_of_post_schema.json"));
    }

    @When("I want to delete data with id: {int}")
    public void i_want_to_delete_data_with_id(Integer id){
        this.postIdToDelete = id;
    }

    @When("I send DELETE request to endpoint:")
    public void i_send_delete_request_to_endpoint(String endpoint) {
        String useEndpoint = endpoint.trim().replace("{id}", String.valueOf(postIdToDelete));
        response = given()
                .baseUri(baseUrl)
                .when()
                .delete(useEndpoint);
    }

    @Then("the response status code should be : {int}")
    public void the_response_status_code_should_be(Integer expectedStatus){
        assertThat(response.getStatusCode())
                .as("Validate HTTP status code")
                .isEqualTo(expectedStatus);
    }

    @Then("the response should display empty object")
    public void the_response_should_display_empty_object(){
        Map<String, Object> jsonResponse = response.as(Map.class);
        assertThat(jsonResponse)
                .as("DELETE response must be empty object {}")
                .isEmpty();
    }

    @Then("the response should match the delete JSON schema")
    public void the_response_should_match_the_delte_json_schema(){
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/delete_schema.json"));
    }

    @Given("I want to update data with id: {int}")
    public void i_want_to_update_data_with_id(Integer id) {
        this.postIdToUpdate = id;
    }

    @When("I send PUT request to:")
    public void i_send_put_request_to(String endpoint) {
        String useEndpoint = endpoint.trim()
                .replace("{id}", String.valueOf(postIdToUpdate));

        requestBody.put("id", postIdToUpdate);

        response = given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put(useEndpoint);
    }
}
