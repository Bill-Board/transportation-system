package net.transpo.spring.entity;

/**
 * @author shoebakib
 * @since 3/4/24
 */
public enum GenderType {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String displayValue;

    GenderType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}