package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemUserRepository {

    private static final AtomicInteger USER_ID = new AtomicInteger(0);
    private final Map<Integer, User> users = new HashMap<>();

    public MemUserRepository() throws UserConstraintViolation {
        save(User.of("root", "12345"));
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public User save(User user) throws UserConstraintViolation {
        User rsl;
        if (user.getId() == 0) {
            if (findUserByName(user.getUsername()) != null) {
                throw new UserConstraintViolation("A user with the same name already exists!");
            }
            user.setId(USER_ID.incrementAndGet());
            rsl = add(user);
        } else {
            rsl = replace(user);
        }
        return rsl;
    }

    private User add(User user) {
        return users.putIfAbsent(user.getId(), user);
    }

    private User replace(User user) {
        return users.replace(user.getId(), user);
    }

    public User findUserByName(String name) {
        User rsl = null;
        for (User value : users.values()) {
            if (value.getUsername().equalsIgnoreCase(name)) {
                rsl = value;
                break;
            }
        }
        return rsl;
    }
}
