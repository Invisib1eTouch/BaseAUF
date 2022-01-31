import baseEntities.BaseTest;
import org.junit.jupiter.api.Test;
import pages.ToolsQAPages.DatePickerPage;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerTests extends BaseTest {

    @Test
    public void t1() {
        driver.get("https://demoqa.com/date-picker");
        DatePickerPage datePickerPage = new DatePickerPage(driver);

//        datePickerPage.getDatePickerWithTime().setDateByInputValue(new GregorianCalendar(1996, Calendar.SEPTEMBER, 19,6, 12));
        datePickerPage.getDatePickerWithTime().setDateBySelectParameters(new GregorianCalendar(2022, Calendar.SEPTEMBER, 19,6, 12));
    }
}
