package net.therap.spring.controller;

import net.therap.spring.command.RouteCmd;
import net.therap.spring.dto.RouteDetailsDTO;
import net.therap.spring.entity.EmployeeType;
import net.therap.spring.entity.Location;
import net.therap.spring.entity.Route;
import net.therap.spring.helper.Authorization;
import net.therap.spring.helper.RouteHelper;
import net.therap.spring.service.LocationService;
import net.therap.spring.service.RouteService;
import net.therap.spring.validator.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static net.therap.spring.constant.CmdConstants.ROUTE_CMD;
import static net.therap.spring.constant.Constants.SUCCESS_MESSAGE;
import static net.therap.spring.constant.URLConstants.LIST_ROUTE_URL;
import static net.therap.spring.util.Util.redirectTo;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Controller
@RequestMapping("/route")
@SessionAttributes(ROUTE_CMD)
public class RouteController {

    public static final String ROUTE_LIST_VIEW = "route/list-route";

    public static final String ROUTE_FORM_VIEW = "route/route-form";

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteHelper routeHelper;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private Authorization authorization;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        String[] allowFields = {"route.name", "route.locations", "route.cars"};
        dataBinder.setAllowedFields(allowFields);

        dataBinder.addValidators(routeValidator);
    }

    @GetMapping(value = "/list")
    public String getRouteList(ModelMap model) {
        routeHelper.populateModelForList(model);

        return ROUTE_LIST_VIEW;
    }

    @GetMapping(value = "/create")
    public String showRouteCreateForm(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);
        routeHelper.populateModelForCreate(model);

        return ROUTE_FORM_VIEW;
    }

    @PostMapping(value = "/updateRouteCmd")
    @ResponseBody
    public ResponseEntity<List<Location>> updateRouteCmd(@RequestParam("locationId") int locationId,
                                                         @ModelAttribute(ROUTE_CMD) RouteCmd routeCmd,
                                                         ModelMap model,
                                                         HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        routeHelper.addLocationInRouteCmd(routeCmd, model, locationId);

        return new ResponseEntity<>(new ArrayList<>(routeCmd.getRoute().getLocations()), HttpStatus.OK);
    }

    @GetMapping(value = "/getLocations")
    @ResponseBody
    public ResponseEntity<List<Location>> getLocations(@ModelAttribute(ROUTE_CMD) RouteCmd routeCmd,
                                                       HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        return new ResponseEntity<>(routeHelper.getLocationList(routeCmd), HttpStatus.OK);
    }

    @PostMapping(value = "/removeLocation")
    @ResponseBody
    public ResponseEntity<List<Location>> removeLocation(@RequestParam(value = "locationId", defaultValue = "0") int locationId,
                                                         @ModelAttribute(ROUTE_CMD) RouteCmd routeCmd,
                                                         ModelMap model,
                                                         HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        routeHelper.removeLocationFromRouteCmd(routeCmd, model, locationId);

        return new ResponseEntity<>(new ArrayList<>(routeCmd.getRoute().getLocations()), HttpStatus.OK);
    }

    @PostMapping(value = "/route", params = "action_save")
    public String save(@Valid @ModelAttribute(ROUTE_CMD) RouteCmd routeCmd,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       SessionStatus status,
                       HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        if (bindingResult.hasErrors()) {
            return ROUTE_FORM_VIEW;
        }

        routeService.saveOrUpdate(routeCmd.getRoute());

        status.setComplete();

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("route.add.success.message"));

        return redirectTo(LIST_ROUTE_URL);
    }

    @GetMapping(value = "/details")
    @ResponseBody
    public ResponseEntity<RouteDetailsDTO> details(@RequestParam(value = "routeId", defaultValue = "0") int routeId) {
        Route route = routeHelper.findById(routeId);

        RouteDetailsDTO routeDetailsDTO = new RouteDetailsDTO();
        routeDetailsDTO.setName(route.getName());
        routeDetailsDTO.setLocations(routeService.findLocations(routeId));

        return new ResponseEntity<>(routeDetailsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/route", params = "action_back")
    public String back(@ModelAttribute(value = ROUTE_CMD, binding = false) RouteCmd routeCmd,
                       SessionStatus status,
                       HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);
        status.setComplete();

        return redirectTo(routeCmd.getBackLink());
    }

}