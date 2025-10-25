package net.transpo.spring.service;

import net.transpo.spring.entity.Location;
import net.transpo.spring.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Repository
public class RouteService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LocationService locationService;

    public List<Route> findAll() {
        return em.createNamedQuery("route.findAll", Route.class)
                .getResultList();
    }

    public Route findById(int id) {
        return em.find(Route.class, id);
    }

    @Transactional
    public Route saveOrUpdate(Route route) {
        if (route.isNew()) {
            em.persist(route);
        } else {
            route = em.merge(route);
        }

        return route;
    }

    @SuppressWarnings("unchecked")
    public List<Location> findLocations(int routeId) {
        List<Object> locationIds = em.createNamedQuery("route.findLocationsById")
                .setParameter("routeId", routeId)
                .getResultList();

        List<Location> locations = new ArrayList<>();

        for (Object id : locationIds) {
            locations.add(locationService.find((Integer) id));
        }

        return locations;
    }

    public boolean isExistByName(String name) {
        String countSQL = "SELECT COUNT(r) FROM Route r" +
                " WHERE r.name = :name";

        return em.createQuery(countSQL, Long.class)
                .setParameter("name", name)
                .getSingleResult() > 0L;
    }

}