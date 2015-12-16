package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);

    private UserMealServiceImpl service;

    public UserMealRestController() {}

    public UserMealServiceImpl getService() {
        return service;
    }

    public void setService(UserMealServiceImpl service) {
        this.service = service;
    }

    public void save(UserMeal userMeal) {
        try {
            service.save(userMeal);
        } catch (NotFoundException e) {
            LOG.info("save - " + e);
        }
    }

    public List<UserMealWithExceed> getAll() {
        try {
            return service.getAll();
        } catch (NotFoundException e) {
            LOG.info("getAll - " + e);
        }
        return new ArrayList<>();
    }

    public List<UserMealWithExceed> filter(LocalDate beginDate, LocalDate endDate, LocalTime beginTime, LocalTime endTime) {
        try {
            return service.filter(beginDate, endDate, beginTime, endTime);
        } catch (NotFoundException e) {
            LOG.info("filter - " + e);
        }
        return new ArrayList<>();
    }

    public void delete(int id) {
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            LOG.info("delete - " + e);
        }
    }

    public UserMeal get(int id) {
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            LOG.info("get - " + e);
        }
        return new UserMeal(LocalDateTime.now(), "", 0);
    }
}
