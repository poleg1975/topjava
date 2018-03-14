package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {
    // false if not found
    boolean delete(int id, int userId) throws NotFoundException;

    // null if not found
    Meal get(int id, int userId) throws NotFoundException;

    List<Meal> getAll(int userId);

    List<Meal> getAllFilter(int userId, String dateStart, String dateEnd, String timeStart, String timeEnd);

    Meal create(Meal meal, int userId);

    void update(Meal meal, int userId) throws NotFoundException;
}