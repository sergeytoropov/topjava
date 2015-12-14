package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal {
    protected Integer id;

    protected Integer userId;

    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this(id, null, dateTime, description, calories);
    }

    public UserMeal(Integer id, Integer userId, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", userId=" + userId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
