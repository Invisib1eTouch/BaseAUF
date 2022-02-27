package tests;

import baseEntities.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.GooglePages.GoogleMainPage;
import steps.GooglePageSteps.GoogleMainPageSteps;
import utils.TestExecutionListener2;
import utils.TestExecutionListener5;

public class SearchTests extends BaseTest {


    @Test
    public void positiveSearchTest() {
        GoogleMainPage googleMainPage = new GoogleMainPage(driver);
        googleMainPage.open();

        GoogleMainPageSteps googleMainPageSteps = new GoogleMainPageSteps(driver);
        googleMainPageSteps.search("Test");

        Assertions.assertEquals(10, googleMainPageSteps.resultsNumber());
    }
}
