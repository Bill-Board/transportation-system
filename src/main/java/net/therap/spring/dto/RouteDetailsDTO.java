package net.therap.spring.dto;

import net.therap.spring.entity.Location;

import java.io.Serializable;
import java.util.List;

/**
 * @author shoebakib
 * @since 4/15/24
 */
public class RouteDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<Location> locations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}
