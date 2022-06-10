Feature: Managing user collection

  Scenario: User added book to collection
    Given 1 new user(s) are registered
    And Token is generated for user(s)
    When User requests all books available in store
    And Any book(s) is added to user's collection
    Then Book(s) is successfully added user's collection

  Scenario: User remove book from collection
    Given 1 new user(s) are registered
    And Token is generated for user(s)
    When User requests all books available in store
    And Any book(s) is added to user's collection
    And Book is removed from user collection
    Then Book is successfully removed from collection

  Scenario: User remove all books from collection
    Given 1 new user(s) are registered
    And Token is generated for user(s)
    When User requests all books available in store
    And Add all books from store to collection
    And All books are removed from collection
    Then Book is successfully removed from collection