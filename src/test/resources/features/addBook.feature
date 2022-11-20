Feature: Adding Book

  Background: : Get Token
    Given Generate token request is sent to related end point

    @wip3
  Scenario: Add Book to User Account
      When a POST request is sent for adding book
      Then Status code is 201 and book added is verified