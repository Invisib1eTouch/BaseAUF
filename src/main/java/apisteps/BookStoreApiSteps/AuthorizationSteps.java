package apisteps.BookStoreApiSteps;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.LoginViewModel;
import models.jwtToken.DecodedTokenHeader;
import models.jwtToken.DecodedTokenPayload;
import models.jwtToken.TokenViewModel;
import org.apache.http.HttpStatus;
import utils.DataGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AuthorizationSteps extends BaseApiSteps {

    private static final String ENDPOINT = "/Account/v1/Authorized";

    public static List<LoginViewModel> userCredentialsModels = new ArrayList<>();
    private final List<Response> responseBodiesToVerify = new ArrayList<>();

    public static final List<TokenViewModel> tokens = new ArrayList<>();

    public static final List<Response> userData = new ArrayList<>();

    @After
    public void cleanup() {
        tokens.clear();
        userData.clear();
        userCredentialsModels.clear();
    }

    @When("{int} new users are registered")
    public void registered_new_unauthorized_users(int numberOfUserCredentialsToGenerate) {
        userCredentialsModels = DataGenerator.generateUserCredentials(numberOfUserCredentialsToGenerate);

        for (LoginViewModel model : userCredentialsModels) {
            userData.add(createNewUser(model));
        }
    }

    @When("Token is generated for users")
    public void generate_token() {
        for (LoginViewModel model : userCredentialsModels) {
            tokens.add(generateToken(model));
        }
    }

    @When("Users try to authorize")
    public void user_authorization() {
        for (LoginViewModel model : userCredentialsModels) {
            responseBodiesToVerify.add(given()
                    .contentType(JSON)
                    .body(gson.toJson(model))
                    .when()
                    .post(ENDPOINT));
        }
    }

    @When("{int} set\\(s) of valid user credentials are generated")
    public void generate_valid_user_credentials(int numberOfUserCredentialsToGenerate) {
        userCredentialsModels = DataGenerator.generateUserCredentials(numberOfUserCredentialsToGenerate);
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

    @Then("Token is valid")
    public void token_is_valid_verification() {
        for (int i = 0; i < tokens.size(); i++) {
            String[] token_parts = tokens.get(i).getToken().split("\\.");

            String header = new String(Base64.getDecoder().decode(token_parts[0]));

            DecodedTokenHeader actualTokenHeader = gson.fromJson(header, DecodedTokenHeader.class);

            DecodedTokenHeader expectedTokedHeader = new DecodedTokenHeader.Builder()
                    .addAlg("HS256")
                    .addTyp("JWT")
                    .build();

            String payload = new String(Base64.getDecoder().decode(token_parts[1]));

            DecodedTokenPayload actualTokenPayload = gson.fromJson(payload, DecodedTokenPayload.class);

            String expirationTokenTimeInMilli = String.valueOf(Instant.parse(tokens.get(i).getExpires()).toEpochMilli());
            String expirationTokenTimeInSec = expirationTokenTimeInMilli.substring(0, expirationTokenTimeInMilli.length() - 3);
            Long activationTokenTimeInSec = Long.parseLong(expirationTokenTimeInSec) - 7 * 24 * 60 * 60;

            DecodedTokenPayload expectedTokenPayload = new DecodedTokenPayload.Builder()
                    .addUserName(userCredentialsModels.get(i).getUserName())
                    .addPassword(userCredentialsModels.get(i).getPassword())
                    .addIat(activationTokenTimeInSec)
                    .build();

            assertThat(token_parts.length, equalTo(3));
            assertThat(actualTokenHeader, equalTo(expectedTokedHeader));
            assertThat(actualTokenPayload, equalTo(expectedTokenPayload));
        }
    }
}
