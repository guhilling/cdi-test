# language: en

Feature: Add values

  Scenario: Add two integers
    Given two integers with value 1
    When those integers get added
    Then the result should be 2