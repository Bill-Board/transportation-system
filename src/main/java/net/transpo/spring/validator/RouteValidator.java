package net.transpo.spring.validator;

import net.transpo.spring.command.RouteCmd;
import net.transpo.spring.entity.Location;
import net.transpo.spring.entity.Route;
import net.transpo.spring.service.LocationService;
import net.transpo.spring.service.RouteService;
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
                    Location startLocation = locationService.findNameByName("#FROM_START");

                    if (!startLocation.equals(location)) {
                        errors.rejectValue("route.locations", "route.location.start");
                    }
                } catch (NoResultException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
