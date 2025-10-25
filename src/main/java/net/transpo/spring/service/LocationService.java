package net.transpo.spring.service;

import net.transpo.spring.entity.Location;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Repository
public class LocationService {

    @PersistenceContext
    private EntityManager em;

    public List<Location> findAll() {
        return em.createNamedQuery("location.findAll", Location.class)
                .getResultList();
    }

    @Transactional
    public Location find(int id) {
        return em.find(Location.class, id);
    }

    @Transactional
    public Location saveOrUpdate(Location location) {
        if (location.isNew()) {
            em.persist(location);
        } else {
            location = em.merge(location);
        }

        return location;
    }

    public boolean isExistByName(String name) {
        String countSQL = "SELECT count(l) FROM Location l" +
                " WHERE l.name = :name";

        return em.createQuery(countSQL, Long.class)
                .setParameter("name", name)
                .getSingleResult() > 0L;
    }

    public Location findNameByName(String name) throws NoResultException {
        try {
            return em.createNamedQuery("location.findByName", Location.class)
                    .setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}