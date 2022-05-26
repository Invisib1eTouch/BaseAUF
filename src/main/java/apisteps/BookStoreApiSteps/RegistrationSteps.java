package apisteps.BookStoreApiSteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import models.LoginViewModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import utils.Randomizer;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
public class RegistrationSteps extends BaseApiSteps {

    private static final String ENDPOINT = "/Account/v1/User";

    private static String token;
    private static String userId;

    @When("User sign up with username:{string} and password:{string} credentials")
    public void user_registration(String username, String password) {
        if (username.equals("randomValid")) {
            username = Randomizer.getRandomAlphabeticValue(10);
        }
        if (password.equals("randomValid")) {
            password = Randomizer.getRandomPassword();
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
        assertThat(response.then().extract().jsonPath().get("userId"), equalTo(givenUserID));
    }

    @Then("User is not registered")
    public void registration_with_incorrect_credentials() {
        JsonPath resultJsonPath = response.then().extract().jsonPath();
        String errorMessage = "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), " +
                "one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight " +
                "characters or longer.";

        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));
        assertThat(resultJsonPath.get("code"), equalTo("1300"));
        assertThat(resultJsonPath.get("message"), equalTo(errorMessage));
        assertThat(token, equalTo(null));
    }
}
