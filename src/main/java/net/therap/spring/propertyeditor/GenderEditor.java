package net.therap.spring.propertyeditor;

import net.therap.spring.entity.GenderType;

import java.beans.PropertyEditorSupport;

import static java.util.Objects.isNull;

/**
 * @author shoebakib
 * @since 3/4/24
 */
public class GenderEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (isNull(text) || text.isEmpty()) {
            setValue(null);
        } else {
            setValue(GenderType.valueOf(text.toUpperCase()));
        }
    }

    @Override
    public String getAsText() {
        GenderType genderType = (GenderType) getValue();

        return isNull(genderType) ? null : genderType.getDisplayValue();
    }

}