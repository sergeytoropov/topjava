package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(UserMeal user);

    void delete(int id, int userId) throws NotFoundException;

    UserMeal get(int id, int userId) throws NotFoundException;

    List<UserMeal> getAll(int userId);

    List<UserMeal> filter(int userId, LocalDateTime begin, LocalDateTime end);

    List<UserMeal> filter(int userId, LocalDate begin, LocalDate end);

    List<UserMeal> filter(int userId, LocalTime begin, LocalTime end);

    List<UserMeal> search(int userId, String condition);
}
