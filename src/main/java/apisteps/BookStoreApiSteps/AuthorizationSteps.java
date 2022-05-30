package apisteps.BookStoreApiSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.LoginViewModel;
import org.apache.http.HttpStatus;
import utils.DataGenerator;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AuthorizationSteps extends BaseApiSteps {

    private static final String ENDPOINT = "/Account/v1/Authorized";

    private List<LoginViewModel> userCredentialsModels;
    private final List<Response> responseBodiesToVerify = new ArrayList<>();

    @Given("Set of valid user credentials equals {int}")
    public void generate_valid_user_credentials(int numberOfUserCredentialsToGenerate) {
        userCredentialsModels = DataGenerator.generateUserCredentials(numberOfUserCredentialsToGenerate);
    }

    @Given("Registered users that have active valid token")
    public void registered_new_authorized_users() {
        for (LoginViewModel model : userCredentialsModels) {
            createNewUser(model);
            generateToken(model);
        }
    }

    @Given("Registered users that have no active valid token")
    public void registered_new_unauthorized_users() {
        for (LoginViewModel model : userCredentialsModels) {
            createNewUser(model);
        }
    }

    @When("User try to authorize")
    public void user_authorization() {
        for (LoginViewModel model : userCredentialsModels) {
            responseBodiesToVerify.add(given()
                    .contentType(JSON)
                    .body(gson.toJson(model))
                    .when()
                    .post(ENDPOINT));
        }
    }

    @Then("Authorization response is {string}")
    public void authorization_response_verification(String response) {
        for (Response responseBodyToVerify : responseBodiesToVerify) {
            assertThat(responseBodyToVerify.then().extract().body().asString(), is(response));
        }
    }

    @Then("The error message {string} and error code {string} is received")
    public void authentication_error_message_and_error_code_verification(String errorMessage, String errorCode) {
        for (Response response : responseBodiesToVerify) {
            JsonPath resultJsonPath = response.then().extract().jsonPath();

            assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
            assertThat(resultJsonPath.get("code"), equalTo(errorCode));
            assertThat(resultJsonPath.get("message"), equalTo(errorMessage));
        }

    }
}
