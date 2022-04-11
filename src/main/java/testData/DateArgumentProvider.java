package testData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

public class DateArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(new GregorianCalendar(2000, Calendar.OCTOBER, 13, 22, 15)),
                Arguments.of(new GregorianCalendar(2028, Calendar.SEPTEMBER, 19, 6, 30)),
                Arguments.of(new GregorianCalendar(1000, Calendar.OCTOBER, 13, 22, 15))
        );
    }
}
