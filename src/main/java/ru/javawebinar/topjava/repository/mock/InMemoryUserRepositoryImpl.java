package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserUtil.getRusUsers().forEach(this::save);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        return repository.put(user.getId(), user);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        for (User u: repository.values()) {
            if (u.getEmail().equals(email)) {
                user = u;
                break;
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
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
}
