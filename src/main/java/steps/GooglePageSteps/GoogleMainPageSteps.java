package steps.GooglePageSteps;

import baseEntities.BaseStep;
import org.openqa.selenium.WebDriver;
import pages.GooglePages.GoogleMainPage;

public class GoogleMainPageSteps extends BaseStep {

    public GoogleMainPageSteps(WebDriver driver) {
        super(driver);
    }

    public GoogleMainPageSteps search(String query) {
        GoogleMainPage page = new GoogleMainPage(driver);
        page.getSearchInput().sendKeys(query);
        page.getSearchBtn().click();
        return this;
    }

    public int resultsNumber() {
        GoogleMainPage page = new GoogleMainPage(driver);
        return page.getSearchResults().size();
    }
}
