package net.therap.spring.formatter;

import net.therap.spring.entity.Location;
import net.therap.spring.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Component
public class LocationFormatter implements Formatter<Location> {

    @Autowired
    private LocationService locationService;


    @Override
    public Location parse(String text, Locale locale) throws ParseException {
        if (text.isEmpty()) {
            return null;
        }

        return locationService.find(Integer.parseInt(text));
    }

    @Override
    public String print(Location location, Locale locale) {
        return null;
    }

}
