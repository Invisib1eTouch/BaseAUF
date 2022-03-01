package tests;

import baseEntities.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ToolsQAPages.DatePickerPage;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Tag("DatePickerTest")
public class DatePickerTests extends BaseTest {

    @Test
    public void setDateTest() {
        driver.get("https://demoqa.com/date-picker");
        DatePickerPage datePickerPage = new DatePickerPage(driver);

        datePickerPage.getDatePickerWithTime().setDateByInputValue(new GregorianCalendar(2000, Calendar.OCTOBER, 13, 22, 15));

        Calendar dateInitial = new GregorianCalendar(2028, Calendar.SEPTEMBER, 19, 6, 30);

        datePickerPage.getDatePickerWithTime().setDateBySelectParameters(dateInitial);

        Calendar dateFormDatePicker = datePickerPage.getDatePickerWithTime().getDateFromDatePicker();

        Assertions.assertEquals(dateInitial, dateFormDatePicker);
    }
}

