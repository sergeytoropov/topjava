package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaUserServiceTest extends UserMealServiceTest {
    public DataJpaUserServiceTest() {
        super(LoggerWrapper.get(DataJpaUserServiceTest.class));
    }
}
