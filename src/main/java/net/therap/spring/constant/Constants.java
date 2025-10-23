package net.therap.spring.constant;

/**
 * @author shoebakib
 * @since 4/22/24
 */
public interface Constants {

    int INCREMENT_ONE_SEAT = 1;
    int DECREMENT_ONE_SEAT = 1;

    String PENDING = "PENDING";
    String APPROVED = "APPROVED";
    String DECLINE = "DECLINE";

    String DRIVING_STATUS_INACTIVE = "INACTIVE";
    String DRIVING_STATUS_ACTIVE = "ACTIVE";

    String STATUS_PENDING = "PENDING";
    String STATUS_ACTIVE = "ACTIVE";
    String STATUS_INACTIVE = "INACTIVE";

    String REDIRECT = "redirect:";
    String SUCCESS_MESSAGE = "message";
    String DECLINE_MESSAGE = "declineMessage";

    String EMPLOYEE = "Employee";
    String IS_ADMIN = "isAdmin";
    String IS_EMPLOYEE = "isEmployee";
    String IS_DRIVER = "isDriver";
    String IS_SUPER_ADMIN = "isSuperAdmin";
    String IS_LOGGED_IN = "isLoggedIn";

    String PHONE_NO_PATTERN = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,7}$";
}
