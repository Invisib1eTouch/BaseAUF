package wrappers;

import org.openqa.selenium.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    public Calendar getDateFromDatePicker() {
        expandDatePicker();
        return new GregorianCalendar(
                Integer.parseInt(datePicker.findElement(By.className("react-datepicker__year-read-view--selected-year")).getText()),
                getCalendarMonth(datePicker.findElement(By.className("react-datepicker__month-read-view--selected-month")).getText()),
                Integer.parseInt(datePicker.findElement(By.className("react-datepicker__day--selected")).getText()),
                Integer.parseInt(datePicker.findElement(By.className("react-datepicker__time-list-item--selected")).getText().split(":")[0]),
                Integer.parseInt(datePicker.findElement(By.className("react-datepicker__time-list-item--selected")).getText().split(":")[1])
        );
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
            datePicker.findElement(By.xpath(String.format("//li[text() = '%s']", getFormattedDate(date, "HH:mm")))).click();
        } else {
            throw new IllegalArgumentException("The minute value " + getFormattedDate(date, "mm") + " is incorrect. The DatePicker range of time is 15 minutes so correct minute values are 0, 15, 30, 45.");
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

    private Integer getCalendarMonth(String monthName) {
        switch (monthName.toLowerCase(Locale.ROOT)) {
            case ("january"):
                return Calendar.JANUARY;
            case ("february"):
                return Calendar.FEBRUARY;
            case ("march"):
                return Calendar.MARCH;
            case ("april"):
                return Calendar.APRIL;
            case ("may"):
                return Calendar.MAY;
            case ("june"):
                return Calendar.JUNE;
            case ("july"):
                return Calendar.JULY;
            case ("august"):
                return Calendar.AUGUST;
            case ("september"):
                return Calendar.SEPTEMBER;
            case ("october"):
                return Calendar.OCTOBER;
            case ("november"):
                return Calendar.NOVEMBER;
            case ("december"):
                return Calendar.DECEMBER;
            default:
                return null;
        }
    }
}
