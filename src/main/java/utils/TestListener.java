package utils;

import core.BrowserService;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListener implements TestExecutionListener {
    private final WebDriver driver;
    private final Path path;

    public TestListener() {
        this.driver = BrowserService.getInstance().getDriver();
        this.path = Paths.get("target/screenshots");
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
            System.out.println("Status: " + testExecutionResult.getStatus());
            if (!testExecutionResult.getStatus().equals(TestExecutionResult.Status.SUCCESSFUL)) {
                captureScreenshot(testIdentifier);
            }
        }
    }




    public void captureScreenshot(TestIdentifier testIdentifier) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            try (FileOutputStream out = new FileOutputStream(path + File.separator + "testFail-" + testIdentifier.getDisplayName() + ".png")) {
                out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (IOException | WebDriverException e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
    }
}
