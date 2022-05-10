package steps.GooglePageSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.GooglePages.GoogleMainPage;

public class GoogleMainPageSteps {
    GoogleMainPage googleMainPage;

    @Given("User proceeds to Google Main page")
    public void proceed_to_google_main_page() {
        googleMainPage.open();
    }

    @When("User searches the {string}")
    public void user_searches_request(String request) {
        googleMainPage.getSearchInput().sendKeys(request);
        googleMainPage.getSearchBtn().click();
    }

    @Then("The list of {int} results is displayed")
    public void verify_results_number(int numberOfResults) {
        Assertions.assertEquals(numberOfResults, googleMainPage.getSearchResults().size());
    }
}
