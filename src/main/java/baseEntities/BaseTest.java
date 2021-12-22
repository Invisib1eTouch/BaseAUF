package baseEntities;

import core.BrowserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setupClass() {
        driver = BrowserService.getInstance().getDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDownMethod() {
        driver.quit();
    }
}
