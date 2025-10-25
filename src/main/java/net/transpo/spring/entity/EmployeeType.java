package net.transpo.spring.entity;

import java.util.Arrays;

/**
 * @author shoebakib
 * @since 3/18/24
 */
public enum EmployeeType {

    SUPER_ADMIN("Super_Admin"),
    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    SUPPORT_STAFF("Support Staff");

    private final String displayValue;

    EmployeeType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static EmployeeType getType(String value) {
        return Arrays.stream(EmployeeType.values())
                .filter(et -> value.equals(et.getDisplayValue()))
                .findFirst()
                .orElse(null);
    }

}
