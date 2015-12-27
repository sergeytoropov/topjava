package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/*
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
*/

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundException() throws Exception {
        service.get(100010, 1);
    }

    @Test
    public void testGet() throws Exception {
        System.out.println("TestGet!!");
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFoundException() throws Exception {
        service.delete(100010, 1);
    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGetBetweenDates() throws Exception {

    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFoundException() throws Exception {
        UserMeal userMeal = service.get(100010, 100000);
        service.update(userMeal, 1);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }
}