package net.therap.spring.service;

import net.therap.spring.entity.Assignment;
import net.therap.spring.entity.Car;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Repository
public class AssignmentService {

    @PersistenceContext
    private EntityManager em;

    public List<Assignment> findAllByCarId(int carId) {
        return em.createNamedQuery("Assignment.findAllByCarId", Assignment.class)
                .setParameter("carId", carId)
                .getResultList();
    }

    public Car findCarByEmployeeId(int employeeId) {
        try {
            return findByEmployeeId(employeeId).getCar();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public boolean isAssignInCar(int employeeId) {
        String countSQL = "select COUNT(a) FROM Assignment a" +
                " WHERE a.employee.id = :employeeId";

        return em.createQuery(countSQL, Long.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult() > 0L;
    }

    @Transactional
    public Assignment saveOrUpdate(Assignment assignment) {
        if (assignment.isNew()) {
            em.persist(assignment);
        } else {
            assignment = em.merge(assignment);
        }

        return assignment;
    }

    public Assignment findByEmployeeId(int employeeId) {
        return em.createNamedQuery("Assignment.findEmployeeById", Assignment.class)
                .setParameter("employeeId", employeeId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void remove(int id) {
        Assignment assignment = em.find(Assignment.class, id);

        em.remove(assignment);
    }

    @Transactional
    public void removeByCarId(int carId) {
        List<Assignment> assignments = em.createNamedQuery("Assignment.findAllByCarId", Assignment.class)
                .setParameter("carId", carId)
                .getResultList();

        for (Assignment assignment : assignments) {
            em.remove(assignment);
        }
    }

}
