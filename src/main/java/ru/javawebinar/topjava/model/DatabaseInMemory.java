package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class DatabaseInMemory implements Database {
    private static DatabaseInMemory ourInstance = new DatabaseInMemory();
    private static Sequence sq = new Sequence();
    private static List<UserMeal> list = new ArrayList<>();

    static {
        list.add(new UserMeal(sq.nextValue(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        list.add(new UserMeal(sq.nextValue(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        list.add(new UserMeal(sq.nextValue(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        list.add(new UserMeal(sq.nextValue(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        list.add(new UserMeal(sq.nextValue(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        list.add(new UserMeal(sq.nextValue(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static DatabaseInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public synchronized void create(LocalDateTime dateTime, String description, int calories) {
        list.add(new UserMeal(sq.nextValue(), dateTime, description, calories));
    }

    @Override
    public synchronized List<UserMealWithExceed> read(int calories) {
        return UserMealsUtil.getFilteredMealsWithExceeded(list, LocalTime.MIN, LocalTime.MAX, calories);
    }

    @Override
    public synchronized void update(int id, LocalDateTime dateTime, String description, int calories) {
        for (UserMeal meal: list) {
            if (meal.getId() == id) {
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
                break;
            }
        }
    }

    @Override
    public synchronized void delete(int id) {
        int removeIndex = Integer.MIN_VALUE;
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getId() == id) {
                removeIndex = index;
                break;
            }
        }
        if (removeIndex != Integer.MIN_VALUE) {
            list.remove(removeIndex);
        }
    }

    private DatabaseInMemory() {
    }
}
