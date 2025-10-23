package net.therap.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.therap.spring.helper.HashCodeHelper;
import net.therap.spring.helper.SaltGeneratorHelper;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * @author shoebakib
 * @since 3/20/24
 */
@Entity
@Table(name = "auth_info", uniqueConstraints = {
        @UniqueConstraint(name = "uk_auth_info_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_auth_info_employee", columnNames = "employee_id")
})
@NamedQueries({
        @NamedQuery(name = "Authentication.findByEmailAndPassword",
                query = "FROM AuthenticationInfo " +
                        " WHERE email = :email AND hasedPassword = :hasedPassword"),
        @NamedQuery(name = "Authentication.findByIDAndEmail",
                query = "FROM AuthenticationInfo " +
                        " WHERE id = :id AND email = :email"),
        @NamedQuery(name = "Authentication.findByEmail",
                query = "FROM AuthenticationInfo " +
                        " WHERE email = :email")
})
public class AuthenticationInfo extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authId")
    @SequenceGenerator(name = "authId", sequenceName = "auth_id_sequence", allocationSize = 1)
    private int id;

    @Email
    @NotEmpty
    @Column(nullable = false)
    @Size(max = 100, message = "{size.email.validation}")
    private String email;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @JsonIgnore
    @Column(name = "hased_password", nullable = false)
    private String hasedPassword;

    @JsonIgnore
    @Column(nullable = false)
    private String salt;

    @Transient
    @Size(min = 8, message = "{size.password.validation}")
    private String password;

    @Transient
    private String confirmPassword;

    @PrePersist
    public void generateHasedPassword() {
        if (nonNull(password)) {
            this.salt = SaltGeneratorHelper.getSalt();

            this.hasedPassword = HashCodeHelper.generateHash(this.password + this.salt);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getHasedPassword() {
        return hasedPassword;
    }

    public void setHasedPassword(String hashcode) {
        this.hasedPassword = hashcode;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationInfo)) return false;
        AuthenticationInfo that = (AuthenticationInfo) o;

        return id == that.id && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

}