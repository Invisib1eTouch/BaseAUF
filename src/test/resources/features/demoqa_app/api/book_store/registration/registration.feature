Feature: User registration

  Scenario: User registered with valid data
    When User sign up with valid random username and password credentials
    Then User successfully registered

  Scenario Outline: User is not able to register with invalid data
    When User sign up with username:'<username>' and password:'<password>' credentials
    Then The error message '<errorMessage>' with error code '<errorCode>' is received
    And User is not registered

    Examples:
      | username   | password     | errorMessage                                                                                                                                                                                                           | errorCode |
      |            | n9V@j5S&y1U$ | UserName and Password required.                                                                                                                                                                                        | 1200      |
      | ZqHwuBnzvH |              | UserName and Password required.                                                                                                                                                                                        | 1200      |
      |            |              | UserName and Password required.                                                                                                                                                                                        | 1200      |
      | ZqHwuBnzvH | a            | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | !aA1         | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | aaaaaaaa     | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | AAAAAAAAA    | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | 12345678     | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | !@#$%^&*     | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | aAaAaAaA     | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | aA1aA1aA     | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
      | ZqHwuBnzvH | A1!A2@A3     | Passwords must have at least one non alphanumeric character, one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), one lowercase (\'a\'-\'z\'), one special character and Password must be eight characters or longer. | 1300      |
