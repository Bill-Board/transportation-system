package net.transpo.spring.helper;

import net.transpo.spring.command.CarCmd;
import net.transpo.spring.entity.*;
import net.transpo.spring.exception.AccessDeniedException;
import net.transpo.spring.service.AssignmentService;
import net.transpo.spring.service.CarService;
import net.transpo.spring.service.EmployeeService;
import net.transpo.spring.service.RouteService;
import net.transpo.spring.util.UtilEmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.transpo.spring.constant.CmdConstants.CAR_CMD;
import static net.transpo.spring.constant.Constants.*;
import static net.transpo.spring.constant.URLConstants.LIST_CAR_URL;
import static net.transpo.spring.util.Util.isZero;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Component
public class CarHelper {

    public static final int ZERO_ID = 0;

    @Autowired
    private CarService carService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private UserHelper userHelper;

    public void populateModelForList(ModelMap model) {
        model.addAttribute("cars", carService.findAll());
    }

    public void populateModel(ModelMap model, int id) {
        Car car;

        if (isZero(id)) {
            car = new Car();
        } else {
            car = this.findCarByIdAndStatus(id);
        }

        CarCmd carCmd = this.createCarCmd(car, LIST_CAR_URL, false);
        carCmd.setRoutes(routeService.findAll());

        if (car.isNew()) {
            carCmd.setDrivers(employeeService.findInactiveDriverList());
        } else {
            carCmd.setReadOnly(true);
            carCmd.setDrivers(getInActiveDriverList(car));
            carCmd.setManagers(findAssignedEmployeeListInCarByCarId(id));
        }

        model.addAttribute(CAR_CMD, carCmd);
    }

    public void populateModel(ModelMap model) {
        this.populateModel(model, ZERO_ID);
    }

    public void populateModelForDetails(ModelMap model, int id, String backLink) {
        Car car = this.findCarByIdAndStatus(id);

        CarCmd carCmd = this.createCarCmd(car, backLink, true);
        carCmd.setEmployees(findAssignedEmployeeListInCarByCarId(id));

        model.addAttribute(CAR_CMD, carCmd);
    }

    public void saveOrUpdate(CarCmd carCmd) {
        if (carCmd.getCar().isNew()) {
            this.save(carCmd.getCar());
        } else {
            this.update(carCmd.getCar());
        }
    }

    public void removeCar(Car car) {
        Employee driver = car.getDriver();
        driver.setDrivingStatus(DRIVING_STATUS_INACTIVE);
        employeeService.saveOrUpdate(driver);

        assignmentService.removeByCarId(car.getId());

        car.setStatus(STATUS_INACTIVE);
        car.setManager(null);
        carService.saveOrUpdate(car);
    }

    public void checkCarDetailsAccessible(HttpSession session, int carId) {
        Employee employee = userHelper.findActiveUser(session);

        if (UtilEmployeeType.isEmployeeAdmin(employee.getEmployeeTypes())) {
            return;
        }

        if (UtilEmployeeType.isEmployeeSupportStaff(employee.getEmployeeTypes())) {
            Car car = this.findCarByIdAndStatus(carId);

            if (!(Objects.equals(employee.getDrivingStatus(), DRIVING_STATUS_ACTIVE)
                    && nonNull(car.getDriver())
                    && car.getDriver().equals(employee))) {

                return;
            }
        }

        if (assignmentService.isAssignInCar(employee.getId())) {
            Car car = assignmentService.findCarByEmployeeId(employee.getId());

            if (!(nonNull(car) && car.getId() == carId)) {
                return;
            }
        }

        throw new AccessDeniedException("Car cannot access");
    }

    public boolean isCarRemovable(int carId) {
        return findAssignedEmployeeListInCarByCarId(carId).isEmpty();
    }

    public Car findCarByIdAndStatus(int carId) {
        Car car = carService.findByIdAndStatus(carId);

        if (isNull(car)) {
            throw new NoResultException("car no found");
        }

        return car;
    }

    private void save(Car car) {
        carService.saveOrUpdate(car);

        Route route = routeService.findById(car.getRoute().getId());
        route.getCars().add(car);
        routeService.saveOrUpdate(route);

        car.getDriver().setDrivingStatus(DRIVING_STATUS_ACTIVE);

        employeeService.saveOrUpdate(car.getDriver());
    }

    private void update(Car car) {
        Employee olderDriver = this.findCarByIdAndStatus(car.getId()).getDriver();

        if (!olderDriver.equals(car.getDriver())) {
            olderDriver.setDrivingStatus(DRIVING_STATUS_INACTIVE);
            employeeService.saveOrUpdate(olderDriver);

            car.getDriver().setDrivingStatus(DRIVING_STATUS_ACTIVE);
            employeeService.saveOrUpdate(car.getDriver());
        }

        carService.saveOrUpdate(car);
    }

    private CarCmd createCarCmd(Car car, String backLink, boolean readOnly) {
        CarCmd carCmd = new CarCmd();
        carCmd.setCar(car);
        carCmd.setBackLink(backLink);
        carCmd.setReadOnly(readOnly);

        return carCmd;
    }

    private List<Employee> getInActiveDriverList(Car car) {
        List<Employee> drivers = employeeService.findInactiveDriverList();

        if (nonNull(car.getDriver()) && !drivers.contains(car.getDriver())) {
            drivers.add(car.getDriver());
        }

        return drivers;
    }

    private List<Employee> findAssignedEmployeeListInCarByCarId(int carId) {
        return assignmentService.findAllByCarId(carId).stream()
                .map(Assignment::getEmployee)
                .filter(employee -> Objects.equals(employee.getStatus(), STATUS_ACTIVE))
                .collect(Collectors.toList());
    }

}