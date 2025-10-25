package net.transpo.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static net.transpo.spring.constant.Constants.STATUS_ACTIVE;
import static net.transpo.spring.constant.Constants.STATUS_PENDING;

/**
 * @author shoebakib
 * @since 3/20/24
 */
@Entity
@Table(name = "car", uniqueConstraints = {
        @UniqueConstraint(name = "uk_car_registration_no", columnNames = "registration_no"),
        @UniqueConstraint(name = "uk_car_name", columnNames = "name"),
        @UniqueConstraint(name = "uk_car_manager", columnNames = "manager_id")
})
@NamedQueries({
        @NamedQuery(name = "car.findByIdAndStatus",
                query = "FROM Car" +
                        " WHERE id = :id AND status = :status"),
        @NamedQuery(name = "car.findAllByStatus",
                query = "FROM Car" +
                        " WHERE status= :status"),
        @NamedQuery(name = "car.findByDriverIdAndStatus",
                query = "FROM Car" +
                        " WHERE driver.id = :id AND status = :status")
})
public class Car extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carId")
    @SequenceGenerator(name = "carId", sequenceName = "car_id_sequence", allocationSize = 1)
    private int id;

    @NotEmpty
    private String name;

    @NotNull
    @Column(name = "total_seat", updatable = false, nullable = false)
    @Min(value = 4, message = "{car.min.total.seat.validation}")
    @Max(value = 50, message = "{car.max.total.seat.validation}")
    private Integer totalSeat;

    @NotNull
    @Column(name = "available_seat", nullable = false)
    @Min(value = 4, message = "{car.min.available.seat.validation}")
    @Max(value = 100, message = "{car.max.available.seat.validation}")
    private Integer availableSeat;

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name = "car_support_staff",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "support_staff_id"))
    private Employee driver;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @NotEmpty
    @Column(name = "registration_no")
    private String registrationNo;

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinTable(name = "car_route",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    private Route route;

    private String status;

    @PrePersist
    public void setStatusInPrePersist() {
        this.status = STATUS_ACTIVE;
    }

    public String getDetails() {
        return name + " , Total Seat Available " + availableSeat;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(Integer totalSeat) {
        this.totalSeat = totalSeat;
    }

    public Integer getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String carNumber) {
        this.registrationNo = carNumber;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;

        return id == car.id && Objects.equals(registrationNo, car.registrationNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNo);
    }

}
