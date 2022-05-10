package pages.ToolsQAPages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import wrappers.DatePickerWithTime;

@DefaultUrl("https://demoqa.com/date-picker")
public class DatePickerPage extends PageObject {
    private static final By datePickerWithTimeBy = By.id("dateAndTimePicker");

    public DatePickerWithTime getDatePickerWithTime() {
        return new DatePickerWithTime(getDriver(), datePickerWithTimeBy);
    }
}
