package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealServiceImpl implements UserMealService {

    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal user) {
        return null;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {

    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return null;
    }

    @Override
    public List<UserMeal> filter(int userId, LocalDateTime begin, LocalDateTime end) {
        return null;
    }

    @Override
    public List<UserMeal> filter(int userId, LocalDate begin, LocalDate end) {
        return null;
    }

    @Override
    public List<UserMeal> filter(int userId, LocalTime begin, LocalTime end) {
        return null;
    }

    @Override
    public List<UserMeal> search(int userId, String condition) {
        return null;
    }
}
