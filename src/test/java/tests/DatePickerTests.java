package tests;

import baseEntities.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pages.ToolsQAPages.DatePickerPage;
import testData.DateArgumentProvider;

import java.util.Calendar;

@Tag("DatePickerTest")
public class DatePickerTests extends BaseTest {
    public static final String BASE_URL = "https://demoqa.com/date-picker";

    @ParameterizedTest(name = "{index}. Set date by input")
    @ArgumentsSource(DateArgumentProvider.class)
    void setDateByInputTest(Calendar calendar) {
        driver.get(BASE_URL);
        DatePickerPage datePickerPage = new DatePickerPage(driver);

        datePickerPage.getDatePickerWithTime().setDateByInputValue(calendar);
        Calendar dateFormDatePicker = datePickerPage.getDatePickerWithTime().getDateFromDatePicker();

        Assertions.assertEquals(calendar, dateFormDatePicker);
    }

    @ParameterizedTest(name = "{index}. Set date by selecting")
    @ArgumentsSource(DateArgumentProvider.class)
    public void setDateBySelectingTest(Calendar calendar) {
        driver.get(BASE_URL);
        DatePickerPage datePickerPage = new DatePickerPage(driver);

        datePickerPage.getDatePickerWithTime().setDateBySelectParameters(calendar);
        Calendar dateFormDatePicker = datePickerPage.getDatePickerWithTime().getDateFromDatePicker();

        Assertions.assertEquals(calendar, dateFormDatePicker);
    }
}

