Feature: filtering list of bands

 Scenario: User is serching for specific band
    Given User is on serch page
    When User sets genre to "Rock"
    And User sets year to hihger than 2
    Then User should see bands that match his choices