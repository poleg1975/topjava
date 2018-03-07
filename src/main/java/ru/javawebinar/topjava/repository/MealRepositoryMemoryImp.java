package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealRepositoryMemoryImp implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public void save(Meal meal) {
        repository.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }
}
