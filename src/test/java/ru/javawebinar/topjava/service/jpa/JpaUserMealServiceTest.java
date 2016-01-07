package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class JpaUserMealServiceTest extends UserMealServiceTest {
    public JpaUserMealServiceTest() {
        super(LoggerWrapper.get(JpaUserMealServiceTest.class));
    }
}
