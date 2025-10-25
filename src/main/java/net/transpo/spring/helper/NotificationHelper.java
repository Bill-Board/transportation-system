package net.transpo.spring.helper;

import net.transpo.spring.command.NotificationCmd;
import net.transpo.spring.entity.Assignment;
import net.transpo.spring.entity.Car;
import net.transpo.spring.entity.CarRequest;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.service.AssignmentService;
import net.transpo.spring.service.CarRequestService;
import net.transpo.spring.service.CarService;
import net.transpo.spring.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;

import static java.util.Objects.isNull;
import static net.transpo.spring.constant.CmdConstants.NOTIFICATION_CMD;
import static net.transpo.spring.constant.Constants.*;
import static net.transpo.spring.constant.URLConstants.LIST_NOTIFICATION_URL;

/**
 * @author shoebakib
 * @since 4/2/24
 */
@Component
public class NotificationHelper {

    @Autowired
    private CarRequestService carRequestService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CarService carService;

    public void populateModelForList(ModelMap model) {
        model.addAttribute("requests", carRequestService.findAll());
    }

    public void populateModelForDetails(ModelMap model, int id) {
        CarRequest request = this.findById(id);

        NotificationCmd notificationCmd = new NotificationCmd();
        notificationCmd.setLocations(routeService.findLocations(request.getRoute().getId()));
        notificationCmd.setRequest(request);
        notificationCmd.setCar(request.getCar());
        notificationCmd.setEmployee(request.getEmployee());
        notificationCmd.setRoute(request.getRoute());
        notificationCmd.setBackLink(LIST_NOTIFICATION_URL);

        model.addAttribute(NOTIFICATION_CMD, notificationCmd);
    }

    public void assignInACar(NotificationCmd notificationCmd) {
        Employee employee = notificationCmd.getEmployee();
        Car car = notificationCmd.getCar();
        int availableSeat = car.getAvailableSeat();

        ifAssignInCar(notificationCmd.getEmployee());

        notificationCmd.getRequest().setStatus(APPROVED);

        car.setAvailableSeat(availableSeat - DECREMENT_ONE_SEAT);
        carService.saveOrUpdate(car);
        carRequestService.saveOrUpdate(notificationCmd.getRequest());

        Assignment assignment = new Assignment();
        assignment.setCar(car);
        assignment.setEmployee(employee);
        assignmentService.saveOrUpdate(assignment);
    }

    private void ifAssignInCar(Employee employee) {

        if (assignmentService.isAssignInCar(employee.getId())) {
            Assignment assignment = assignmentService.findByEmployeeId(employee.getId());

            Car car = assignment.getCar();
            car.setAvailableSeat(car.getAvailableSeat() + INCREMENT_ONE_SEAT);

            if (employee.equals(car.getManager())) {
                car.setManager(null);
            }

            carService.saveOrUpdate(car);

            assignmentService.remove(assignment.getId());
        }
    }

    private CarRequest findById(int id) {
        CarRequest carRequest = carRequestService.findById(id);

        if (isNull(carRequest)) {
            throw new NoResultException();
        }

        return carRequest;
    }

}
