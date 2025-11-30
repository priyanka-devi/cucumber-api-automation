Feature: User API

  Scenario: Create a new post for a user
    Given the API base URL is:
        """
        https://jsonplaceholder.typicode.com
        """
    And I have a request body:
        | title | Learn API Testing |
        | body | Practicing API testing with JSONPlaceholder |
        | userId | 101 |
    When I send POST request to:
        """
        /posts
        """
    Then the response status code should be OK
    And the response body should be the same as request body