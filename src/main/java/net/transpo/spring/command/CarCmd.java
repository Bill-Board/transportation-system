package net.transpo.spring.command;

import net.transpo.spring.entity.Car;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.Route;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shoebakib
 * @since 3/31/24
 */
public class CarCmd extends Command {

    private static final long serialVersionUID = 1L;

    @Valid
    private Car car;

    private List<Route> routes;

    private List<Employee> drivers;

    private List<Employee> managers;

    private List<Employee> employees;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Employee> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Employee> drivers) {
        this.drivers = drivers;
    }

    public List<Employee> getManagers() {
        return managers;
    }

    public void setManagers(List<Employee> managers) {
        this.managers = managers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
