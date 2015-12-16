package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    protected int id;
    protected final int caloriesPerDay = UserMealsUtil.DEFAULT_CALORIES_PER_DAY;

    public LoggedUser() {
        unlogged();
    }

    public boolean isLogged() {
        return this.id != Integer.MIN_VALUE;
    }

    public void unlogged() {
        this.id = Integer.MIN_VALUE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public String getName() {
        return UserUtil.getName(id);
    }
}
