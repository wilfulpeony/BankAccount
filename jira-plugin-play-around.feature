Feature: Jira plugin play around

  @XRAY-2
  Scenario: User login to system
    Given user doesn't have any account
    When user opens jira project
    And user enters his valid credentials
    Then user successfully logs in
