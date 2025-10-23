package net.therap.spring.service;

import net.therap.spring.entity.CarRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static net.therap.spring.constant.Constants.PENDING;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Repository
public class CarRequestService {

    @PersistenceContext
    private EntityManager em;

    public CarRequest findById(int id) {
        return em.find(CarRequest.class, id);
    }

    public List<CarRequest> findAll() {
        return em.createNamedQuery("request.findAll", CarRequest.class)
                .getResultList();
    }

    @Transactional
    public CarRequest saveOrUpdate(CarRequest request) {
        if (request.isNew()) {
            em.persist(request);

        } else {
            request = em.merge(request);
        }

        return request;
    }

    @Transactional
    public List<CarRequest> findAllByEmployeeId(int employeeId) {
        return em.createNamedQuery("request.findAllByEmployeeId", CarRequest.class)
                .setParameter("id", employeeId)
                .getResultList();
    }

    public boolean isPendingARequest(int employeeId) {
        String countSQL = "SELECT COUNT(id) FROM CarRequest" +
                " WHERE employee.id = :id AND status = :status";

        return em.createQuery(countSQL, Long.class)
                .setParameter("id", employeeId)
                .setParameter("status", PENDING)
                .getSingleResult() > 0L;
    }

}