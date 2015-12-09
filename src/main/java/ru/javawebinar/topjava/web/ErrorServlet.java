package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sit on 08.12.15.
 */
public class ErrorServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to error");

//        request.getRequestDispatcher("/userList.jsp").forward(request, response);
        response.sendRedirect("error.jsp");
    }
}
