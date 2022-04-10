Feature: End To End Testing for Regres APIs

  @Integration
  Scenario: User is able to create a resource
    Given As an user
    When I add a resource by providing name and job
    Then I should get "201" response with id

  @Integration
  Scenario: User is able to get list of users
    Given A list of users are available
    And I specify the pagecount as "2"
    When I fetch the users
    Then I should get "200" response and page value as "2" in response body