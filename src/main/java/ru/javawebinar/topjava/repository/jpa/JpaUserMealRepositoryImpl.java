package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

/*
    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }
*/

    @PersistenceContext
    private EntityManager em;

    private void setUser(UserMeal userMeal, int userId) {
        userMeal.setUser(em.getReference(User.class, userId));
    }

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        UserMeal answer = null;

        if (userMeal != null) {
            if (userMeal.isNew()) {
                setUser(userMeal, userId);
                em.persist(userMeal);
                answer = userMeal;
            } else {
                if (get(userMeal.getId(), userId) != null) {
                    setUser(userMeal, userId);
                    answer = em.merge(userMeal);
                }
            }
        }
        return answer;

        /*
        final int EXIT = 0;
        final int INIT = 1;
        final int NEW = 2;
        final int UPDATE = 3;
        final int ACCESS_DENIED = 4;
        int state = INIT;

        UserMeal currentUserMeal = null;
        boolean continued = true;
        while (continued) {
            switch (state) {
                case EXIT:
                    continued = false;
                    break;

                case INIT:
                    state = UPDATE;
                    if (userMeal == null) {
                        state = ACCESS_DENIED;
                    } else if (userMeal.isNew()) {
                        state = NEW;
                    }
                    break;

                case NEW:
                    state = EXIT;

                    setUser(userMeal, userId);
                    em.persist(userMeal);
                    currentUserMeal = userMeal;
                    break;

                case UPDATE:
                    state = ACCESS_DENIED;
                    if (get(userMeal.getId(), userId) != null) {
                        state = EXIT;

                        setUser(userMeal, userId);
                        currentUserMeal = em.merge(userMeal);
                    }
                    break;

                case ACCESS_DENIED:
                    state = EXIT;

                    currentUserMeal = null;
                    break;
            }
        }
        return currentUserMeal;
        */
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        try {
            return em.createNamedQuery(UserMeal.GET, UserMeal.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.DATE_SORTED, UserMeal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}