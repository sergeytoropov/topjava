package ru.javawebinar.topjava.repository.mock;

import org.junit.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class InMemoryUserRepositoryImplTest {
    private static UserRepository userRepository = new InMemoryUserRepositoryImpl();

    @Test
    public void testSave() throws Exception {
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(userRepository.delete(0));
        assertFalse(userRepository.delete(0));
    }

    @Test
    public void testGet() throws Exception {
        assertNull(userRepository.get(Integer.MAX_VALUE));
        assertTrue(userRepository.get(0).getId() == 0);
    }

    @Test
    public void testGetByEmail() throws Exception {
        assertNull(userRepository.getByEmail("null"));

        String email = "sergeytoropov@rambler.ru";
        assertTrue(userRepository.getByEmail(email).getEmail().equals(email));
    }

    @Test
    public void testGetAll() throws Exception {
        userRepository = new InMemoryUserRepositoryImpl();
        for (User user: userRepository.getAll()) {
            System.out.println(user.getName());
        }
    }

    public Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        return repository.put(user.getId(), user);
    }

    public List<User> unknown() {
        class UserComparator implements Comparator<User> {
            @Override
            public int compare(User a, User b) {
                return a.getName().compareTo(b.getName());
            }
        }
        Set<User> set = new TreeSet<>(new UserComparator());
        set.addAll(repository.values());
        return new ArrayList<>(set);
    }

    @Test
    public void test() {
        System.out.println("Start test!");

        UserUtil.getRusUsers().forEach(this::save);
        for (User user: unknown()) {
            System.out.println(user.getName());
        }
        System.out.println("Stop test!");
    }
}


