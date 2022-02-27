package utils;

import core.BrowserService;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestExecutionListener5 implements AfterTestExecutionCallback {
    private WebDriver driver;
    private final String path = "target/surefire-reports";


    public TestExecutionListener5() {
        this.driver = BrowserService.getInstance().getDriver();
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        System.out.println("MESSAGE");
        Boolean testResult = extensionContext.getExecutionException().isPresent();
        System.out.println(testResult); //false - SUCCESS, true - FAILED

        if (testResult) {
            captureScreenshot1(driver, path);
        }
    }

    public void captureScreenshot1(WebDriver driver, String fileName) {
        try {
//            new File("target/surefire-reports").mkdirs();
            try (FileOutputStream out = new FileOutputStream("E:/Java/BaseAUF/target/surefire-reports" + File.separator + "TE-4-captureScreenshot()-.png")) {
                out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (IOException | WebDriverException e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
    }
}
