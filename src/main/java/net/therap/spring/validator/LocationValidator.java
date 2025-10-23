package net.therap.spring.validator;

import net.therap.spring.command.LocationCmd;
import net.therap.spring.entity.Location;
import net.therap.spring.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Component
public class LocationValidator implements Validator {

    public static final String NAME = "location.name";

    public static final String ERROR_CODE_LOCATION_EXIST = "LocationExist";

    @Autowired
    private LocationService locationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LocationCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LocationCmd locationCmd = (LocationCmd) target;
        Location location = locationCmd.getLocation();

        if (nonNull(location.getName())) {

            if (locationCmd.getLocation().isNew()) {
                this.nameValidation(location, errors);
            } else {
                Location existingLocation = locationService.find(location.getId());

                this.nameValidation(location, existingLocation, errors);
            }
        }
    }

    private void nameValidation(Location location, Errors errors) {
        if (locationService.isExistByName(location.getName())) {
            errors.rejectValue(NAME, ERROR_CODE_LOCATION_EXIST);
        }
    }

    private void nameValidation(Location location, Location existingLocation, Errors errors) {
        if (isNull(existingLocation)) {
            errors.rejectValue(NAME, ERROR_CODE_LOCATION_EXIST);
        }

        if (locationService.isExistByName(location.getName())
                && !location.getName().equals(existingLocation.getName())) {

            errors.rejectValue(NAME, ERROR_CODE_LOCATION_EXIST);
        }
    }

}
