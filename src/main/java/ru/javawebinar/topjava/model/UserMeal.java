package ru.javawebinar.topjava.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */

@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET, query = "SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT um FROM UserMeal um WHERE um.user.id=:userId ORDER BY um.dateTime DESC"),
        @NamedQuery(name = UserMeal.DATE_SORTED,
                query = "SELECT um FROM UserMeal um WHERE um.dateTime BETWEEN :startDate AND :endDate AND um.user.id=:userId ORDER BY um.dateTime DESC"),
})
@Entity
@Table(name = "MEALS")
public class UserMeal extends BaseEntity implements Serializable {

    public static final String DELETE = "UserMeal.delete";
    public static final String GET = "UserMeal.get";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String DATE_SORTED = "UserMeal.getDateSorted";

    @Column(name = "DATE_TIME")
    protected LocalDateTime dateTime;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "CALORIES")
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserMeal() {}

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
//                ", user_id=" + user.getId() +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}