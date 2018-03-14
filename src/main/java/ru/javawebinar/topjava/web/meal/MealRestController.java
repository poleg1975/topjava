package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAllFilter(String dateStart, String dateEnd, String timeStart, String timeEnd) {
        log.info("getAll");
        if ((!dateStart.isEmpty() && !dateEnd.isEmpty()) && (!timeStart.isEmpty() && !timeEnd.isEmpty()))
            return MealsUtil.getWithExceeded(service.getAllFilter(AuthorizedUser.id(), dateStart, dateEnd, timeStart, timeEnd), AuthorizedUser.getCaloriesPerDay());
        else
            return getAll();
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

    public boolean delete(int id) {
        return service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }
}