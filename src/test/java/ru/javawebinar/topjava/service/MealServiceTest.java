package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(100002, 100000);
        assertMatch(meal, MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(100002, 100000);
        assertMatch(service.getAll(100000).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), MEAL_5, MEAL_3);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> all = service.getBetweenDateTimes(LocalDateTime.of(2011, 05, 16, 9, 36, 38), LocalDateTime.of(2011, 05, 16, 15, 36, 38), 100000).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        assertMatch(all, MEAL_3,  MEAL_1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(100000).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        assertMatch(all, MEAL_5,  MEAL_3,  MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_5);
        updated.setCalories(777);
        updated.setDescription("Ужин 5");
        updated.setDateTime(LocalDateTime.of(2011, 05, 16, 19, 36, 59));
        service.update(updated, 100000);
        assertMatch(service.get(100006, 100000), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2017, 05, 16, 18, 36, 38), "Ужин", 900);
        service.create(newMeal, 100000);
        assertMatch(service.getAll(100000), newMeal, MEAL_5,  MEAL_3,  MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, 100000);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() throws Exception {
        service.create(new Meal(LocalDateTime.of(2011, 05, 16, 19, 36, 38), "Ужин Дубликат", 900), 100000);
    }
}