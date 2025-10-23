package net.therap.spring.command;

import net.therap.spring.entity.Employee;
import net.therap.spring.entity.EmployeeType;
import net.therap.spring.entity.GenderType;

import javax.validation.Valid;

/**
 * @author shoebakib
 * @since 3/18/24
 */
public class EmployeeCmd extends Command {

    private static final long serialVersionUID = 1L;

    @Valid
    private Employee employee;

    private GenderType[] genderOptions;

    private EmployeeType[] typeOptions;

    public GenderType[] getGenderOptions() {
        return genderOptions;
    }

    public void setGenderOptions(GenderType[] genderOptions) {
        this.genderOptions = genderOptions;
    }

    public EmployeeType[] getTypeOptions() {
        return typeOptions;
    }

    public void setTypeOptions(EmployeeType[] typeOptions) {
        this.typeOptions = typeOptions;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
