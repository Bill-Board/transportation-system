package net.transpo.spring.service;

import net.transpo.spring.entity.AuthenticationInfo;
import net.transpo.spring.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author shoebakib
 * @since 3/21/24
 */
@Repository
public class AuthenticationInfoService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInfoService.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveOrUpdate(AuthenticationInfo authenticationInfo) {
        if (authenticationInfo.isNew()) {
            em.persist(authenticationInfo);
        } else {
            em.merge(authenticationInfo);
        }
    }

    public AuthenticationInfo findByEmail(AuthenticationInfo authenticationInfo) {
        return em.createNamedQuery("Authentication.findByEmail", AuthenticationInfo.class)
                .setParameter("email", authenticationInfo.getEmail())
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public AuthenticationInfo findByIdAndEmailAndHash(AuthenticationInfo authenticationInfo) {
        return em.createNamedQuery("Authentication.findByIDAndEmail", AuthenticationInfo.class)
                .setParameter("id", authenticationInfo.getId())
                .setParameter("email", authenticationInfo.getEmail())
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public boolean isEmailExist(String email) {
        String countSQL = "SELECT COUNT(id) FROM AuthenticationInfo" +
                " WHERE email = :email";

        return em.createQuery(countSQL, Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0L;
    }

    public Employee findEmployee(AuthenticationInfo authenticationInfo) {
        try {
            return em.createNamedQuery("Authentication.findByEmailAndPassword", AuthenticationInfo.class)
                    .setParameter("email", authenticationInfo.getEmail())
                    .setParameter("hasedPassword", authenticationInfo.getHasedPassword())
                    .getSingleResult()
                    .getEmployee();
        } catch (NoResultException | NullPointerException ne) {
            return null;
        }
    }

}