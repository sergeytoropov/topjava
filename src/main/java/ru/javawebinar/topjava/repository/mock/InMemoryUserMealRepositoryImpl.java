package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        return repository.put(userMeal.getId(), userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return (get(id, userId) == null) ? null : repository.remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = repository.get(id);
        if (userMeal != null && userMeal.getUserId() == userId) {
            return userMeal;
        }
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        //TODO: список еды возвращать отсортированным по времени

        List<UserMeal> list = new ArrayList<>();

        for (Map.Entry<Integer, UserMeal> item: repository.entrySet()) {
            UserMeal userMeal = item.getValue();

            if (userMeal.getUserId() == userId) {
               list.add(userMeal);
            }
        }
        return list;
    }
}

