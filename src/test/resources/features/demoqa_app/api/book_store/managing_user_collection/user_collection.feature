Feature: Managing user collection

  Scenario: User adds book to collection
    Given 1 new users are registered
    And Token is generated for users
    When User requests all books available in store
    And Any book is added to user's collection
    Then Book(s) is successfully added user's collection

  Scenario: User removes book from collection
    Given 1 new users are registered
    And Token is generated for users
    When User requests all books available in store
    And Any book is added to user's collection
    And Book is removed from user collection
    Then User has the same books in collection as before

  Scenario: User is not able to add the same book to collection twice
    Given 1 new users are registered
    And Token is generated for users
    When User requests all books available in store
    And User tries to add the same book to the user collection twice
    Then Only one instance of book is added to user's collection
