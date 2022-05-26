Feature: User registration

  Scenario Outline: User registered with valid data
    When User sign up with username:'<username>' and password:'<password>' credentials
    Then User successfully registered

    Examples:
      | username    | password    |
      | randomValid | randomValid |

  Scenario Outline: User is not able to register with invalid data
    When User sign up with username:'<username>' and password:'<password>' credentials
    Then User is not registered

    Examples:
      | username    | password    |
      | randomValid | a           |
      | randomValid | !aA1        |
      | randomValid | aaaaaaaa    |
      | randomValid | AAAAAAAAA   |
      | randomValid | 12345678    |
      | randomValid | !@#$%^&*    |
      | randomValid | aAaAaAaA    |
      | randomValid | aA1aA1aA    |
      | randomValid | A1!A2@A3    |
