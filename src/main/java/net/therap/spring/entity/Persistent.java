package net.therap.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author shoebakib
 * @since 3/7/24
 */

@MappedSuperclass
public abstract class Persistent implements Serializable {

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updated;

    @Version
    @JsonIgnore
    private int version;

    public Persistent() {
    }

    public abstract int getId();

    public boolean isNew() {
        return getId() == 0;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime createdOn) {
        this.created = createdOn;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updatedOn) {
        this.updated = updatedOn;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}