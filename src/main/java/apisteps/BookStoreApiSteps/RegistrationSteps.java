package apisteps.BookStoreApiSteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.LoginViewModel;
import org.apache.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
public class RegistrationSteps extends BaseApiSteps {

    private static final String ENDPOINT = "/Account/v1/User";

    private static String token;
    private static String userId;

    @When("User sign up with valid username:{string} and password:{string} credentials")
    public void user_registration(String username, String password) {

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

        userId = response
                .then()
                .extract().jsonPath().get("userId");
    }

    @Then("User successfully registered")
    public void successful_registration() {
        String givenUserID = given()
                .header("Authorization", "Bearer " + token)
                .contentType(JSON)
                .when()
                .get(ENDPOINT + "/" + userId)
                .then()
                .extract().jsonPath().get("userId");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(userId, equalTo(givenUserID));
    }
}
