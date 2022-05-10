package pages.GooglePages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

import java.util.List;

@DefaultUrl("https://google.by")
public class GoogleMainPage extends PageObject {
    private static final By searchInputBy = By.className("gLFyf");
    private static final By searchResultsBy = By.cssSelector("#search .g");
    private static final By searchBtnBy = By.cssSelector(".CqAVzb .gNO89b");

    public WebElementFacade getSearchInput() {
        return find(searchInputBy);
    }

    public List<WebElementFacade> getSearchResults() {
        return waitForRenderedElements(searchResultsBy).$$(searchResultsBy);
    }

    public WebElementFacade getSearchBtn() {
        return find(searchBtnBy);
    }
}
