package wrappers;

import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DatePickerWithTime extends PageObject {
    private JavascriptExecutor jsExecutor;

    private final By datePickerBy;

    private static final By datePickerInputBy = By.tagName("input");
    private static final By yearDropdownBy = By.className("react-datepicker__year-read-view--selected-year");
    private static final By monthDropdownBy = By.className("react-datepicker__month-read-view--selected-month");
    private static final By currentTimeBy = By.className("react-datepicker__time-list-item--selected");
    private static final By yearListFromDropdownBy = By.className("react-datepicker__year-option");

    public DatePickerWithTime(By by) {
        this.jsExecutor = (JavascriptExecutor) getDriver();
        this.datePickerBy = by;
    }

    private WebElementFacade findDatePickerElement(By datePickerElement) {
        return find(datePickerBy).then(datePickerElement);
    }

    private ListOfWebElementFacades findDatePickerElements(By datePickerElements) {
        return find(datePickerBy).thenFindAll(datePickerElements);
    }

    public void setDateByInputValue(Calendar date) {
        findDatePickerElement(datePickerInputBy).typeAndEnter(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        findDatePickerElement(datePickerInputBy).typeAndEnter(getFormattedDate(date, "MMMMM d, yyyy h:mm a"));
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
                Integer.parseInt(findDatePickerElement(yearDropdownBy).getText()),
                getCalendarMonth(findDatePickerElement(monthDropdownBy).getText()),
                Integer.parseInt(findDatePickerElement(By.className("react-datepicker__day--selected")).getText()),
                Integer.parseInt(findDatePickerElement(currentTimeBy).getText().split(":")[0]),
                Integer.parseInt(findDatePickerElement(currentTimeBy).getText().split(":")[1])
        );
    }

    private void selectYearFromDropdown(Calendar date) {
        expandDatePickerIfCollapsed();
        String currentYear = findDatePickerElement(yearDropdownBy).getText();
        String yearToSelect = String.valueOf(date.get(Calendar.YEAR));

        if (!currentYear.equals(yearToSelect)) {
            int differance = Integer.parseInt(yearToSelect) - Integer.parseInt(currentYear);
            findDatePickerElement(By.className("react-datepicker__year-read-view--down-arrow")).click();
            ListOfWebElementFacades years = findDatePickerElements(yearListFromDropdownBy);

            if (Math.abs(differance) <= 5) {
                selectYearIfPresent(yearToSelect, years);
            } else {
                selectYearIfNotPresent(differance);
            }
        }
    }

    private void selectYearIfNotPresent(int differance) {
        int yearIndexToSelect = proceedToYearsRangeToSelect(differance);
        ListOfWebElementFacades years = findDatePickerElements(yearListFromDropdownBy);
        years.get(yearIndexToSelect).click();
    }

    private int proceedToYearsRangeToSelect(int diffBetweenResultAndCurrentYears) {
        int clicks;
        int yearIndexToSelect;
        WebElementFacade navigationYearBtn;

        final By nextNavigationBtnBy = By.className("react-datepicker__navigation--years-upcoming");
        final By previousNavigationBtnBy = By.className("react-datepicker__navigation--years-previous");

        if (diffBetweenResultAndCurrentYears < 0) {
            clicks = Math.abs(diffBetweenResultAndCurrentYears + 5);
            navigationYearBtn = findDatePickerElement(previousNavigationBtnBy);
            yearIndexToSelect = 11;
        } else {
            clicks = diffBetweenResultAndCurrentYears - 5;
            navigationYearBtn = findDatePickerElement(nextNavigationBtnBy);
            yearIndexToSelect = 1;
        }

        while (clicks != 0) {
            scrollIntoView(findDatePickerElement(previousNavigationBtnBy));
            navigationYearBtn.click();
            clicks--;
        }
        return yearIndexToSelect;
    }

    private void selectYearIfPresent(String yearToSelect, ListOfWebElementFacades years) {
        for (int i = 1; i < years.size() - 1; i++) {
            if (years.get(i).getText().equals(yearToSelect)) {
                years.get(i).click();
                break;
            }
        }
    }

    private void selectMonthFromDropdown(Calendar date) {
        expandDatePickerIfCollapsed();
        WebElementFacade datePickerMonth = findDatePickerElement(monthDropdownBy);
        String monthToSelect = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        if (!datePickerMonth.getText().equals(monthToSelect)) {
            datePickerMonth.click();
            ListOfWebElementFacades monthList = findDatePickerElements(By.className("react-datepicker__month-option"));
            for (WebElementFacade month : monthList) {
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
        findDatePickerElement(By.xpath(String.format("//div[contains(@aria-label, '%s')]", getFormattedDate(date, "MMMMM d")))).click();
    }

    private void selectTime(Calendar date) {
        if (Integer.parseInt(getFormattedDate(date, "m")) % 15 == 0) {
            findDatePickerElement(By.xpath(String.format("//li[text() = '%s']", getFormattedDate(date, "HH:mm")))).click();
        } else {
            throw new IllegalArgumentException("The minute value " + getFormattedDate(date, "mm") + " is incorrect. The DatePicker range of time is 15 minutes so correct minute values are 0, 15, 30, 45.");
        }
    }

    private void expandDatePicker() {
        findDatePickerElement(datePickerInputBy).click();
    }

    private void scrollIntoView(WebElementFacade element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
    }

    private void expandDatePickerIfCollapsed() {
        if (findDatePickerElements(By.className("react-datepicker-popper")).size() < 1) {
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
