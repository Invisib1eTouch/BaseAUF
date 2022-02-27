package utils;

import core.BrowserService;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestExecutionListener4 implements TestRule {
    private WebDriver driver;
    private String path;


    public TestExecutionListener4(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return new ExpansiveExternalResourceStatement(statement);
    }

//    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
//        return new Statement() {
//            @Override
//            public void evaluate() throws Throwable {
//                try {
//                    statement.evaluate();
//
//                } catch (Throwable t) {
//                    captureScreenshot(frameworkMethod.getName());
//                    captureScreenshot1(driver, frameworkMethod.getName());
//                    throw t; // rethrow to allow the failure to be reported to JUnit
//                } finally {
//                    tearDown();
//                }
//            }
//
//            public void tearDown() {
//                //logout to the system;
//            }
//
//
//            public void captureScreenshot(String fileName) {
//                try {
//                    new File("target/surefire-reports/screenshot").mkdirs(); // Insure directory is there
//                    FileOutputStream out = new FileOutputStream("target/surefire-reports/screenshot/screenshot-" + fileName + ".png");
//                    Object driver;
//                    out.write(((TakesScreenshot) BrowserService.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES));
//                    out.close();
//                } catch (Exception e) {
//                    // No need to crash the tests if the screenshot fails
//                }
//            }
//
//            public void captureScreenshot1(WebDriver driver, String fileName) {
//                try {
//                    new File(path).mkdirs();
//                    try (FileOutputStream out = new FileOutputStream(path + File.separator + "TE-4-captureScreenshot()-" + fileName + ".png")) {
//                        out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
//                    }
//                } catch (IOException | WebDriverException e) {
//                    System.out.println("screenshot failed:" + e.getMessage());
//                }
//            }
//
//        };
//    }

    public class ExpansiveExternalResourceStatement extends Statement {

        private Statement baseStatement;

        public ExpansiveExternalResourceStatement(Statement b) {
            baseStatement = b;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                baseStatement.evaluate();
            } catch (Error e) {
                captureScreenshot1(driver, "scr1111");
                System.out.println("I take a Screenshot");
                throw e;
            } finally {
                after();
            }
        }

        //Put your after code in this method!
        public void after() {
            System.out.println("I am after");
        }

        public void captureScreenshot1(WebDriver driver, String fileName) {
                try {
                    new File(path).mkdirs();
                    try (FileOutputStream out = new FileOutputStream(path + File.separator + "TE-4-captureScreenshot()-" + fileName + ".png")) {
                        out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                    }
                } catch (IOException | WebDriverException e) {
                    System.out.println("screenshot failed:" + e.getMessage());
                }
            }

    }


}
