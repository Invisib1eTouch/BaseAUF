package baseEntities;

import core.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Waits;

public abstract class BasePage {
    protected WebDriver driver;
    protected final static String BASE_URL = PropertyReader.getUrl();
    protected final String path;

    public BasePage(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    public void open() {
        if (this.path != null) {
            this.driver.get(BASE_URL + path);
        }
        verifyPageOpened();
    }

    public void verifyPageOpened() {
        Waits.getInstance(driver).waitForVisibility(getPageOpenedIndicatorBy());
    }

    protected abstract By getPageOpenedIndicatorBy();
}
