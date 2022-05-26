Feature: User registration

  Scenario Outline: User registered with valid data
    When User sign up with valid username:'<username>' and password:'<password>' credentials
    Then User successfully registered

    Examples:
      | username | password |
      | b11      | !1Qq!1Qq |