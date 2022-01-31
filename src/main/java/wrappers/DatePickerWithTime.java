package wrappers;

import core.PropertyReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.awt.windows.ThemeReader;
import utils.Waits;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatePickerWithTime {
    private final WebDriver driver;
    private WebElement datePicker;
    private JavascriptExecutor jsExecutor;

    public DatePickerWithTime(WebDriver driver, By by) {
        this.driver = driver;
        this.datePicker = driver.findElement(by);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void setDateByInputValue(Calendar date) {
        DateFormat dateFormat = new SimpleDateFormat("MMMMM d, yyyy h:mm a", Locale.ENGLISH);
        String resultDate = dateFormat.format(date.getTime());
        datePicker.findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        datePicker.findElement(By.tagName("input")).sendKeys(resultDate);
    }

    public void setDateBySelectParameters(Calendar date) {
        expandDatePicker();
        datePicker.findElement(By.className("react-datepicker__month-read-view--selected-month")).click();
        selectYearFromDropdown(date);
    }

    private void selectMonthFromDropdown() {
////        Month
//        List<WebElement> monthList = datePicker.findElements(By.className("react-datepicker__month-option"));
//        for (WebElement month : monthList) {
//            System.out.println(month.getText());
//        }

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void selectYearFromDropdown(Calendar date) {
        if (datePicker.findElements(By.className("react-datepicker-popper")).size() < 1) {
            expandDatePicker();
        }
        String currentYear = datePicker.findElement(By.className("react-datepicker__year-read-view--selected-year")).getText();
        datePicker.findElement(By.className("react-datepicker__year-read-view--down-arrow")).click();
        List<WebElement> years = datePicker.findElements(By.className("react-datepicker__year-option"));

        if (!currentYear.equals(String.valueOf(date.get(Calendar.YEAR)))) {
            int differance = date.get(Calendar.YEAR) - Integer.parseInt(currentYear);

            if (Math.abs(differance) <= 5) {
                for (int i = 1; i < years.size() - 1; i++) {
                    if (years.get(i).getText().equals(String.valueOf(date.get(Calendar.YEAR)))) {
                        years.get(i).click();
                        break;
                    }
                }
            } else {
                int clicks = 0;
                int yearIndexToSelect = 0;
                WebElement navigationYearBtn;

                if (differance < 0) {
                    clicks = Math.abs(differance + 5);
                    navigationYearBtn = datePicker.findElement(By.className("react-datepicker__navigation--years-previous"));
                    yearIndexToSelect = years.size() - 2;
                } else {
                    clicks = differance - 5;
                    navigationYearBtn = datePicker.findElement(By.className("react-datepicker__navigation--years-upcoming"));
                    yearIndexToSelect = 1;
                }

                while (clicks != 0) {
                    jsExecutor.executeScript("arguments[0].scrollIntoView();", datePicker.findElement(By.className("react-datepicker__navigation--years-previous")));
                    navigationYearBtn.click();
                    clicks--;
                }
                List<WebElement> updatedYears = datePicker.findElements(By.className("react-datepicker__year-option"));
                updatedYears.get(yearIndexToSelect).click();
            }
        }
    }

    private void expandDatePicker() {
        datePicker.findElement(By.tagName("input")).click();
    }
}
