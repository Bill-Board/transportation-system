package net.therap.spring.command;

import net.therap.spring.entity.Route;

import javax.validation.Valid;

/**
 * @author shoebakib
 * @since 3/31/24
 */
public class RouteCmd extends Command {

    private static final long serialVersionUID = 1L;

    @Valid
    private Route route;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
