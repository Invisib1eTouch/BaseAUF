package wrappers;

import org.openqa.selenium.*;

import java.text.SimpleDateFormat;
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
        datePicker.findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        datePicker.findElement(By.tagName("input")).sendKeys(getFormattedDate(date, "MMMMM d, yyyy h:mm a"));
    }

    public void setDateBySelectParameters(Calendar date) {
        expandDatePicker();
        selectYearFromDropdown(date);
        selectMonthFromDropdown(date);
        selectDayFromNumbersArea(date);
        selectTime(date);
    }

    private void selectYearFromDropdown(Calendar date) {
        expandDatePickerIfCollapsed();
        String currentYear = datePicker.findElement(By.className("react-datepicker__year-read-view--selected-year")).getText();

        if (!currentYear.equals(String.valueOf(date.get(Calendar.YEAR)))) {
            int differance = date.get(Calendar.YEAR) - Integer.parseInt(currentYear);
            datePicker.findElement(By.className("react-datepicker__year-read-view--down-arrow")).click();
            List<WebElement> years = datePicker.findElements(By.className("react-datepicker__year-option"));

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
                    scrollIntoView(datePicker.findElement(By.className("react-datepicker__navigation--years-previous")));
                    navigationYearBtn.click();
                    clicks--;
                }
                List<WebElement> updatedYears = datePicker.findElements(By.className("react-datepicker__year-option"));
                updatedYears.get(yearIndexToSelect).click();
            }
        }
    }

    private void selectMonthFromDropdown(Calendar date) {
        expandDatePickerIfCollapsed();
        if (!datePicker.findElement(By.className("react-datepicker__month-read-view--selected-month")).getText().equals(date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))) {
            datePicker.findElement(By.className("react-datepicker__month-read-view--selected-month")).click();
            List<WebElement> monthList = datePicker.findElements(By.className("react-datepicker__month-option"));
            for (WebElement month : monthList) {
                if (month.getText().equals(date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))) {
                    scrollIntoView(month);
                    month.click();
                    break;
                }
            }
        }
    }

    private void selectDayFromNumbersArea(Calendar date) {
        expandDatePickerIfCollapsed();
        datePicker.findElement(By.xpath(String.format("//div[contains(@aria-label, '%s')]", getFormattedDate(date, "MMMMM d")))).click();
    }

    private void selectTime(Calendar date) {
        if (Integer.parseInt(getFormattedDate(date, "m")) % 15 == 0) {
            datePicker.findElement(By.xpath(String.format("//li[text() = '%s']", getFormattedDate(date, "hh:mm")))).click();
        } else {
            datePicker.findElement(By.xpath("//li[text() = '00:00']")).click();
        }
    }

    private void expandDatePicker() {
        datePicker.findElement(By.tagName("input")).click();
    }

    private void scrollIntoView(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
    }

    private void expandDatePickerIfCollapsed() {
        if (datePicker.findElements(By.className("react-datepicker-popper")).size() < 1) {
            expandDatePicker();
        }
    }

    private String getFormattedDate(Calendar date, String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH).format(date.getTime());
    }
}
