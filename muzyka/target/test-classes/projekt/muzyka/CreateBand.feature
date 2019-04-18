Feature: Creating new band

  Scenario Outline: User creates a new band
    Given user creates band Metallica
    When user clicks next
    And such band does exist <exists>
    Then User should be told <result>

  Examples:
    | exists  |  result   |
    | "false" | "Success" |
    | "true"  | "Failure" |