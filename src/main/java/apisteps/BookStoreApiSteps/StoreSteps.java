package apisteps.BookStoreApiSteps;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.BookModel;
import models.BooksModel;
import models.DeleteBookModel;
import models.addListOfBooks.AddListOfBooks;
import models.addListOfBooks.CollectionOfIsbns;
import org.apache.http.HttpStatus;
import utils.DataGenerator;
import utils.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StoreSteps extends BaseApiSteps {
    private static final String BOOK_ENDPOINT = "/BookStore/v1/Book";

    private static final String BOOKS_ENDPOINT = "/BookStore/v1/Books";

    public static List<BookModel> bookModels = new ArrayList<>();

    public static Response storeStepsResponse;

    @After
    public void cleanup() {
        bookModels.clear();
    }

    private BooksModel getBooksModelFromStoreStepsResponse() {
        String storeStepsResponseBody = storeStepsResponse.then().extract().body().asString();
        return gson.fromJson(storeStepsResponseBody, BooksModel.class);
    }

    private BookModel getRandomBookFromBooksModel(BooksModel model) {
        int randomBookIndex = DataGenerator.getRandomNumber(0, model.getBooks().length - 1);
        return model.getBooks()[randomBookIndex];
    }

    @When("User requests for book with {string}")
    public void get_book_by_isbn(String isbn) {
        storeStepsResponse = given()
                .param("ISBN", isbn)
                .when()
                .get(BOOK_ENDPOINT);
    }

    @When("User requests all books available in store")
    public void get_all_books() {
        storeStepsResponse = given()
                .when()
                .get(BOOKS_ENDPOINT);
    }

    @When("Any book is added to user's collection")
    public void add_book_to_user_collection() {
        BooksModel booksInStore = getBooksModelFromStoreStepsResponse();

        BookModel randomBookFromStore = getRandomBookFromBooksModel(booksInStore);

        for (int i = 0; i < AuthorizationSteps.userCredentialsModels.size(); i++) {
            bookModels.add(randomBookFromStore);

            CollectionOfIsbns bookIsbn = new CollectionOfIsbns();
            bookIsbn.setIsbn(bookModels.get(i).getIsbn());

            CollectionOfIsbns[] collectionOfIsbns = {bookIsbn};

            String userID = AuthorizationSteps.userData.get(i).then().extract().jsonPath().get("userID");

            AddListOfBooks addListOfBooks = new AddListOfBooks();
            addListOfBooks.setUserId(userID);
            addListOfBooks.setCollectionOfIsbns(collectionOfIsbns);

            given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                    .contentType(ContentType.JSON)
                    .body(addListOfBooks)
                    .when()
                    .post(BOOKS_ENDPOINT);
        }
    }

    @When("Book is removed from user collection")
    public void remove_book_from_collection() {
        List<Response> userData = AuthorizationSteps.userData;

        for (int i = 0; i < userData.size(); i++) {
            String userID = userData.get(i).then().extract().jsonPath().get("userID");
            String isbn = bookModels.get(i).getIsbn();

            DeleteBookModel deleteBookModel = new DeleteBookModel();
            deleteBookModel.setUserId(userID);
            deleteBookModel.setIsbn(isbn);

            storeStepsResponse = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                    .contentType(JSON)
                    .body(gson.toJson(deleteBookModel))
                    .when()
                    .delete(BOOK_ENDPOINT);
        }
    }

    @When("User tries to add the same book to the user collection twice")
    public void user_adds_the_same_book_to_collection_two_times() {
        BooksModel booksInStore = getBooksModelFromStoreStepsResponse();

        BookModel randomBookFromStore = getRandomBookFromBooksModel(booksInStore);
        bookModels.add(randomBookFromStore);

        CollectionOfIsbns bookIsbn = new CollectionOfIsbns();
        bookIsbn.setIsbn(randomBookFromStore.getIsbn());

        CollectionOfIsbns[] collectionOfIsbns = {bookIsbn};

        for (int i = 0; i < AuthorizationSteps.userCredentialsModels.size(); i++) {
            String userID = AuthorizationSteps.userData.get(i).then().extract().jsonPath().get("userID");

            AddListOfBooks addListOfBooks = new AddListOfBooks();
            addListOfBooks.setUserId(userID);
            addListOfBooks.setCollectionOfIsbns(collectionOfIsbns);

            for (int j = 0; j < 2; j++) {
                given()
                        .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                        .contentType(ContentType.JSON)
                        .body(addListOfBooks)
                        .when()
                        .post(BOOKS_ENDPOINT);
            }
        }
    }

    @When("Book is removed from one of users")
    public void book_is_removed_from_first_user() {
        List<Response> userData = AuthorizationSteps.userData;

        String userID = userData.get(0).then().extract().jsonPath().get("userID");
        String isbn = bookModels.get(0).getIsbn();

        DeleteBookModel deleteBookModel = new DeleteBookModel();
        deleteBookModel.setUserId(userID);
        deleteBookModel.setIsbn(isbn);

        given()
                .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(0).getToken())
                .contentType(JSON)
                .body(gson.toJson(deleteBookModel))
                .when()
                .delete(BOOK_ENDPOINT);
    }

    @Then("Correct book details info for book with {string} is received")
    public void book_details_info_verification(String isbn) throws IOException {
        String actualBookResponseBody = storeStepsResponse.then().extract().body().asString();
        String testBooksModelsAsString = FileService.readFile("src/test/resources/testData/books.json");

        BookModel expectedBook = null;
        BookModel actualBook = gson.fromJson(actualBookResponseBody, BookModel.class);

        BooksModel books = gson.fromJson(testBooksModelsAsString, BooksModel.class);

        for (BookModel book : books.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                expectedBook = book;
                break;
            }
        }

        assertThat(storeStepsResponse.getStatusCode(), equalTo(HttpStatus.SC_OK));
        assertThat(actualBook, is(equalTo(expectedBook)));
    }

    @Then("{int} books are received")
    public void number_of_books_verification(int numberOfBooks) {
        String booksResponseBody = storeStepsResponse.then().extract().body().asString();
        BooksModel books = gson.fromJson(booksResponseBody, BooksModel.class);

        assertThat(books.getBooks().length, equalTo(numberOfBooks));
    }

    @Then("Correct data for all books are received")
    public void correct_books_data_received_verification() throws IOException {
        String allBooksResponseBody = storeStepsResponse.then().extract().body().asString();
        String testBooksModelsAsString = FileService.readFile("src/test/resources/testData/books.json");

        BooksModel responseBooks = gson.fromJson(allBooksResponseBody, BooksModel.class);
        BooksModel testDataBooks = gson.fromJson(testBooksModelsAsString, BooksModel.class);

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
