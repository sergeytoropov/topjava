package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
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
    UserMeal save(UserMeal user) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    UserMeal get(int id) throws NotFoundException;

    List<UserMealWithExceed> getAll() throws NotFoundException;

    List<UserMealWithExceed> filter(LocalDate beginDate, LocalDate endDate, LocalTime beginTime, LocalTime endTime) throws NotFoundException;

    List<UserMealWithExceed> search(String condition) throws NotFoundException;
}
