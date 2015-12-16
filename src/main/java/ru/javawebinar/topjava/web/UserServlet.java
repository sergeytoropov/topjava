package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.SpringContainer;
import ru.javawebinar.topjava.util.UserUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = SpringContainer.getAppCtx();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = getId(request);
        LoggedUser loggedUser = appCtx.getBean(LoggedUser.class);
        loggedUser.setId(id);
        LOG.info("logged {} userId = " + id);
        response.sendRedirect("meals?userId=" + id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("selectUser {}");
        request.setAttribute("userList", UserUtil.getUsers());
        request.getRequestDispatcher("userList.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("loggedUserId"));
        return Integer.valueOf(paramId);
    }
}
