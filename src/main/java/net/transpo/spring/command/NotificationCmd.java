package net.transpo.spring.command;

import net.transpo.spring.entity.*;

import java.util.List;

/**
 * @author shoebakib
 * @since 4/2/24
 */
public class NotificationCmd extends Command {

    private static final long serialVersionUID = 1L;

    private CarRequest request;

    private Car car;

    private Employee employee;

    private Route route;

    private List<Location> locations;

    public CarRequest getRequest() {
        return request;
    }

    public void setRequest(CarRequest request) {
        this.request = request;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}
