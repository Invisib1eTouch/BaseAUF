package testData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.*;

public class DateParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(), "java.util.List<java.util.Calendar>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        List<Calendar> date = new ArrayList<>();
        date.add(new GregorianCalendar(2000, Calendar.OCTOBER, 13, 22, 15));
        date.add(new GregorianCalendar(2028, Calendar.SEPTEMBER, 19, 6, 30));
        date.add(new GregorianCalendar(1000, Calendar.OCTOBER, 13, 22, 15));
        return date;
    }
}
