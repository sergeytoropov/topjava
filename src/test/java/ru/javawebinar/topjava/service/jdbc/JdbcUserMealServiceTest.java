package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class JdbcUserMealServiceTest extends UserMealServiceTest {
    public JdbcUserMealServiceTest() {
        super(LoggerWrapper.get(JdbcUserMealServiceTest.class));
    }
}
