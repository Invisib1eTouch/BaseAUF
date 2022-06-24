package apisteps.BookStoreApiSteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.BookModel;
import models.LoginViewModel;
import models.UserDetailsModel;
import org.apache.http.HttpStatus;
import utils.DataGenerator;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountSteps extends BaseApiSteps {

    private static final String ENDPOINT = "/Account/v1/User";

    private String token;

    public static Response accountStepsResponse;

    @When("User sign up with valid random username and password credentials")
    public void user_registration_with_random_values() {
        String username = DataGenerator.getRandomAlphabeticValue(10);
        String password = DataGenerator.getRandomPassword();
        user_registration(username, password);
    }

    @When("User sign up with username:{string} and password:{string} credentials")
    public void user_registration(String username, String password) {
        LoginViewModel loginModel = new LoginViewModel();
        loginModel.setUserName(username);
        loginModel.setPassword(password);

        accountStepsResponse = given()
                .contentType(JSON)
                .body(gson.toJson(loginModel))
                .when()
                .post(ENDPOINT);

        this.token = generateToken(username, password);
    }

    @Then("User successfully registered")
    public void successful_registration_verification() {
        String userID = accountStepsResponse.then().extract().jsonPath().get("userID");

        String givenUserID = given()
                .header("Authorization", "Bearer " + this.token)
                .contentType(JSON)
                .when()
                .get(ENDPOINT + "/" + userID)
                .then()
                .extract().jsonPath().get("userId");

        assertThat(accountStepsResponse.getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(userID, equalTo(givenUserID));
    }

    @Then("The error message {string} with error code {string} is received")
    public void registration_error_message_and_error_code_verification(String errorMessage, String errorCode) {
        JsonPath resultJsonPath = accountStepsResponse.then().extract().jsonPath();

        assertThat(accountStepsResponse.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(resultJsonPath.get("code"), equalTo(errorCode));
        assertThat(resultJsonPath.get("message"), equalTo(errorMessage));
    }

    @Then("User is not registered")
    public void registration_with_incorrect_credentials_verification() {
        assertThat(this.token, equalTo(null));
    }

    @Then("Book\\(s) is successfully added user's collection")
    public void correct_book_added_to_collection_verification() {
        List<Response> userDetailsResponses = AuthorizationSteps.userData;

        for (int i = 0; i < userDetailsResponses.size(); i++) {
            String userID = userDetailsResponses.get(i).then().extract().jsonPath().get("userID");

            String userDetailsResponseBody = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                    .contentType(JSON)
                    .when()
                    .get(ENDPOINT + "/" + userID)
                    .then().extract().body().asString();

            UserDetailsModel userDetailsModel = gson.fromJson(userDetailsResponseBody, UserDetailsModel.class);

            assertThat(userDetailsModel.getBooks()[0], equalTo(StoreSteps.bookModels.get(i)));
        }
    }

    @Then("User has the same books in collection as before")
    public void book_is_removed_from_collection() {
        List<Response> userDetailsResponses = AuthorizationSteps.userData;

        for (int i = 0; i < userDetailsResponses.size(); i++) {
            String userID = userDetailsResponses.get(i).then().extract().jsonPath().get("userID");

            String beforeBookRemoveResponseBody = userDetailsResponses.get(i).then().extract().body().asString();
            UserDetailsModel userDetailsBeforeBookRemove = gson.fromJson(beforeBookRemoveResponseBody,
                    UserDetailsModel.class);

            String afterBookRemoveResponseBody = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                    .contentType(JSON)
                    .when()
                    .get(ENDPOINT + "/" + userID)
                    .then().extract().body().asString();

            UserDetailsModel userDetailsAfterBookRemove = gson.fromJson(afterBookRemoveResponseBody,
                    UserDetailsModel.class);

            assertThat(StoreSteps.storeStepsResponse.getStatusCode(), equalTo(HttpStatus.SC_NO_CONTENT));
            assertThat(userDetailsAfterBookRemove.getBooks(), equalTo(userDetailsBeforeBookRemove.getBooks()));
        }
    }

    @Then("Only one instance of book is added to user's collection")
    public void only_one_book_instance_in_user_collection_verification() {
        List<Response> userDetailsResponses = AuthorizationSteps.userData;

        for (int i = 0; i < userDetailsResponses.size(); i++) {
            int numberOfTheSameBooks = 0;
            String userID = userDetailsResponses.get(i).then().extract().jsonPath().get("userID");

            String userDetailsResponseBody = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                    .contentType(JSON)
                    .when()
                    .get(ENDPOINT + "/" + userID)
                    .then().extract().body().asString();

            UserDetailsModel userDetails = gson.fromJson(userDetailsResponseBody,
                    UserDetailsModel.class);
            BookModel[] userBooks = userDetails.getBooks();

            for (BookModel book : userBooks) {
                if (book.equals(StoreSteps.bookModels.get(0))) {
                    numberOfTheSameBooks++;
                }
            }
            assertThat(numberOfTheSameBooks, equalTo(1));
        }
    }

    @Then("Book remains for other user collections")
    public void book_remains_in_user_collection_except_first_user_verification() {
        List<Response> userDetailsResponses = AuthorizationSteps.userData;

        for (int i = 1; i < userDetailsResponses.size(); i++) {
            String userID = userDetailsResponses.get(i).then().extract().jsonPath().get("userID");

            String userDetailsResponseBody = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i).getToken())
                    .contentType(JSON)
                    .when()
                    .get(ENDPOINT + "/" + userID)
                    .then().extract().body().asString();

            UserDetailsModel userDetails = gson.fromJson(userDetailsResponseBody,
                    UserDetailsModel.class);
            BookModel[] userBooks = userDetails.getBooks();

            for (BookModel book : userBooks) {
                assertThat(book, equalTo(StoreSteps.bookModels.get(i)));
            }
        }
    }
}
