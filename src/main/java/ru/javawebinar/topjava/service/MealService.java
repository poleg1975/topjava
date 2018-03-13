package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    Meal save(Meal meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    List<Meal> getAll();
}