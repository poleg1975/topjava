package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryMemoryImp;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repository;

    public MealServlet() {
        this.repository = new MealRepositoryMemoryImp();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(getId(request), LocalDateTime.parse(request.getParameter("date")), request.getParameter("description"), Integer.valueOf(request.getParameter("calories")));
        repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        // List<MealWithExceed> mealsWithExceeded = MealsUtil.getWithExceeded();

        String action = request.getParameter("action") == null ? "read" : request.getParameter("action");

        switch (action) {
            case "create":
                int calories = (int)(Math.random() * 1001) + 500;
                repository.save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Description " + calories, calories));
                response.sendRedirect("meals");
                break;
            case "read":
                List<MealWithExceed> mealsWithExceeded = MealsUtil.getWithExceededCRUD(new ArrayList<>(repository.getAll()));
                request.setAttribute("meals", mealsWithExceeded);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "delete":
                repository.delete(getId(request));
                response.sendRedirect("meals");
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        return Integer.valueOf(request.getParameter("id"));
    }
}
