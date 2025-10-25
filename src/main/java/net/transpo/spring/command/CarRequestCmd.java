package net.transpo.spring.command;

import net.transpo.spring.entity.CarRequest;
import net.transpo.spring.entity.Route;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shoebakib
 * @since 4/1/24
 */
public class CarRequestCmd extends Command {

    private static final long serialVersionUID = 1L;

    @Valid
    private CarRequest request;

    private List<Route> routes;

    public CarRequest getRequest() {
        return request;
    }

    public void setRequest(CarRequest request) {
        this.request = request;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
