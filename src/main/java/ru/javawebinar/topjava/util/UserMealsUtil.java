package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
        Map<LocalDate, Integer> caloriesDays = new HashMap<LocalDate, Integer>();
        List<UserMealWithExceed> mealExceedList = new ArrayList<UserMealWithExceed>();

        for (UserMeal meal: mealList) {
            LocalDate day = meal.getDateTime().toLocalDate();

            if (caloriesDays.containsKey(day)) {
                caloriesDays.put(day, caloriesDays.get(day) + meal.getCalories());
            } else {
                caloriesDays.put(day, meal.getCalories());
            }
        }

        for (UserMeal meal: mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDate day = meal.getDateTime().toLocalDate();
                boolean exceed = false;

                if (caloriesDays.containsKey(day)) {
                    if (caloriesDays.get(day) > caloriesPerDay) {
                        exceed = true;
                    }
                }
                mealExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
            }
        }

        return mealExceedList;
    }
}
