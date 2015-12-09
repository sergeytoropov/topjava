package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Database;
import ru.javawebinar.topjava.model.DatabaseInMemory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CRUDMealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);
    private static final Database db = DatabaseInMemory.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("Servlet CRUDMeal - function doPost");

        request.setCharacterEncoding("utf-8");
        try {
            String datetime = request.getParameter("inputDatetime");
            String description = request.getParameter("inputDescription");
            int calories = Integer.valueOf(request.getParameter("inputCalories"));
            int id = Integer.valueOf(request.getParameter("inputMealId"));

            if (id == -1) {
               db.create(LocalDateTime.parse(datetime), description, calories);
            } else {
               db.update(id, LocalDateTime.parse(datetime), description, calories);
            }
            redirectMealList(response);
            return;
        } catch (NumberFormatException e) {
            LOG.debug("Некорректные данные в форме поле id или calories");
        }
        redirectError(response);
    }

    protected int getId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id != null) {
            try {
                return Integer.valueOf(id);
            } catch (NumberFormatException e) {
                LOG.debug("Ошибочный идентификатор id = [" + id + "]");
            }
        } else {
            LOG.debug("Идентификатор id установлен в NULL");
        }
        return Integer.MIN_VALUE;
    }

    protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward("createUpdateMeal.jsp", request, response);
    }

    protected void forwardMealList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward("crud?action=read", request, response);
    }

    protected void redirect(String path, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(path);
    }

    protected void redirectError(HttpServletResponse response) throws ServletException, IOException {
        redirect("error.jsp", response);
    }

    protected void redirectMealList(HttpServletResponse response) throws ServletException, IOException {
        redirect("crud?action=read", response);
    }

    protected void doInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("headerName", "Добавить еду");
        request.setAttribute("actionName", "Добавить");
        request.setAttribute("mealId", -1);

        forward(request, response);
    }

    protected void doRead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("columnNameDate", "Дата");
        request.setAttribute("columnNameDesc", "Описание");
        request.setAttribute("columnNameCalories", "Калории");

        request.setAttribute("itemList", db.read(2000));

        forward("mealList.jsp", request, response);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        if (id != Integer.MIN_VALUE) {
            request.setAttribute("headerName", "Редактировать еду");
            request.setAttribute("actionName", "Изменить");

            request.setAttribute("datetime", request.getParameter("datetime"));
            request.setAttribute("description", request.getParameter("description"));
            request.setAttribute("calories", request.getParameter("calories"));
            request.setAttribute("mealId", id);

            forward(request, response);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        if (id != Integer.MIN_VALUE) {
            db.delete(id);

            forwardMealList(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("Servlet CRUDMeal - function doGet");

        /*
         * Вопрос? Данный алгоритм пропускает верные строк даже если она избыточна или содержит любые другие параметры
         * например topjava/crud?action=delete&id=123&name=qwerty... и т.д.
         * данная избыточность строки запроса не влияет на действия выполняемые в программе.
         * Стоит ли такую строку считать ошибкой?
         */
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            doInsert(request, response);
        } else if ("read".equals(action)) {
            doRead(request, response);
        } else if ("update".equals(action)) {
            doUpdate(request, response);
        } else if ("delete".equals(action)) {
            doDelete(request, response);
        } else {
            LOG.debug("Неизвестная операция action = [" + action + "]");
        }
        redirectError(response);
    }
}
