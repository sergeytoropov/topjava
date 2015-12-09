package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface Database {
    void create(LocalDateTime dateTime, String description, int calories);
    List<UserMealWithExceed> read(int calories);
    void update(int id, LocalDateTime dateTime, String description, int calories);
    void delete(int id);
}
