package net.transpo.spring.formatter;

import net.transpo.spring.entity.Route;
import net.transpo.spring.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

import static java.util.Objects.isNull;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Component
public class RouteFormatter implements Formatter<Route> {

    @Autowired
    private RouteService routeService;

    @Override
    public Route parse(String text, Locale locale) throws ParseException {
        if (isNull(text) || text.isEmpty()) {
            return null;
        }

        return routeService.findById(Integer.parseInt(text));
    }

    @Override
    public String print(Route route, Locale locale) {
        return route.getName();
    }

}
