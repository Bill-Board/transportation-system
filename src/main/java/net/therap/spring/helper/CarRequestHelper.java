package net.therap.spring.helper;

import net.therap.spring.command.CarRequestCmd;
import net.therap.spring.entity.Car;
import net.therap.spring.entity.CarRequest;
import net.therap.spring.entity.Employee;
import net.therap.spring.entity.Route;
import net.therap.spring.service.AssignmentService;
import net.therap.spring.service.CarRequestService;
import net.therap.spring.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static net.therap.spring.constant.CmdConstants.REQUEST_CMD;
import static net.therap.spring.constant.Constants.STATUS_ACTIVE;
import static net.therap.spring.constant.URLConstants.LIST_REQUEST_URL;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Component
public class CarRequestHelper {

    private static final Logger log = LoggerFactory.getLogger(CarRequestHelper.class);

    @Autowired
    private CarRequestService carRequestService;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private RouteService routeService;

    @Autowired
    private AssignmentService assignmentService;

    public void populateModelForList(ModelMap model, HttpSession session) {
        Employee employee = userHelper.findActiveUser(session);

        model.addAttribute("requests", carRequestService.findAllByEmployeeId(employee.getId()));
    }

    public void populateModelForCreateFirstForm(ModelMap model) {
        CarRequestCmd requestCmd = createRequestCmd(new CarRequest(), routeService.findAll(), LIST_REQUEST_URL);

        model.addAttribute(REQUEST_CMD, requestCmd);
    }

    public CarRequestCmd createRequestCmd(CarRequest carRequest, List<Route> routes, String backLink) {
        CarRequestCmd requestCmd = new CarRequestCmd();
        requestCmd.setRoutes(routes);
        requestCmd.setRequest(carRequest);
        requestCmd.setBackLink(backLink);

        return requestCmd;
    }

    public boolean isRequestPending(Employee employee) {
        return carRequestService.isPendingARequest(employee.getId());
    }

    public boolean isAssignedInRequestedCar(Employee employee, CarRequestCmd carRequestCmd) {
        if (assignmentService.isAssignInCar(employee.getId())) {
            Car car = assignmentService.findCarByEmployeeId(employee.getId());

            return carRequestCmd.getRequest().getCar().equals(car);
        }

        return false;
    }

    public List<Car> getAllCarListByRouteId(int id) {
        List<Car> cars = new ArrayList<>();
        List<Car> allCars = this.findAllCarByRouteId(id);

        log.debug("\n\nAll cars\n" + allCars + "\n\n");

        for (Car car : allCars) {
            if (Objects.equals(car.getStatus(), STATUS_ACTIVE)) {
                cars.add(car);
            }
        }

        return cars;
    }

    private List<Car> findAllCarByRouteId(int id) {
        Route route = routeService.findById(id);

        if (isNull(route)) {
            throw new NoResultException();
        }

        return new ArrayList<>(route.getCars());
    }

}