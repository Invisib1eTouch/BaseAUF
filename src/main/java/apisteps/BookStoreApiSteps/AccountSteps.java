package apisteps.BookStoreApiSteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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

    private static String token;
    private static String userID;

    @When("User sign up with username:{string} and password:{string} credentials")
    public void user_registration(String username, String password) {
        if (username.equals("randomValid")) {
            username = DataGenerator.getRandomAlphabeticValue(10);
        }
        if (password.equals("randomValid")) {
            password = DataGenerator.getRandomPassword();
        }

        LoginViewModel loginModel = new LoginViewModel.Builder()
                .addUsername(username)
                .addPassword(password)
                .build();

        response = given()
                .contentType(JSON)
                .body(gson.toJson(loginModel))
                .when()
                .post(ENDPOINT);

        token = generateToken(username, password);

        userID = response
                .then()
                .extract().jsonPath().get("userID");
    }

    @Then("User successfully registered")
    public void successful_registration_verification() {
        String givenUserID = given()
                .header("Authorization", "Bearer " + token)
                .contentType(JSON)
                .when()
                .get(ENDPOINT + "/" + userID)
                .then()
                .extract().jsonPath().get("userId");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(userID, equalTo(givenUserID));
    }

    @Then("The error message {string} with error code {string} is received")
    public void registration_error_message_and_error_code_verification(String errorMessage, String errorCode) {
        JsonPath resultJsonPath = response.then().extract().jsonPath();

        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(resultJsonPath.get("code"), equalTo(errorCode));
        assertThat(resultJsonPath.get("message"), equalTo(errorMessage));
    }

    @Then("User is not registered")
    public void registration_with_incorrect_credentials_verification() {
        assertThat(token, equalTo(null));
    }

    @Then("Book\\(s) is successfully added user's collection")
    public void correct_book_added_to_collection_verification() {
        List<Response> userData = AuthorizationSteps.userData;

        for (int i = 0; i < userData.size(); i++) {
            Response userDetails = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i))
                    .contentType(JSON)
                    .when()
                    .get(ENDPOINT + "/" + userData.get(i).then().extract().jsonPath().get("userID"));

            UserDetailsModel userDetailsModel = gson.fromJson(userDetails.then().extract().body().asString(), UserDetailsModel.class);

            assertThat(userDetailsModel.getBooks()[0], equalTo(StoreSteps.bookModels.get(i)));
        }
    }

    @Then("Book is successfully removed from collection")
    public void book_is_removed_from_collection() {
        List<Response> userData = AuthorizationSteps.userData;

        for (int i = 0; i < userData.size(); i++) {
            Response userDetails = given()
                    .header("Authorization", "Bearer " + AuthorizationSteps.tokens.get(i))
                    .contentType(JSON)
                    .when()
                    .get(ENDPOINT + "/" + userData.get(i).then().extract().jsonPath().get("userID"));

            UserDetailsModel userDetailsBeforeBookRemove = gson.fromJson(userData.get(i).then().extract().body().asString(), UserDetailsModel.class);

            UserDetailsModel userDetailsAfterBookRemove = gson.fromJson(userDetails.then().extract().body().asString(), UserDetailsModel.class);

            assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_NO_CONTENT));
            assertThat(userDetailsAfterBookRemove.getBooks(), equalTo(userDetailsBeforeBookRemove.getBooks()));
        }
    }
}
