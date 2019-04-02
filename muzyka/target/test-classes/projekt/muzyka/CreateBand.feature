Feature: Creating new band
  User creates a new band

  Scenario: Band does not exist yet
    Given user creates band Metallica
    When user clicks next
    And such band does exist "false"
    Then User should be told "Success"

  Scenario: Band does exist 
    Given user creates band Metallica
    When user clicks next
    And such band does exist "true"
    Then User should be told "Failure"