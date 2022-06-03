Feature: Book Store feature

  Scenario Outline: Get book details info by ISBN value
    When User request for book with '<ISBN>'
    Then Correct book details info for book with '<ISBN>' is received

    Examples:
      | ISBN          |
      | 9781449325862 |

  Scenario Outline: Get all books from store
    When User requests all books available in store
    Then <numberOfBooks> books are received
    And Correct data for all books are received

    Examples:
      | numberOfBooks |
      | 8             |

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