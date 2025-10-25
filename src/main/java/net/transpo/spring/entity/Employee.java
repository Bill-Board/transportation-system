package net.transpo.spring.entity;

import net.transpo.spring.util.UtilDateTime;
import net.transpo.spring.util.UtilEmployeeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.transpo.spring.constant.Constants.*;

/**
 * @author shoebakib
 * @since 3/18/24
 */
@Entity
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(name = "uk_employee_phone_number", columnNames = "phone_number"))
@NamedQueries({
        @NamedQuery(name = "Employee.findByTypeAndStatus",
                query = "SELECT e FROM Employee e JOIN e.employeeTypes r " +
                        " WHERE r = :role AND e.status = :status"),
        @NamedQuery(name = "Employee.findByIdAndStatus",
                query = "FROM Employee " +
                        " WHERE id = :id AND status = :status"),
        @NamedQuery(name = "Employee.findByTypeAndStatusAndDrivingStatus",
                query = "SELECT e FROM Employee e JOIN e.employeeTypes r" +
                        " WHERE r = :role AND e.status = :status AND e.drivingStatus = :driving_status"),
        @NamedQuery(name = "Employee.findByStatus",
                query = "FROM Employee " +
                        " WHERE status = :status")
})
public class Employee extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeId")
    @SequenceGenerator(name = "employeeId", sequenceName = "employee_id_sequence", allocationSize = 1)
    private int id;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    @Size(max = 100, message = "{employee.firstName.validation}")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    @Size(max = 100, message = "{employee.lastName.validation}")
    private String lastName;

    @Past
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(name = "employee_type", nullable = false)
    @JoinTable(name = "employee_types", joinColumns = @JoinColumn(name = "id"))
    private Set<EmployeeType> employeeTypes;

    @Valid
    @NotNull
    @OneToOne(mappedBy = "employee")
    private AuthenticationInfo authenticationInfo;

    @NotEmpty
    @Pattern(regexp = PHONE_NO_PATTERN, message = "{employee.phoneNumber.validation}")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String status;

    @Column(name = "driving_status")
    private String drivingStatus;

    public Employee() {
        employeeTypes = new HashSet<>();
    }

    @PrePersist
    public void setStatusInPrePersist() {
        this.status = STATUS_PENDING;

        if (UtilEmployeeType.isEmployeeSupportStaff(employeeTypes)) {
            this.drivingStatus = STATUS_INACTIVE;
        }
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return UtilDateTime.getTimePeriod(dateOfBirth);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Set<EmployeeType> getEmployeeTypes() {
        return employeeTypes;
    }

    public void setEmployeeTypes(Set<EmployeeType> employeeTypes) {
        this.employeeTypes = employeeTypes;
    }

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDrivingStatus() {
        return drivingStatus;
    }

    public void setDrivingStatus(String drivingStatus) {
        this.drivingStatus = drivingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;

        return id == employee.id && Objects.equals(phoneNumber, employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber);
    }

}
