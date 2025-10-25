package net.transpo.spring.formatter;

import net.transpo.spring.entity.EmployeeType;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author shoebakib
 * @since 3/27/24
 */
public class EmployeeTypeFormatter implements Formatter<EmployeeType> {

    @Override
    public EmployeeType parse(String text, Locale locale) throws ParseException {
        return EmployeeType.getType(text);
    }

    @Override
    public String print(EmployeeType employeeType, Locale locale) {
        return employeeType.getDisplayValue();
    }

}
