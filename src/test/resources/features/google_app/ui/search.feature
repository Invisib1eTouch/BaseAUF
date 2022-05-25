Feature: Search request

  Background: User is on the Google Main page
    Given User proceeds to Google Main page

  Scenario Outline: Positive search test
    When User searches the '<request>'
    Then The list of <number_of_results> results is displayed
    Examples:
      | request        | number_of_results |
      | Test           | 9                 |
      | Simple request | 7                 |
