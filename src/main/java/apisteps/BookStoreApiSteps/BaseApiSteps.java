package apisteps.BookStoreApiSteps;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.LoginViewModel;
import models.jwtToken.TokenViewModel;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.rest.SerenityRest.given;

public abstract class BaseApiSteps {
    protected static Gson gson = new Gson();

    protected static Response response;

    public BaseApiSteps() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    protected String generateToken(String username, String password) {
        LoginViewModel loginModel = new LoginViewModel();
        loginModel.setUserName(username);
        loginModel.setPassword(password);

        return given()
                .contentType(JSON)
                .body(gson.toJson(loginModel))
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .extract().jsonPath().get("token");
    }

    protected TokenViewModel generateToken(LoginViewModel loginViewModel) {
        response = given()
                .contentType(JSON)
                .body(gson.toJson(loginViewModel))
                .when()
                .post("/Account/v1/GenerateToken");

        return gson.fromJson(response.then().extract().body().asString(), TokenViewModel.class);
    }

    protected Response createNewUser(LoginViewModel loginModel) {
        return given()
                .contentType(JSON)
                .body(gson.toJson(loginModel))
                .when()
                .post("/Account/v1/User");
    }
}
