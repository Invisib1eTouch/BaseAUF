import baseEntities.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.GoogleMainPage;
import steps.GoogleMainPageSteps;

public class SearchTests extends BaseTest {

    @Test
    public void positiveSearchTest() {
        GoogleMainPage googleMainPage = new GoogleMainPage(driver);
        googleMainPage.open();

        GoogleMainPageSteps googleMainPageSteps = new GoogleMainPageSteps(driver);
        googleMainPageSteps.search("New query");

        Assertions.assertEquals(10, googleMainPageSteps.resultsNumber());
    }
}
