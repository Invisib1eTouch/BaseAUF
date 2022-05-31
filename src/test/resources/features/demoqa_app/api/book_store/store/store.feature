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