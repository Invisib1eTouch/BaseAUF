package pages.ToolsQAPages;

import baseEntities.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wrappers.DatePickerWithTime;

public class DatePickerPage extends BasePage {
    private static final By datePickerWithTimeBy = By.id("dateAndTimePicker");

    public DatePickerPage(WebDriver driver) {
        super(driver, null);
    }

    @Override
    protected By getPageOpenedIndicatorBy() {
        return datePickerWithTimeBy;
    }

    public DatePickerWithTime getDatePickerWithTime(){
        return new DatePickerWithTime(driver, datePickerWithTimeBy);
    }
}
