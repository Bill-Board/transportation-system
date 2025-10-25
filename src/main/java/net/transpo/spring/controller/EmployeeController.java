package net.transpo.spring.controller;

import net.transpo.spring.command.EmployeeCmd;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.entity.GenderType;
import net.transpo.spring.helper.Authorization;
import net.transpo.spring.helper.EmployeeHelper;
import net.transpo.spring.helper.UserHelper;
import net.transpo.spring.propertyeditor.GenderEditor;
import net.transpo.spring.util.UtilEmployeeType;
import net.transpo.spring.validator.EmployeeValidator;
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

import static net.transpo.spring.constant.CmdConstants.EMPLOYEE_CMD;
import static net.transpo.spring.constant.Constants.DECLINE_MESSAGE;
import static net.transpo.spring.constant.Constants.SUCCESS_MESSAGE;
import static net.transpo.spring.constant.URLConstants.*;
import static net.transpo.spring.util.Util.redirectTo;
import static org.hibernate.validator.internal.metadata.core.ConstraintHelper.MESSAGE;

/**
 * @author shoebakib
 * @since 3/18/24
 */
@Controller
@RequestMapping("/employee")
@SessionAttributes(EMPLOYEE_CMD)
public class EmployeeController {

    public static final String REGISTER_VIEW = "register";

    public static final String EMPLOYEE_LIST_VIEW = "employee/list-employee";

    public static final String EMPLOYEE_EDIT_VIEW = "employee/employee-edit";

    public static final String PENDING_EMPLOYEE_LIST_VIEW = "employee/list-pending-employee";

    @Autowired
    private EmployeeHelper employeeHelper;

    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private Authorization authorization;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        dataBinder.setDisallowedFields("employee.id", "employee.status", "employee.authenticationInfo.hashcode");

        dataBinder.registerCustomEditor(GenderType.class, new GenderEditor());
        dataBinder.addValidators(employeeValidator);
    }

    @GetMapping(value = "/register")
    public String showRegisterForm(ModelMap model) {
        employeeHelper.populateModelForRegister(model);

        return REGISTER_VIEW;
    }

    @PostMapping(value = "/register")
    public String save(@Valid @ModelAttribute(EMPLOYEE_CMD) EmployeeCmd employeeCmd,
                       BindingResult bindingResult,
                       SessionStatus status,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return REGISTER_VIEW;
        }

        employeeHelper.saveEmployee(employeeCmd.getEmployee());
        status.setComplete();

        redirectAttributes.addFlashAttribute(MESSAGE, messageSourceAccessor.getMessage("employee.register.success.message"));

        return redirectTo(LOGIN_URL);
    }

    @GetMapping(value = "/pending-list")
    public String getPendingEmployeeList(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.SUPER_ADMIN);
        employeeHelper.populateModelForPendingList(model);

        return PENDING_EMPLOYEE_LIST_VIEW;
    }

    @GetMapping(value = "/list")
    public String getEmployeeList(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);
        employeeHelper.populateModelForList(model, EmployeeType.EMPLOYEE);

        return EMPLOYEE_LIST_VIEW;
    }

    @GetMapping(value = "/driverList")
    public String getDriverList(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);
        employeeHelper.populateModelForList(model, EmployeeType.SUPPORT_STAFF);

        return EMPLOYEE_LIST_VIEW;
    }

    @GetMapping(value = "/update-profile")
    public String updateProfileForm(ModelMap model, HttpSession session) {
        employeeHelper.populateModelForUpdate(model, session);

        return EMPLOYEE_EDIT_VIEW;
    }

    @PostMapping(value = "/employee", params = "action_update")
    public String update(@Valid @ModelAttribute(EMPLOYEE_CMD) EmployeeCmd employeeCmd,
                         BindingResult bindingResult,
                         SessionStatus status,
                         RedirectAttributes redirectAttributes,
                         HttpSession session) {

        if (bindingResult.hasErrors()) {
            return EMPLOYEE_EDIT_VIEW;
        }

        employeeHelper.updateEmployee(employeeCmd.getEmployee());
        status.setComplete();

        redirectAttributes.addFlashAttribute("message", messageSourceAccessor.getMessage("employee.update.success.message"));

        userHelper.setActiveUser(employeeCmd.getEmployee(), session);

        return redirectTo(HOME_URL);
    }

    @GetMapping(value = "/edit")
    public String showEditForm(@RequestParam(value = "employeeId", defaultValue = "0") int id,
                               ModelMap model,
                               HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);
        employeeHelper.populateModelForEdit(model, id);

        return EMPLOYEE_EDIT_VIEW;
    }

    @PostMapping(value = "/employee", params = "action_details_update")
    public String detailsUpdate(@Valid @ModelAttribute(EMPLOYEE_CMD) EmployeeCmd employeeCmd,
                                BindingResult bindingResult,
                                SessionStatus status,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        if (bindingResult.hasErrors()) {
            return EMPLOYEE_EDIT_VIEW;
        }

        employeeHelper.updateEmployee(employeeCmd.getEmployee());
        status.setComplete();

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("employee.update.success.message"));

        if (UtilEmployeeType.isEmployeeSupportStaff(employeeCmd.getEmployee().getEmployeeTypes())) {
            return redirectTo(LIST_DRIVER_URL);
        } else {
            return redirectTo(LIST_EMPLOYEE_URL);
        }
    }

    @GetMapping(value = "/details")
    @ResponseBody
    public ResponseEntity<Employee> details(@RequestParam(value = "employeeId", defaultValue = "0") int employeeId, HttpSession session) {
        Employee employee = employeeHelper.findByIdAndStatus(employeeId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping(value = "/employee", params = "action_remove")
    public String remove(@RequestParam(value = "employeeId", defaultValue = "0") int id,
                         HttpSession session,
                         SessionStatus status,
                         RedirectAttributes redirectAttributes) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        Employee employee = employeeHelper.findByIdAndStatus(id);

        if (employeeHelper.isRemovable(employee)) {
            redirectAttributes.addFlashAttribute(MESSAGE, messageSourceAccessor.getMessage("employee.remove.success.message"));
            status.setComplete();

            if (UtilEmployeeType.isEmployeeSupportStaff(employee.getEmployeeTypes())) {
                employeeHelper.removeSupportStaff(employee);

                return redirectTo(LIST_DRIVER_URL);
            } else {
                employeeHelper.removeEmployee(employee);

                return redirectTo(LIST_EMPLOYEE_URL);
            }
        }

        status.setComplete();

        redirectAttributes.addFlashAttribute(DECLINE_MESSAGE, messageSourceAccessor.getMessage("employee.remove.decline.message"));

        if (UtilEmployeeType.isEmployeeSupportStaff(employee.getEmployeeTypes())) {
            return redirectTo(LIST_DRIVER_URL);
        } else {
            return redirectTo(LIST_EMPLOYEE_URL);
        }
    }

    @PostMapping(value = "/employee", params = "action_approve")
    public String approve(@RequestParam(value = "employeeId", defaultValue = "0") int id,
                          HttpSession session,
                          SessionStatus status) {

        authorization.checkAccess(session, EmployeeType.SUPER_ADMIN);

        employeeHelper.approveEmployee(id);

        status.setComplete();

        return redirectTo(LIST_PENDING_EMPLOYEE);
    }

    @PostMapping(value = "/employee", params = "action_decline")
    public String decline(@RequestParam(value = "employeeId", defaultValue = "0") int id,
                          HttpSession session,
                          SessionStatus status) {

        authorization.checkAccess(session, EmployeeType.SUPER_ADMIN);

        employeeHelper.declineEmployee(id);

        status.setComplete();

        return redirectTo(LIST_PENDING_EMPLOYEE);
    }

    @PostMapping(value = "/employee", params = "action_back")
    public String back(@ModelAttribute(value = EMPLOYEE_CMD, binding = false) EmployeeCmd employeeCmd, SessionStatus status) {
        status.setComplete();

        return redirectTo(employeeCmd.getBackLink());
    }

}