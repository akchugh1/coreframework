@E2E_TEST
Feature: Mobile automation test cases for zoom

  @ZOOM_TEST
  Scenario: ZOOM : user is able to see the error pop while joining zoom meeting
    Given user clicks on join meeting
    Then user validates join button is "disabled"
    When user enters any 9 digits meeting id
    Then user validates join button is "enabled"
    When user toggles "on" Turn off my video button
    And user clicks on join button
    And user relaunches the app after putting in background for 4 seconds
    Then user validates the invalid meeting id in popup message


