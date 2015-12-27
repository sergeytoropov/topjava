package ru.javawebinar.topjava.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal extends BaseEntity {
    //protected Integer userId;

    protected LocalDateTime dateTime;

    protected String description;

    protected int calories;

    public UserMeal() {}

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    /*
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    */

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /*
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    */

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime.toLocalDateTime();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                //"userId=" + userId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
