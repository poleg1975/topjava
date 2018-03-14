package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
        for (Meal meal : MealsUtil.MEALS) {
            save(meal, AuthorizedUser.id());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> oldMeal.getUserId() == userId ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return (repository.containsKey(id) && repository.get(id).getUserId() == userId && repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.values().stream().filter(m -> m.getId() == id).filter(m -> m.getUserId() == userId).findFirst().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
          return repository.values().stream().filter(meal -> meal.getUserId()==userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFilter(int userId, String dateStart, String dateEnd) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateStartF = LocalDate.parse(dateStart, dateFormatter);
        LocalDate dateEndF = LocalDate.parse(dateEnd, dateFormatter);

        return getAll(userId).stream().filter(d -> DateTimeUtil.isBetweenDate(d.getDate(), dateStartF, dateEndF)).collect(Collectors.toList());
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

        r.getAll(2).forEach(System.out::println);
        System.out.println();

        r.delete(3, 2);
        r.getAllTest().forEach(System.out::println);
        System.out.println();

        r.getAll(2).forEach(System.out::println);
        System.out.println();

        System.out.println(r.get(1, 2));
        System.out.println(r.get(5, 2));
        System.out.println(r.get(500, 2));
        System.out.println();
    }
}

