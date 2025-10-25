package net.transpo.spring.util;

import net.transpo.spring.entity.EmployeeType;

import java.util.Arrays;
import java.util.Set;

/**
 * @author shoebakib
 * @since 5/8/24
 */
public class UtilEmployeeType {

    public static EmployeeType[] getGeneralEmployeeTypes() {
        return Arrays.stream(EmployeeType.values())
                .filter(et -> et != EmployeeType.SUPER_ADMIN)
                .toArray(EmployeeType[]::new);
    }

    public static boolean isEmployeeSuperAdmin(Set<EmployeeType> employeeTypes) {
        return isEmployeeThisType(employeeTypes, EmployeeType.SUPER_ADMIN);
    }

    public static boolean isEmployeeAdmin(Set<EmployeeType> employeeTypes) {
        return isEmployeeThisType(employeeTypes, EmployeeType.ADMIN);
    }

    public static boolean isEmployeeGeneralEmployee(Set<EmployeeType> employeeTypes) {
        return isEmployeeThisType(employeeTypes, EmployeeType.EMPLOYEE);
    }

    public static boolean isEmployeeSupportStaff(Set<EmployeeType> employeeTypes) {
        return isEmployeeThisType(employeeTypes, EmployeeType.SUPPORT_STAFF);
    }

    private static boolean isEmployeeThisType(Set<EmployeeType> employeeTypes, EmployeeType employeeType) {
        return employeeTypes.contains(employeeType);
    }

}
