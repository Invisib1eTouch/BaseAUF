package tests;

import baseEntities.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.ToolsQAPages.DatePickerPage;
import testData.DateParameterResolver;

import java.util.Calendar;
import java.util.List;

@Tag("DatePickerTest")
@ExtendWith(DateParameterResolver.class)
public class DatePickerTests extends BaseTest {

    @Test
    void setDateByInputTest(List<Calendar> calendar) {
        driver.get("https://demoqa.com/date-picker");
        DatePickerPage datePickerPage = new DatePickerPage(driver);

        for (Calendar date : calendar) {
            datePickerPage.getDatePickerWithTime().setDateByInputValue(date);
            Calendar dateFormDatePicker = datePickerPage.getDatePickerWithTime().getDateFromDatePicker();

            Assertions.assertEquals(date, dateFormDatePicker);
        }

    }

    @Test
    public void setDateBySelectingTest(List<Calendar> calendar) {
        driver.get("https://demoqa.com/date-picker");
        DatePickerPage datePickerPage = new DatePickerPage(driver);

        for (Calendar date : calendar) {
            datePickerPage.getDatePickerWithTime().setDateBySelectParameters(date);
            Calendar dateFormDatePicker = datePickerPage.getDatePickerWithTime().getDateFromDatePicker();

            Assertions.assertEquals(date, dateFormDatePicker);
        }
    }
}

