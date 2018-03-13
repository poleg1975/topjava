package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(null, "Жора", "jora@q.ru", "qwerty", 2000, true, new HashSet<>(Arrays.asList(Role.ROLE_USER))));
        save(new User(null, "Петя", "petya1@q.ru", "ytrewq", 3000, true, new HashSet<>(Arrays.asList(Role.ROLE_USER))));
        save(new User(null, "Вася", "vasya@q.ru", "qwerty", 2000, true, new HashSet<>(Arrays.asList(Role.ROLE_USER))));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return (repository.remove(id) != null);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        /*
        List<User> userList = new ArrayList<>(repository.values());
        userList.sort((h1, h2) -> !h1.getName().equals(h2.getName()) ? h1.getName().compareTo(h2.getName()) : Integer.compare(h1.getId(), h2.getId()));
        return userList;
        */
        return new ArrayList<>(repository.values()).stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(m -> m.getEmail().equals(email)).findFirst().orElse(null);
    }

    public static void main(String[] args) {
        InMemoryUserRepositoryImpl r = new InMemoryUserRepositoryImpl();
        System.out.println(r.get(2));
        r.getAll().forEach(System.out::println);
        System.out.println(r.getByEmail("vasya@q.ru"));
        r.delete(1);
        r.getAll().forEach(System.out::println);
    }
}
