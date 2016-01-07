package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
@Transactional(readOnly = true)
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    private ProxyUserMealRepository proxy;

    @Autowired
    private ProxyUserRepository proxyUser;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        userMeal.setUser(proxyUser.getOne(userId));
        if (userMeal.isNew()) {
            return proxy.save(userMeal);
        } else {
            UserMeal currentUserMeal = proxy.getOne(userMeal.getId());
            if (currentUserMeal.getUser().getId() == userId) {
                return proxy.save(userMeal);
            }
        }
/*
        } else if (proxy.getOne(userMeal.getId()).getUser().getId() == userId) {
            return proxy.save(userMeal);
        }
*/
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = proxy.getOne(id);
        if (userMeal.getUser().getId() == userId) {
            return userMeal;
        }
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAll(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findAll(userId).stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
