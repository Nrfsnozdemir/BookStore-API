Feature: Bookstore API Create User Functionality

  @wip
  Scenario: Create a user
    When User sends a POST request to create user end point
    And User captures status code, userID username information
    Then User verifies status code, username and userID is NOT null