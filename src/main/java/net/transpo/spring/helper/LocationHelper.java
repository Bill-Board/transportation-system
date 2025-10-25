package net.transpo.spring.helper;

import net.transpo.spring.command.LocationCmd;
import net.transpo.spring.entity.Attachment;
import net.transpo.spring.entity.Location;
import net.transpo.spring.service.LocationService;
import net.transpo.spring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;

import static java.util.Objects.isNull;
import static net.transpo.spring.constant.CmdConstants.LOCATION_CMD;
import static net.transpo.spring.constant.URLConstants.LIST_LOCATION_URL;

/**
 * @author shoebakib
 * @since 4/2/24
 */
@Component
public class LocationHelper {

    @Autowired
    private LocationService locationService;

    public void populateModelForCreate(ModelMap model) {
        LocationCmd locationCmd = this.createLocationCmd(new Location(), false);

        model.addAttribute(LOCATION_CMD, locationCmd);
    }

    public void populateModelForList(ModelMap model) {
        model.addAttribute("locations", locationService.findAll());
    }

    public void populateModelForDetails(ModelMap model, int id) {
        Location location = findById(id);

        LocationCmd locationCmd = this.createLocationCmd(location, true);
        locationCmd.setImageURL(Util.generateImageUrl(locationCmd.getLocation().getImage()));

        model.addAttribute(LOCATION_CMD, locationCmd);
    }

    public void populateModelForEdit(ModelMap model, int id) {
        Location location = findById(id);

        LocationCmd locationCmd = this.createLocationCmd(location, false);

        model.addAttribute(LOCATION_CMD, locationCmd);
    }

    public void saveOrUpdate(LocationCmd locationCmd, MultipartFile attachment) throws IOException {
        if (!attachment.isEmpty()) {
            Attachment image = new Attachment();
            image.setName(attachment.getOriginalFilename());
            image.setType(attachment.getContentType());
            image.setData(attachment.getBytes());

            locationCmd.getLocation().setImage(image);
        }

        locationService.saveOrUpdate(locationCmd.getLocation());
    }

    private LocationCmd createLocationCmd(Location location, boolean readOnly) {
        LocationCmd locationCmd = new LocationCmd();

        locationCmd.setLocation(location);
        locationCmd.setBackLink(LIST_LOCATION_URL);
        locationCmd.setReadOnly(readOnly);

        return locationCmd;
    }

    private Location findById(int id) {
        Location location = locationService.find(id);

        if (isNull(location)) {
            throw new NoResultException();
        }

        return location;
    }

}
