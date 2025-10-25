package net.transpo.spring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author shoebakib
 * @since 3/20/24
 */
@Entity
@Table(name = "route", uniqueConstraints = @UniqueConstraint(name = "uk_route_name", columnNames = {"name"}))
@NamedQuery(name = "route.findAll",
        query = "FROM Route")
@NamedNativeQuery(name = "route.findLocationsById",
        query = "SELECT rl.location_id FROM route_location as rl WHERE rl.route_id = :routeId")
public class Route extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routeId")
    @SequenceGenerator(name = "routeId", sequenceName = "route_id_sequence", allocationSize = 1)
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    @ManyToMany
    @JoinTable(name = "route_location",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Set<Location> locations;

    @OneToMany(mappedBy = "route")
    private Set<Car> cars;

    public Route() {
        cars = new HashSet<>();
        locations = new LinkedHashSet<>();
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

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;

        return id == route.id && Objects.equals(name, route.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}