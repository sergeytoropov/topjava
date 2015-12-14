package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
            new UserMeal(0, 0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(1, 0, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(2, 0, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(3, 0, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(4, 0, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(5, 0, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final List<User> USER_LIST = Arrays.asList(
            new User(0, "Sergey Toropov", "sergeytoropov@rambler.ru", "sergey", 1500, true, EnumSet.of(Role.ROLE_ADMIN)),
            new User(1, "Vladimir Nikolaev", "vladimirnikolaev@rambler.ru", "vladimir", 2500, true, EnumSet.of(Role.ROLE_USER)),
            new User(2, "Michail Sidorov", "michailsidorov@rambler.ru", "michail", 2000, false, EnumSet.of(Role.ROLE_ADMIN)),
            new User(3, "Andrey Ivanov", "andreyivanov@rambler.ru", "andrey", 2100, false, EnumSet.of(Role.ROLE_USER)),
            new User(5, "Nikolay Boldin", "nikolayboldin@rambler.ru", "nikolay", 1700, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER))
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<UserMealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY);
        filteredMealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }

    public static List<UserMealWithExceed> getWithExceeded(Collection<UserMeal> mealList, int caloriesPerDay) {
        return getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded) {
        return new UserMealWithExceed(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories(), exceeded);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : mealList) {
            caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return mealExceeded;
    }
}
