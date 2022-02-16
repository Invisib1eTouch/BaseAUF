package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Locale;

public class BrowserService {
    private WebDriver driver;
    private static BrowserService instance;

    private BrowserService() {
        DriverManagerType driverManagerType;
        switch (PropertyReader.getBrowserName().toLowerCase(Locale.ROOT)) {
            case "chrome":
                driverManagerType = DriverManagerType.CHROME;
                WebDriverManager.getInstance(driverManagerType).setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                driverManagerType = DriverManagerType.FIREFOX;
                WebDriverManager.getInstance(driverManagerType).setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                driverManagerType = DriverManagerType.EDGE;
                WebDriverManager.getInstance(driverManagerType).setup();
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Browser " + PropertyReader.getBrowserName() + " is not supported");
                break;
        }
    }

    public static BrowserService getInstance() {
        if (instance == null) {
            instance = new BrowserService();
        }
        return instance;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public static void quitInstance() {
        instance = null;
    }
}
