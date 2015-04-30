Feature: Scenario Scope

  As a cucumber-cdi test framework provider
  I want to provide an easy way to understand the lifecycle of a bean

  Scenario: ScenarioScoped should maintain field for one scenario
    Given a boolean
    When I set the boolean to true
    Then the boolean should be true

  Scenario: ScenarioScoped should provide null fields on new scenario

  This scenario is executed after the first scenario. It does not
  set any values and does not perform an action.

    Then the boolean should be null