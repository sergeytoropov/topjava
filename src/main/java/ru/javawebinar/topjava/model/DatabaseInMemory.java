package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class DatabaseInMemory implements Database {
    private static DatabaseInMemory ourInstance = new DatabaseInMemory();
    private static Sequence sq = new Sequence();
    private static Map<Integer, UserMeal> map = new HashMap<>();

    static {
        map.put(sq.nextValue(), new UserMeal(sq.value(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        map.put(sq.nextValue(), new UserMeal(sq.value(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        map.put(sq.nextValue(), new UserMeal(sq.value(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        map.put(sq.nextValue(), new UserMeal(sq.value(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        map.put(sq.nextValue(), new UserMeal(sq.value(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        map.put(sq.nextValue(), new UserMeal(sq.value(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static DatabaseInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public synchronized void create(LocalDateTime dateTime, String description, int calories) {
        int id = sq.nextValue();
        map.put(id, new UserMeal(id, dateTime, description, calories));
    }

    @Override
    public synchronized List<UserMealWithExceed> read(int calories) {
        return UserMealsUtil.getFilteredMealsWithExceeded(new ArrayList<>(map.values()), LocalTime.MIN, LocalTime.MAX, calories);
    }

    @Override
    public synchronized void update(int id, LocalDateTime dateTime, String description, int calories) {
        UserMeal meal = map.get(id);
        if (meal != null) {
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
        }
    }

    @Override
    public synchronized void delete(int id) {
        map.remove(id);
    }

    private DatabaseInMemory() {
    }
}
