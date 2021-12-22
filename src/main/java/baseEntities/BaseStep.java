package baseEntities;

import org.openqa.selenium.WebDriver;

public abstract class BaseStep {
    protected WebDriver driver;

    public BaseStep(WebDriver driver) {
        this.driver = driver;
    }
}
