package net.transpo.spring.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author shoebakib
 * @since 3/25/24
 */
@Entity
@Table(name = "assignment", uniqueConstraints = @UniqueConstraint(name = "uk_assignment_employee", columnNames = "employee_id"))
@NamedQueries({
        @NamedQuery(name = "Assignment.findAllByCarId",
                query = "FROM Assignment WHERE car.id = :carId"),
        @NamedQuery(name = "Assignment.findEmployeeById",
                query = "FROM Assignment WHERE employee.id = :employeeId")
})
public class Assignment extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignmentId")
    @SequenceGenerator(name = "assignmentId", sequenceName = "assignment_id_sequence", allocationSize = 1)
    private int id;

    @ManyToOne(optional = false)
    private Car car;

    @OneToOne(optional = false)
    private Employee employee;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment that = (Assignment) o;

        return id == that.id && Objects.equals(car, that.car) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, employee);
    }

}
