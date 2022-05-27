Feature: User registration

  Scenario Outline: User registered with valid data
    When User sign up with username:'<username>' and password:'<password>' credentials
    Then User successfully registered

    Examples:
      | username    | password    |
      | randomValid | randomValid |

  Scenario Outline: User is not able to register with invalid data
    When User sign up with username:'<username>' and password:'<password>' credentials
    Then The error message '<errorMessage>' with error code '<errorCode>' is received
    And User is not registered

    Examples:
      | username    | password    | errorMessage                                                                                                                                                                                                           | errorCode |
      |             | randomValid | UserName and Password required.                                                                                                                                                                                        | 1200      |
      | randomValid |             | UserName and Password required.                                                                                                                                                                                        | 1200      |
      |             |             | UserName and Password required.                                                                                                                                                                                        | 1200      |
      | randomValid | a           | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | !aA1        | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | aaaaaaaa    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | AAAAAAAAA   | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | 12345678    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | !@#$%^&*    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | aAaAaAaA    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | aA1aA1aA    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | randomValid | A1!A2@A3    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
