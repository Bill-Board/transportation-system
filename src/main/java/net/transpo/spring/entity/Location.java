package net.transpo.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * @author shoebakib
 * @since 3/20/24
 */
@Entity
@Table(name = "location", uniqueConstraints = {
        @UniqueConstraint(name = "uk_location_name", columnNames = "name"),
        @UniqueConstraint(name = "uk_location_image", columnNames = "image_id")
})
@NamedQueries({
        @NamedQuery(name = "location.findAll",
                query = "FROM Location"),
        @NamedQuery(name = "location.findByName",
                query = "FROM Location l" +
                        " WHERE l.name = :name")
})
public class Location extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationId")
    @SequenceGenerator(name = "locationId", sequenceName = "location_id_sequence", allocationSize = 1)
    private int id;

    @NotEmpty
    private String name;

    @JsonIgnore
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "image_id")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Attachment image;

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

    public Attachment getImage() {
        return image;
    }

    public void setImage(Attachment image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;

        return id == location.id && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}