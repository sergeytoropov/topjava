package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by sit on 20.12.15.
 */
public class SpringJDBC {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println("Database");
            //JdbcUserMealRepositoryImpl jdbc = appCtx.getBean(JdbcUserMealRepositoryImpl.class);

            //UserMeal userMeal = new UserMeal(100020, LocalDateTime.now(), "Мой Волшебный слон", 1331);
            //jdbc.save(userMeal, 100000);
            //jdbc.delete(100019, 100000);
            JdbcUserMealRepositoryImpl jdbc = appCtx.getBean(JdbcUserMealRepositoryImpl.class);
            for (int i = 0; i < 10000; i++) {
                UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Обед " + i, 1331);
                jdbc.save(userMeal, 100000);
            }

            System.out.println("OUTPUT");
            UserMeal um = jdbc.get(100010, 100000);
            System.out.println(um);

            System.out.println("GETALL");
            Collection<UserMeal> coll = jdbc.getAll(100000);
            coll.stream().forEach(System.out::println);


            System.out.println("GETBETWEEN");
            Collection<UserMeal> coll2 = jdbc.getBetween(
                    LocalDateTime.parse("2015-12-21T14:00"), LocalDateTime.parse("2015-12-21T23:00"), 100000);
            coll2.stream().forEach(System.out::println);
            appCtx.close();
            /*
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.println(adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));
            System.out.println();

            UserMealRestController mealController = appCtx.getBean(UserMealRestController.class);
            List<UserMealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
            */
        }
    }
}
