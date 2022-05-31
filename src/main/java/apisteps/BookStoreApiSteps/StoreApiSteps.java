package apisteps.BookStoreApiSteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.BookModel;
import models.BooksModel;
import org.apache.http.HttpStatus;
import utils.FileService;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StoreApiSteps extends BaseApiSteps {
    private static final String BOOK_ENDPOINT = "/BookStore/v1/Book";

    private static final String BOOKS_ENDPOINT = "/BookStore/v1/Books";

    @When("User request for book with {string}")
    public void get_book_by_isbn(String isbn) {
        response = given()
                .param("ISBN", isbn)
                .when()
                .get(BOOK_ENDPOINT);
    }

    @When("User requests all books available in store")
    public void get_all_books() {
        response = given()
                .when()
                .get(BOOKS_ENDPOINT);
    }

    @Then("Correct book details info for book with {string} is received")
    public void book_details_info_verification(String isbn) throws IOException {
        BookModel expectedBook = null;
        BookModel actualBook = gson.fromJson(response.then().extract().body().asString(), BookModel.class);

        BooksModel books = gson.fromJson(FileService.readFile("src/test/resources/testData/books.json"), BooksModel.class);

        for (BookModel book : books.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                expectedBook = book;
                break;
            }
        }

        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(actualBook, is(equalTo(expectedBook)));
    }

    @Then("{int} books are received")
    public void number_of_books_verification(int numberOfBooks) {
        BooksModel books = gson.fromJson(response.then().extract().body().asString(), BooksModel.class);

        assertThat(books.getBooks().length, equalTo(numberOfBooks));
    }

    @Then("Correct data for all books are received")
    public void correct_books_data_received_verification() throws IOException {
        BooksModel responseBooks = gson.fromJson(response.then().extract().body().asString(), BooksModel.class);
        BooksModel testDataBooks = gson.fromJson(FileService.readFile("src/test/resources/testData/books.json"), BooksModel.class);

        int bookWithIsbnCounter = 0;

        for (BookModel responseBook : responseBooks.getBooks()) {
            for (BookModel testDataBook : testDataBooks.getBooks()) {
                if (responseBook.getIsbn().equals(testDataBook.getIsbn())) {
                    assertThat(responseBook, equalTo(testDataBook));
                    bookWithIsbnCounter++;
                    break;
                }
            }
        }

        assertThat(bookWithIsbnCounter, equalTo(testDataBooks.getBooks().length));
    }
}
