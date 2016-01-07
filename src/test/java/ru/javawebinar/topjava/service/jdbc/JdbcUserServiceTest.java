package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class JdbcUserServiceTest extends UserMealServiceTest {
    public JdbcUserServiceTest() {
        super(LoggerWrapper.get(JdbcUserServiceTest.class));
    }
}
