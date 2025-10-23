package net.therap.spring.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author shoebakib
 * @since 5/8/24
 */
public class UtilDateTime {

    public static int getTimePeriod(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
