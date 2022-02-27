package utils;

import core.BrowserService;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestExecutionListener1 extends TestWatcher {
    private WebDriver driver;
    private String path;


    public TestExecutionListener1(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    private File screenshotFile;

    @Override
    protected void failed(Throwable e, Description description) {
//        saveScreenshotAs(generateFileNameBasedOnDescription(description));
//        takeScreenshotToSaveWhenFailed();
        captureScreenshot(driver, description.getDisplayName());
    }

    @Override
    protected void succeeded(Description description) {
        screenshotFile.delete();
    }

    public void takeScreenshotToSaveWhenFailed() {
        screenshotFile = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
    }

    private void saveScreenshotAs(String fileName) {
        if (screenshotFile == null) {
            return;
        }
        String targetFile = path + File.separator + "screenshot-" + fileName;
        if (screenshotFile.renameTo(new File(targetFile))) {

        } else {

        }
    }

    private String generateFileNameBasedOnDescription(Description description) {
//        String className = getClassName(description);
//        String methodName = getMethodName(description);
        return  "1.png";
    }

    public void captureScreenshot(WebDriver driver, String fileName) {
        try {
            new File(path).mkdirs();
            try (FileOutputStream out = new FileOutputStream(path + File.separator + "captureScreenshot()-" + fileName + ".png")) {
                out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (IOException | WebDriverException e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
    }
}
