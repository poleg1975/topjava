package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Collection<Meal> getAll();
    Meal save(Meal Meal);
    void delete(int id);
    Meal get(int id);
}
