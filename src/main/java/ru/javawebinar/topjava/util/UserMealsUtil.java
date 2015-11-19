package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Распределение калорий по дням
        Map<LocalDate, Integer> caloriesDays = mealList.stream()
                .collect(groupingBy(meal -> meal.getDateTime().toLocalDate(), summingInt(meal -> meal.getCalories())));

        /*
        caloriesDays.forEach((key, value) -> {
                System.out.println("key [" + key + "] value [" + value + "]");
        });
        */

        List<UserMealWithExceed> mealExceedList = mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        (caloriesDays.containsKey(meal.getDateTime().toLocalDate())) ? caloriesDays.get(meal.getDateTime().toLocalDate()) > caloriesPerDay : false))
                .collect(toList());

        /*
        mealExceedList.forEach(meal -> {
            System.out.println(meal);
        });
        */

        return mealExceedList;
    }
}
