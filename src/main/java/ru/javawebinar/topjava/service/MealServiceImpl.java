package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getAllFilter(int userId, String dateStart, String dateEnd, String timeStart, String timeEnd) {
        return repository.getAllFilter(userId, dateStart, dateEnd, timeStart, timeEnd);
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}