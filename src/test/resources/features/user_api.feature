Feature: User API

  Scenario: Able to create a user
    Given the API base URL is:
        """
        https://jsonplaceholder.typicode.com
        """
    And I have a request body:
      | title  | Learn API Testing                           |
      | body   | Practicing API testing with JSONPlaceholder |
      | userId | 101                                         |
    When I send POST request to:
        """
        /posts
        """
    Then the response status code should be OK
    Then the response body should be the same as request body
    Then the response should match the post-put JSON schema

  Scenario: Able to Get data from /posts endpoint
    Given the API base URL is:
        """
        https://jsonplaceholder.typicode.com
        """
    When I send GET request to endpoint:
        """
        /posts
        """
    Then the id for each element in the response should not be null
    Then the response should match the list of post JSON schema

  Scenario: Able to Delete data from /posts with id = 1
    Given the API base URL is:
        """
        https://jsonplaceholder.typicode.com
        """
    And I want to delete data with id: 1
    When I send DELETE request to endpoint:
        """
        /posts/{id}
        """
    Then the response status code should be : 200
    Then the response should display empty object
    Then the response should match the delete JSON schema

  Scenario: Able to Update data in /posts for id = 1
    Given the API base URL is:
        """
        https://jsonplaceholder.typicode.com
        """
    And I have a request body:
          | title  | Updated Post Title                           |
          | body   | This is the updated body content             |
          | userId | 99                                           |
    And I want to update data with id: 1
    When I send PUT request to:
            """
            /posts/{id}
            """
    Then the response body should be the same as request body
    Then the response should match the post-put JSON schema