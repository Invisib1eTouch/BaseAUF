package utils;

import core.BrowserService;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestExecutionListener implements AfterTestExecutionCallback {
    private final Path path;


    public TestExecutionListener() {
        this.path = Paths.get("target/screenshots");
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        if (extensionContext.getExecutionException().isPresent()) {
            captureScreenshot(extensionContext);
        }
    }

    public void captureScreenshot(ExtensionContext extensionContext) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            try (FileOutputStream out = new FileOutputStream(path + File.separator + "testFail-" + extensionContext.getDisplayName() + ".png")) {
                out.write(((TakesScreenshot) BrowserService.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES));
            }
        } catch (IOException | WebDriverException e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
    }
}
