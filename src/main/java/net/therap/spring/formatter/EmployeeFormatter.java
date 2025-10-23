package net.therap.spring.formatter;

import net.therap.spring.entity.Employee;
import net.therap.spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Component
public class EmployeeFormatter implements Formatter<Employee> {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Employee parse(String text, Locale locale) throws ParseException {
        if (text.isEmpty()) {
            return null;
        }

        return employeeService.findByIdAndStatus(Integer.parseInt(text));
    }

    @Override
    public String print(Employee employee, Locale locale) {
        return employee.getName();
    }

}
