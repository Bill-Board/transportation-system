package net.therap.spring.validator;

import net.therap.spring.command.RouteCmd;
import net.therap.spring.entity.Location;
import net.therap.spring.entity.Route;
import net.therap.spring.service.LocationService;
import net.therap.spring.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.NoResultException;
import java.util.Iterator;

import static java.util.Objects.nonNull;

/**
 * @author shoebakib
 * @since 4/5/24
 */
@Component
public class RouteValidator implements Validator {

    @Autowired
    private RouteService routeService;

    @Autowired
    LocationService locationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RouteCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RouteCmd routeCmd = (RouteCmd) target;

        this.nameValidation(routeCmd.getRoute(), errors);

        this.locationValidation(routeCmd.getRoute(), errors);
    }

    private void nameValidation(Route route, Errors errors) {
        if (nonNull(route.getName())) {

            if (routeService.isExistByName(route.getName())) {
                errors.rejectValue("route.name", "NameExist");
            }
        }
    }

    private void locationValidation(Route route, Errors errors) {
        if (!route.getLocations().isEmpty()) {
            Iterator<Location> iterator = route.getLocations().iterator();

            if (iterator.hasNext()) {
                Location location = iterator.next();

                try {
                    Location locationTherap = locationService.findNameById("Therap");

                    if (!locationTherap.equals(location)) {
                        errors.rejectValue("route.locations", "route.location.start");
                    }
                } catch (NoResultException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
