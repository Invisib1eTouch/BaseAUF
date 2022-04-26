package baseEntities;

import core.BrowserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected static WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        driver = BrowserService.getInstance().getDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void tearDownClass() {
        driver.quit();
        BrowserService.quitInstance();
    }
}
