Feature: Validating Place API's

  Scenario: Verify Add Place Functionality
    Given I have Add Place Payload
    When I call "AddPlaceAPI" with Post HTTP Request
    Then I should get the Success Response with Status Code 200
