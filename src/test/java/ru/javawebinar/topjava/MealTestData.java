package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL_1 = new Meal(100002, LocalDateTime.of(2011, 05, 16, 9, 36, 38), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(100003, LocalDateTime.of(2011, 05, 16, 10, 17, 57), "Завтрак", 700);
    public static final Meal MEAL_3 = new Meal(100004, LocalDateTime.of(2011, 05, 16, 15, 36, 38), "Обед", 1000);
    public static final Meal MEAL_4 = new Meal(100005, LocalDateTime.of(2011, 05, 16, 16, 36, 38), "Обед", 1100);
    public static final Meal MEAL_5 = new Meal(100006, LocalDateTime.of(2011, 05, 16, 19, 36, 38), "Ужин", 900);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        // assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
