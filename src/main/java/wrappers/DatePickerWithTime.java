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

    private static final By datePickerInputBy = By.tagName("input");
    private static final By yearDropdownBy = By.className("react-datepicker__year-read-view--selected-year");
    private static final By monthDropdownBy = By.className("react-datepicker__month-read-view--selected-month");
    private static final By currentTimeBy = By.className("react-datepicker__time-list-item--selected");
    private static final By yearListFromDropdownBy = By.className("react-datepicker__year-option");

    public DatePickerWithTime(WebDriver driver, By by) {
        this.driver = driver;
        this.datePicker = driver.findElement(by);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void setDateByInputValue(Calendar date) {
        datePicker.findElement(datePickerInputBy).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        datePicker.findElement(datePickerInputBy).sendKeys(getFormattedDate(date, "MMMMM d, yyyy h:mm a"));
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
                Integer.parseInt(datePicker.findElement(yearDropdownBy).getText()),
                getCalendarMonth(datePicker.findElement(monthDropdownBy).getText()),
                Integer.parseInt(datePicker.findElement(By.className("react-datepicker__day--selected")).getText()),
                Integer.parseInt(datePicker.findElement(currentTimeBy).getText().split(":")[0]),
                Integer.parseInt(datePicker.findElement(currentTimeBy).getText().split(":")[1])
        );
    }

    private void selectYearFromDropdown(Calendar date) {
        expandDatePickerIfCollapsed();
        String currentYear = datePicker.findElement(yearDropdownBy).getText();
        String yearToSelect = String.valueOf(date.get(Calendar.YEAR));

        if (!currentYear.equals(yearToSelect)) {
            int differance = Integer.parseInt(yearToSelect) - Integer.parseInt(currentYear);
            datePicker.findElement(By.className("react-datepicker__year-read-view--down-arrow")).click();
            List<WebElement> years = datePicker.findElements(yearListFromDropdownBy);

            if (Math.abs(differance) <= 5) {
                selectYearIfPresent(yearToSelect, years);
            } else {
                selectYearIfNotPresent(differance);
            }
        }
    }

    private void selectYearIfNotPresent(int differance) {
        int yearIndexToSelect = proceedToYearsRangeToSelect(differance);
        List<WebElement> years = datePicker.findElements(yearListFromDropdownBy);
        years.get(yearIndexToSelect).click();
    }

    private int proceedToYearsRangeToSelect(int diffBetweenResultAndCurrentYears) {
        int clicks;
        int yearIndexToSelect;
        WebElement navigationYearBtn;

        final By nextNavigationBtnBy = By.className("react-datepicker__navigation--years-upcoming");
        final By previousNavigationBtnBy = By.className("react-datepicker__navigation--years-previous");

        if (diffBetweenResultAndCurrentYears < 0) {
            clicks = Math.abs(diffBetweenResultAndCurrentYears + 5);
            navigationYearBtn = datePicker.findElement(previousNavigationBtnBy);
            yearIndexToSelect = 11;
        } else {
            clicks = diffBetweenResultAndCurrentYears - 5;
            navigationYearBtn = datePicker.findElement(nextNavigationBtnBy);
            yearIndexToSelect = 1;
        }

        while (clicks != 0) {
            scrollIntoView(datePicker.findElement(previousNavigationBtnBy));
            navigationYearBtn.click();
            clicks--;
        }
        return yearIndexToSelect;
    }

    private void selectYearIfPresent(String yearToSelect, List<WebElement> years) {
        for (int i = 1; i < years.size() - 1; i++) {
            if (years.get(i).getText().equals(yearToSelect)) {
                years.get(i).click();
                break;
            }
        }
    }

    private void selectMonthFromDropdown(Calendar date) {
        expandDatePickerIfCollapsed();
        WebElement datePickerMonth = datePicker.findElement(monthDropdownBy);
        String monthToSelect = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        if (!datePickerMonth.getText().equals(monthToSelect)) {
            datePickerMonth.click();
            List<WebElement> monthList = datePicker.findElements(By.className("react-datepicker__month-option"));
            for (WebElement month : monthList) {
                if (month.getText().equals(monthToSelect)) {
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
        datePicker.findElement(datePickerInputBy).click();
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

    public static Integer getCalendarMonth(String monthName) {
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
