Feature: User authorization

  Background: There is set of generated user credentials
    Given Set of valid user credentials equals 3

  Scenario Outline: Registered user is authorized when he/she has valid token
    Given Registered users that have active valid token
    When User try to authorize
    Then Authorization response is '<response>'

    Examples:
      | response |
      | true     |

  Scenario Outline: Registered user is not authorized when he/she has no token
    Given Registered users that have no active valid token
    When User try to authorize
    Then Authorization response is '<response>'

    Examples:
      | response |
      | false    |

  Scenario Outline: Unregistered user is not able to authorize
    When User try to authorize
    Then The error message '<errorMessage>' and error code '<errorCode>' is received

    Examples:
      | errorMessage    | errorCode |
      | User not found! | 1207      |