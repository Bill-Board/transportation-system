package net.therap.spring.helper;

import net.therap.spring.entity.*;
import net.therap.spring.service.AssignmentService;
import net.therap.spring.service.CarService;
import net.therap.spring.service.LocationService;
import net.therap.spring.service.RouteService;
import net.therap.spring.util.UtilEmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static net.therap.spring.constant.Constants.DRIVING_STATUS_ACTIVE;

/**
 * @author shoebakib
 * @since 3/28/24
 */
@Component
public class HomeHelper {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CarService carService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private LocationService locationService;

    public void populateHomePageModel(ModelMap model, HttpSession session) {
        Employee employee = userHelper.findActiveUser(session);

        model.addAttribute("employee", employee);

        if (UtilEmployeeType.isEmployeeGeneralEmployee(employee.getEmployeeTypes())) {

            if (assignmentService.isAssignInCar(employee.getId())) {
                Car car = assignmentService.findCarByEmployeeId(employee.getId());

                populateModel(model, car);
            }
        }

        if (UtilEmployeeType.isEmployeeSupportStaff(employee.getEmployeeTypes())) {
            if (Objects.equals(employee.getDrivingStatus(), DRIVING_STATUS_ACTIVE)) {
                Car car = carService.findByDriverIdAndStatus(employee.getId());

                populateModel(model, car);
            }
        }

        model.addAttribute("locations", locationService.findAll());
    }

    private void populateModel(ModelMap model, Car car) {
        Route route = car.getRoute();
        List<Location> routeLocations = routeService.findLocations(route.getId());

        model.addAttribute("car", car);
        model.addAttribute("route", route);
        model.addAttribute("routeLocations", routeLocations);
    }

}