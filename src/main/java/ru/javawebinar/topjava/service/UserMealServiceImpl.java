package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealServiceImpl implements UserMealService {

    private UserMealRepository repository;

    private LoggedUser loggedUser;

    public UserMealServiceImpl() {}

    public UserMealRepository getRepository() {
        return repository;
    }

    public void setRepository(UserMealRepository repository) {
        this.repository = repository;
    }

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    @Override
    public UserMeal save(UserMeal user) throws NotFoundException {
        user.setUserId(loggedUser.getId());
        return ExceptionUtil.check(repository.save(user), "User not a save " + user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id, loggedUser.getId()), "User not a delete id = " + id);
    }

    @Override
    public UserMeal get(int id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id, loggedUser.getId()), "User not found id = " + id);
    }

    private List<UserMeal> internalGetAll() throws NotFoundException {
        int loggedUserId = loggedUser.getId();
        return ExceptionUtil.check(repository.getAll(loggedUserId), "loggedUserId=" + loggedUserId);
    }

    @Override
    public List<UserMealWithExceed> getAll() throws NotFoundException {
        return UserMealsUtil.getWithExceeded(internalGetAll(), loggedUser.getCaloriesPerDay());
    }

    @Override
    public List<UserMealWithExceed> filter(LocalDate beginDate, LocalDate endDate, LocalTime beginTime, LocalTime endTime) throws NotFoundException {
        return UserMealsUtil.getFilteredWithExceeded(internalGetAll(), beginDate, endDate, beginTime, endTime, loggedUser.getCaloriesPerDay());
    }

    @Override
    public List<UserMealWithExceed> search(String condition) throws NotFoundException {
        return null;
    }
}
