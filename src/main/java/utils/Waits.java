package utils;

import core.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    private static WebDriverWait wait;

    public Waits(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(PropertyReader.getTimeOut()));
    }

    public static WebElement waitForVisibility(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
