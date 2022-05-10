package steps.ToolsQAPageSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.ToolsQAPages.DatePickerPage;
import wrappers.DatePickerWithTime;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerPageSteps {
    DatePickerPage datePickerPage;

    @Given("User proceeds to page contains Date picker")
    public void proceed_to_date_picker_page() {
        datePickerPage.open();
    }


    @When("User input {int}-{string}-{int}-{int}-{int} date to Date picker")
    public void user_input_date_to_date_picker(int year, String month, int day, int hours, int minutes) {
        Calendar date = new GregorianCalendar(year, DatePickerWithTime.getCalendarMonth(month), day, hours, minutes);
        datePickerPage.getDatePickerWithTime().setDateByInputValue(date);
    }

    @Then("The set data matches the entered {int}-{string}-{int}-{int}-{int} date")
    public void verify_correct_date_is_selected(int year, String month, int day, int hour, int minutes) {
        Calendar dateFormDatePicker = datePickerPage.getDatePickerWithTime().getDateFromDatePicker();
        Calendar initialDate = new GregorianCalendar(year, DatePickerWithTime.getCalendarMonth(month), day, hour, minutes);
        Assertions.assertEquals(initialDate, dateFormDatePicker);
    }

    @When("User selects {int}-{string}-{int}-{int}-{int} date in Date picker")
    public void user_select_date_in_date_picker(int year, String month, int day, int hours, int minutes) {
        Calendar date = new GregorianCalendar(year, DatePickerWithTime.getCalendarMonth(month), day, hours, minutes);
        datePickerPage.getDatePickerWithTime().setDateBySelectParameters(date);
    }
}
