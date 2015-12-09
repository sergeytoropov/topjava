package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        request.setAttribute("columnNameDate", "Дата");
        request.setAttribute("columnNameDesc", "Описание");
        request.setAttribute("columnNameCalories", "Калории");

        request.setAttribute("itemList", UserMealsUtil.getMealsWithExceeded(2000));

        RequestDispatcher dispatcher = request.getRequestDispatcher("mealList.jsp");
        dispatcher.forward(request, response);
    }
}
