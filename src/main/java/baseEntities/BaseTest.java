package baseEntities;

import core.BrowserService;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherFactory;
import org.openqa.selenium.WebDriver;
import utils.*;

@ExtendWith(TestExecutionListener5.class)
public abstract class BaseTest {
    protected WebDriver driver;

    @Rule
    TestExecutionListener1 watcher = new TestExecutionListener1(BrowserService.getInstance().getDriver(), "target/surefire-reports");

    @Rule
    TestExecutionListener4 testExecutionListener4 = new TestExecutionListener4(BrowserService.getInstance().getDriver(), "target/surefire-reports");

//    @RegisterExtension
//    TestExecutionListener2 testExecutionListener2 = new TestExecutionListener2(BrowserService.getInstance().getDriver(), "target/surefire-reports");





    @BeforeEach
    public void setupMethod() {
        driver = BrowserService.getInstance().getDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDownMethod() {
        driver.quit();
        BrowserService.quitInstance();
    }
}
