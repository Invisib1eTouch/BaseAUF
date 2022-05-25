package apisteps.BookStoreApiSteps;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.LoginViewModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public abstract class BaseApiSteps {
    protected Gson gson;

    protected static Response response;
    protected static final String BASE_URL = "https://demoqa.com";

    public BaseApiSteps() {
        this.gson = new Gson();
        RestAssured.baseURI = BASE_URL;
    }

    protected String generateToken(String username, String password) {
        LoginViewModel loginModel = new LoginViewModel.Builder()
                .addUsername(username)
                .addPassword(password)
                .build();

        return given()
                .contentType(JSON)
                .body(gson.toJson(loginModel))
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .extract().jsonPath().get("token");
    }
}
