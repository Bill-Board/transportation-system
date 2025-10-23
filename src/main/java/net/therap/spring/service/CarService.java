package net.therap.spring.service;

import net.therap.spring.entity.Car;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static net.therap.spring.constant.Constants.STATUS_ACTIVE;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Repository
public class CarService {

    @PersistenceContext
    private EntityManager em;

    public List<Car> findAll() {
        return em.createNamedQuery("car.findAllByStatus", Car.class)
                .setParameter("status", STATUS_ACTIVE)
                .getResultList();
    }

    @Transactional
    public Car saveOrUpdate(Car car) {
        if (car.isNew()) {
            em.persist(car);
        } else {
            car = em.merge(car);
        }

        return car;
    }

    public Car findByIdAndStatus(int id) {
        return em.createNamedQuery("car.findByIdAndStatus", Car.class)
                .setParameter("id", id)
                .setParameter("status", STATUS_ACTIVE)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Car findByDriverIdAndStatus(int id) {
        return em.createNamedQuery("car.findByDriverIdAndStatus", Car.class)
                .setParameter("id", id)
                .setParameter("status", STATUS_ACTIVE)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public boolean isExistByRegistrationNo(String registrationNo) {
        String countSQL = "SELECT COUNT(c) FROM Car c " +
                " WHERE c.registrationNo = :registrationNo";

        return em.createQuery(countSQL, Long.class)
                .setParameter("registrationNo", registrationNo)
                .getSingleResult() > 0L;
    }

    public boolean isExistByName(String name) {
        String countSQL = "SELECT COUNT(c) FROM Car c " +
                " WHERE c.name = :name";

        return em.createQuery(countSQL, Long.class)
                .setParameter("name", name)
                .getSingleResult() > 0L;
    }

}
