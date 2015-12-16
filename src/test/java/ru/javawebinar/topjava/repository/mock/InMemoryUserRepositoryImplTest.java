package ru.javawebinar.topjava.repository.mock;

import org.junit.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class InMemoryUserRepositoryImplTest {
    private static UserRepository repository = new InMemoryUserRepositoryImpl();

    @Test
    public void testSave() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(repository.delete(0));
        assertFalse(repository.delete(0));
    }

    @Test
    public void testGet() throws Exception {
        assertNull(repository.get(Integer.MAX_VALUE));
        assertTrue(repository.get(0).getId() == 0);
    }

    @Test
    public void testGetByEmail() throws Exception {
        assertNull(repository.getByEmail("null"));

        String email = "sergeytoropov@rambler.ru";
        assertTrue(repository.getByEmail(email).getEmail().equals(email));
    }

    @Test
    public void testGetAll() throws Exception {
        repository = new InMemoryUserRepositoryImpl();
        for (User user: repository.getAll()) {
            System.out.println(user.getName());
        }
    }
}


