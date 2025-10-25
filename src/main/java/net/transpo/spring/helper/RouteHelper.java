package net.transpo.spring.helper;

import net.transpo.spring.command.RouteCmd;
import net.transpo.spring.entity.Location;
import net.transpo.spring.entity.Route;
import net.transpo.spring.service.LocationService;
import net.transpo.spring.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static net.transpo.spring.constant.CmdConstants.ROUTE_CMD;
import static net.transpo.spring.constant.URLConstants.LIST_ROUTE_URL;

/**
 * @author shoebakib
 * @since 4/2/24
 */
@Component
public class RouteHelper {

    @Autowired
    private RouteService routeService;

    @Autowired
    private LocationService locationService;

    public void populateModelForList(ModelMap model) {
        if (!model.containsAttribute("routes")) {
            model.addAttribute("routes", routeService.findAll());
        }
    }

    public void populateModelForCreate(ModelMap model) {
        RouteCmd routeCmd = new RouteCmd();
        routeCmd.setRoute(new Route());
        routeCmd.setBackLink(LIST_ROUTE_URL);
        model.addAttribute(ROUTE_CMD, routeCmd);
    }

    public void addLocationInRouteCmd(RouteCmd routeCmd, ModelMap model, int locationId) {
        Location location = this.findLocationById(locationId);

        routeCmd.getRoute().getLocations().add(location);

        model.addAttribute(ROUTE_CMD, routeCmd);
    }

    public void removeLocationFromRouteCmd(RouteCmd routeCmd, ModelMap model, int locationId) {
        Location location = this.findLocationById(locationId);

        routeCmd.getRoute().getLocations().remove(location);

        model.addAttribute(ROUTE_CMD, routeCmd);
    }

    public Route findById(int id) {
        Route route = routeService.findById(id);

        if (isNull(route)) {
            throw new NoResultException();
        }

        return route;
    }

    public List<Location> getLocationList(RouteCmd routeCmd) {
        Set<Location> existingLocations = routeCmd.getRoute().getLocations();
        List<Location> AllLocations = locationService.findAll();
        List<Location> locations = new ArrayList<>();

        for (Location location : AllLocations) {
            if (!existingLocations.contains(location)) {
                locations.add(location);
            }
        }

        return locations;
    }

    private Location findLocationById(int id) {
        Location location = locationService.find(id);

        if (isNull(location)) {
            throw new NoResultException();
        }

        return location;
    }

}
