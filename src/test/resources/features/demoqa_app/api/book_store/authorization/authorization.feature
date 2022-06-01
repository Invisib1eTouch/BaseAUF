Feature: User authorization

  Scenario Outline: Registered user is authorized when he/she has valid token
    When 3 new user(s) are registered
    And Token is generated for user(s)
    And User(s) try to authorize
    Then Authorization response is '<response>'

    Examples:
      | response |
      | true     |

  Scenario Outline: Registered user is not authorized when he/she has no token
    When 3 new user(s) are registered
    And User(s) try to authorize
    Then Authorization response is '<response>'

    Examples:
      | response |
      | false    |

  Scenario Outline: Unregistered user is not able to authorize
    When 3 set(s) of valid user credentials are generated
    And User(s) try to authorize
    Then The error message '<errorMessage>' and error code '<errorCode>' is received

    Examples:
      | errorMessage    | errorCode |
      | User not found! | 1207      |