Feature: User authorization

  Scenario Outline: Registered user authorizes when he/she has valid token
    When 3 new users are registered
    And Token is generated for users
    And Users try to authorize
    Then Authorization response is '<response>'

    Examples:
      | response |
      | true     |

  Scenario Outline: Registered user is not authorized when he/she doesn't have token
    When 3 new users are registered
    And Users try to authorize
    Then Authorization response is '<response>'

    Examples:
      | response |
      | false    |

  Scenario Outline: Unregistered user is not able to authorize
    When 3 set(s) of valid user credentials are generated
    And Users try to authorize
    Then The error message '<errorMessage>' and error code '<errorCode>' is received

    Examples:
      | errorMessage    | errorCode |
      | User not found! | 1207      |