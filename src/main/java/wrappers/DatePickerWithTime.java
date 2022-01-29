package wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerWithTime {
    private final WebDriver driver;
    private WebElement datePicker;

    public DatePickerWithTime(WebDriver driver, By by) {
        this.driver = driver;
        this.datePicker = driver.findElement(by);
    }

    public void setDateByInputValue(Calendar date) {
        DateFormat dateFormat = new SimpleDateFormat("MMMMM d, yyyy h:mm a", Locale.ENGLISH);
        String resultDate = dateFormat.format(date.getTime());
        datePicker.findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        datePicker.findElement(By.tagName("input")).sendKeys(resultDate);
    }
}
