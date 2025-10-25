package net.transpo.spring.command;

import net.transpo.spring.entity.Location;

import javax.validation.Valid;

/**
 * @author shoebakib
 * @since 3/31/24
 */
public class LocationCmd extends Command {

    private static final long serialVersionUID = 1L;

    @Valid
    private Location location;

    private String imageURL;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
