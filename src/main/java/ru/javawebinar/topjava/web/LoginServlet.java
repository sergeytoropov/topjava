package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.util.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.info("doPost");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60);
        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setId(Integer.valueOf(request.getParameter("loggedUserId")));
        session.setAttribute("loggedUser", loggedUser);
        String encodedURL = response.encodeRedirectURL("meals");
        LOG.info(encodedURL);
        response.sendRedirect(encodedURL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("doGet");
        request.setAttribute("userList", UserUtil.getUsers());
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
