package net.therap.spring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static net.therap.spring.constant.Constants.PENDING;

/**
 * @author shoebakib
 * @since 3/20/24
 */
@Entity
@Table(name = "request")
@NamedQueries({
        @NamedQuery(name = "request.findAllByEmployeeId",
                query = "FROM CarRequest" +
                        " WHERE employee.id = :id ORDER BY id DESC"),
        @NamedQuery(name = "request.findAll",
                query = "FROM CarRequest ORDER BY status DESC")
})
public class CarRequest extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requestId")
    @SequenceGenerator(name = "requestId", sequenceName = "request_id_sequence", allocationSize = 1)
    private int id;

    @ManyToOne(optional = false)
    private Employee employee;

    private String status;

    @NotNull
    @ManyToOne(optional = false)
    private Route route;

    @NotNull
    @ManyToOne(optional = false)
    private Car car;

    private String comment;

    public CarRequest() {
        this.status = PENDING;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarRequest)) return false;
        CarRequest request = (CarRequest) o;

        return id == request.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
