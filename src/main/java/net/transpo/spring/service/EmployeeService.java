package net.transpo.spring.service;

import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.EmployeeType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static net.transpo.spring.constant.Constants.*;

/**
 * @author shoebakib
 * @since 3/18/24
 */
@Repository
public class EmployeeService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Employee saveOrUpdate(Employee employee) {

        if (employee.isNew()) {
            em.persist(employee);
        } else {
            employee = em.merge(employee);
        }

        return employee;
    }

    public Employee findByIdAndStatus(int id) {
        return em.createNamedQuery("Employee.findByIdAndStatus", Employee.class)
                .setParameter("id", id)
                .setParameter("status", STATUS_ACTIVE)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Employee findPendingEmployee(int id) {
        return em.createNamedQuery("Employee.findByIdAndStatus", Employee.class)
                .setParameter("id", id)
                .setParameter("status", STATUS_PENDING)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployeeListByType(EmployeeType employeeType) {
        return em.createNamedQuery(
                        "Employee.findByTypeAndStatus", Employee.class)
                .setParameter("role", employeeType)
                .setParameter("status", STATUS_ACTIVE)
                .getResultList();
    }

    public List<Employee> findInactiveDriverList() {
        return em.createNamedQuery(
                        "Employee.findByTypeAndStatusAndDrivingStatus", Employee.class)
                .setParameter("role", EmployeeType.SUPPORT_STAFF)
                .setParameter("driving_status", DRIVING_STATUS_INACTIVE)
                .setParameter("status", STATUS_ACTIVE)
                .getResultList();
    }

    public List<Employee> findPendingEmployeeList() {
        return em.createNamedQuery(
                        "Employee.findByStatus", Employee.class)
                .setParameter("status", STATUS_PENDING)
                .getResultList();
    }

    public boolean isExistByIdAndStatus(int id) {
        String countSQL = "SELECT COUNT(e) FROM Employee e " +
                " WHERE e.id = :id AND status = :status";

        return em.createQuery(countSQL, Long.class)
                .setParameter("id", id)
                .setParameter("status", STATUS_ACTIVE)
                .getSingleResult() > 0L;
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        String countSQL = "SELECT COUNT(e) FROM Employee e" +
                " WHERE e.phoneNumber = :phoneNumber";

        return em.createQuery(countSQL, Long.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult() > 0L;
    }

}