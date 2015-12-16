package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class UserUtil {
    private static final List<User> USER_LIST = Arrays.asList(
            new User(0, "Sergey Toropov", "sergeytoropov@rambler.ru", "sergey", 1500, true, EnumSet.of(Role.ROLE_ADMIN)),
            new User(1, "Vladimir Nikolaev", "vladimirnikolaev@rambler.ru", "vladimir", 2500, true, EnumSet.of(Role.ROLE_USER)),
            new User(2, "Michail Sidorov", "michailsidorov@rambler.ru", "michail", 2000, false, EnumSet.of(Role.ROLE_ADMIN)),
            new User(3, "Andrey Ivanov", "andreyivanov@rambler.ru", "andrey", 2100, false, EnumSet.of(Role.ROLE_USER)),
            new User(4, "Nikolay Boldin", "nikolayboldin@rambler.ru", "nikolay", 1700, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER))
    );

    private static final List<User> RUS_USER_LIST = Arrays.asList(
            new User(0, "Сергей Торопов", "sergeytoropov@rambler.ru", "sergey", 1500, true, EnumSet.of(Role.ROLE_ADMIN)),
            new User(1, "Владимир Николаев", "vladimirnikolaev@rambler.ru", "vladimir", 2500, true, EnumSet.of(Role.ROLE_USER)),
            new User(2, "Михаил Сидоров", "michailsidorov@rambler.ru", "michail", 2000, false, EnumSet.of(Role.ROLE_ADMIN)),
            new User(3, "Андрей Иванов", "andreyivanov@rambler.ru", "andrey", 2100, false, EnumSet.of(Role.ROLE_USER)),
            new User(4, "Николай Болдин", "nikolayboldin@rambler.ru", "nikolay", 1700, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER))
    );

    public static List<User> getUsers() {
        return new ArrayList<>(USER_LIST);
    }

    public static List<User> getRusUsers() {
        return new ArrayList<>(RUS_USER_LIST);
    }

    public static String getName(int id) {
        String name = "";
        for (User user: USER_LIST) {
            if (user.getId() == id) {
                name = user.getName();
                break;
            }
        }
        return name;
    }
}
