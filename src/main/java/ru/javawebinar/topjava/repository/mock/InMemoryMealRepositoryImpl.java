package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> oldMeal.getUserId() == AuthorizedUser.id() ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id) {
        return (repository.containsKey(id) && repository.get(id).getUserId() == AuthorizedUser.id() && repository.remove(id) != null);
    }

    @Override
    public Meal get(int id) {
        return repository.values().stream().filter(m -> m.getId() == id).filter(m -> m.getUserId() == AuthorizedUser.id()).findFirst().orElse(null);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>(repository.values());
        mealList.sort((m2, m1) -> m1.getDateTime().compareTo(m2.getDateTime()));
        return mealList.stream().filter(m -> m.getUserId() == AuthorizedUser.id()).collect(Collectors.toList());
    }

    public List<Meal> getAllTest() {
        List<Meal> mealList = new ArrayList<>(repository.values());
        mealList.sort((m2, m1) -> m1.getDateTime().compareTo(m2.getDateTime()));
        return mealList;
    }

    public static void main(String[] args) {
        InMemoryMealRepositoryImpl r = new InMemoryMealRepositoryImpl();
        r.getAllTest().forEach(System.out::println);
        System.out.println();

        r.getAll().forEach(System.out::println);
        System.out.println();

        r.delete(3);
        r.getAllTest().forEach(System.out::println);
        System.out.println();

        r.getAll().forEach(System.out::println);
        System.out.println();

        System.out.println(r.get(1));
        System.out.println(r.get(5));
        System.out.println(r.get(500));
        System.out.println();
    }
}

