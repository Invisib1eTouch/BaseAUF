Feature: Date picker with time functionality

  Background: User is on page contains Date picker with time element
    Given User proceeds to page contains Date picker

  Scenario Outline: Set date by input
    When User input <year>-'<month>'-<day>-<hours>-<minutes> date to Date picker
    Then The set data matches the entered <year>-'<month>'-<day>-<hours>-<minutes> date
    Examples:
      | year | month     | day | hours | minutes |
      | 2000 | october   | 13  | 22    | 15      |
      | 2028 | september | 19  | 6     | 30      |
      | 1000 | october   | 13  | 22    | 15      |

  Scenario Outline: Set date by selecting in date picker
    When User selects <year>-'<month>'-<day>-<hours>-<minutes> date in Date picker
    Then The set data matches the entered <year>-'<month>'-<day>-<hours>-<minutes> date
    Examples:
      | year | month     | day | hours | minutes |
      | 2000 | october   | 13  | 22    | 15      |
      | 2028 | september | 19  | 6     | 30      |
      | 1000 | october   | 13  | 22    | 15      |