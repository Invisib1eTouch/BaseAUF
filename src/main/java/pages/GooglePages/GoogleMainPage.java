package pages.GooglePages;

import baseEntities.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Waits;

import java.util.List;

public class GoogleMainPage extends BasePage {
    private static final By searchInputBy = By.className("gLFyf");
    private static final By searchResultsBy = By.cssSelector("#search .g");
    private static final By searchBtnBy = By.cssSelector(".FPdoLc .gNO89b");

    public GoogleMainPage(WebDriver driver) {
        super(driver, "");
    }

    @Override
    protected By getPageOpenedIndicatorBy() {
        return searchInputBy;
    }

    public WebElement getSearchInput() {
        return driver.findElement(searchInputBy);
    }

    public List<WebElement> getSearchResults() {
        return new Waits(driver).waitForVisibilityOfAllElements(searchResultsBy);
    }

    public WebElement getSearchBtn() {
        return driver.findElement(searchBtnBy);
    }
}
