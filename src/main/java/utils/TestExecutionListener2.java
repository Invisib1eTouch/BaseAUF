package utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class TestExecutionListener2 implements TestWatcher {
    private WebDriver driver;
    private String path;

    public TestExecutionListener2(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
//        TestWatcher.super.testFailed(context, cause);
        captureScreenshot(driver, context.getDisplayName());
        takeScreenShot(driver, context.getDisplayName());
        tkscr(driver, context.getDisplayName());
        getScreenShot(driver, context.getDisplayName());
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

    public void takeScreenShot(WebDriver driver, String fileName) {
        try {
            new File(path).mkdirs();
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(path + File.separator + "takeScreenShot()-" + fileName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException("Could not make a screenshot");
        }
    }

    public void tkscr(WebDriver driver, String fileName) {
        final File screenShotOutputFile = new File("tkscr()" + ".png").getAbsoluteFile();
        System.out.println(screenShotOutputFile.getAbsoluteFile().toString());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, screenShotOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getScreenShot(WebDriver driver, String fileName) {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(path + File.separator + "getScreenShot()" + fileName + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] info = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        try {
            file.write(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
