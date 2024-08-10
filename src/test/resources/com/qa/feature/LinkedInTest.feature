@E2E_TEST
Feature: Mobile automation test cases for LinkedIn

  @LINKEDIN_TEST
  Scenario: LINKEDIN : user is able tosearch the organisation and goto settings page
    Given user validates the text on landing page
     #needed in case of running it on emulator
#    And user reaches to sign in page
    When user signs in with its credentials
    Then user reaches the home page and searches "callsign" in search bar
    And user validates all results have "callsign" in its search text
    And see all results button is displayed
    When user opens chat from home page
    Then user tap on filter icon with my connections on Search Messages
    When user tap on profile photo and settings button
    Then user scrolls down to the bottom till Linked in banner is displayed


