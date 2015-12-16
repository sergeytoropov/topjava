package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.SpringContainer;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = SpringContainer.getAppCtx();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (authentication(request)) {
            UserMealRestController controller = appCtx.getBean(UserMealRestController.class);

            request.setAttribute("loggedUserId", getLoggedUserId());
            request.setAttribute("loggedUserName", getLoggedUserName());
            request.setAttribute("userId", getUserId(request));

            String formName = request.getParameter("formName");
            if ("editForm".equals(formName)) {
                String id = request.getParameter("id");
                UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));
                LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
                controller.save(userMeal);
                response.sendRedirect("meals?userId=" + getUserId(request));
            } else if ("filterForm".equals(formName)) {
                String strBeginDate = request.getParameter("beginDate");
                String strEndDate = request.getParameter("endDate");
                String strBeginTime = request.getParameter("beginTime");
                String strEndTime = request.getParameter("endTime");

                LocalDate beginDate = parseDate(LocalDate.MIN, strBeginDate);
                LocalDate endDate = parseDate(LocalDate.MAX, strEndDate);
                LocalTime beginTime = parseTime(LocalTime.MIN, strBeginTime);
                LocalTime endTime = parseTime(LocalTime.MAX, strEndTime);

                request.setAttribute("beginDate", strBeginDate);
                request.setAttribute("endDate", strEndDate);
                request.setAttribute("beginTime", strBeginTime);
                request.setAttribute("endTime", strEndTime);

                LOG.info("filter {} " + strBeginDate + " - " + strEndDate + " : " + strBeginTime + " - " + strEndTime);
                request.setAttribute("mealList", controller.filter(beginDate, endDate, beginTime, endTime));

                request.getRequestDispatcher("mealList.jsp").forward(request, response);
            } else {
                response.sendError(404, "Не найден ресурс.");
            }
        } else {
            doAuthentication(response);
        }
    }

    private LocalDate duplicateDate(LocalDate ld) {
       return LocalDate.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth());
    }

    private LocalTime duplicateTime(LocalTime lt) {
        return LocalTime.of(lt.getHour(), lt.getMinute());
    }

    private LocalDate parseDate(LocalDate ld, String date) {
        return (date != null && date.length() > 0) ? LocalDate.parse(date) : duplicateDate(ld);
    }

    private LocalTime parseTime(LocalTime lt, String time) {
        return (time != null && time.length() > 0) ? LocalTime.parse(time) : duplicateTime(lt);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (authentication(request)) {
            UserMealRestController controller = appCtx.getBean(UserMealRestController.class);

            request.setAttribute("loggedUserId", getLoggedUserId());
            request.setAttribute("loggedUserName", getLoggedUserName());
            request.setAttribute("userId", getUserId(request));

            String action = request.getParameter("action");
            if (action == null) {
                LOG.info("getAll");
                request.setAttribute("mealList", controller.getAll());
                request.getRequestDispatcher("mealList.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                int id = getId(request);
                LOG.info("Delete {}", id);
                controller.delete(id);
                request.setAttribute("mealList", controller.getAll());
                request.getRequestDispatcher("mealList.jsp").forward(request, response);
            } else {
                final UserMeal meal = action.equals("create") ?
                        new UserMeal(LocalDateTime.now(), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
            }
        } else {
            doAuthentication(response);
        }
    }

    private int toInteger(HttpServletRequest request, String param) {
        String paramId = Objects.requireNonNull(request.getParameter(param));
        return Integer.valueOf(paramId);
    }

    private int getId(HttpServletRequest request) {
        return toInteger(request, "id");
    }

    private int getUserId(HttpServletRequest request) {
        return toInteger(request, "userId");
    }

    private int getLoggedUserId() {
        return appCtx.getBean(LoggedUser.class).getId();
    }

    private String getLoggedUserName() {
        return appCtx.getBean(LoggedUser.class).getName();
    }

    private boolean authentication(HttpServletRequest request) {
        String paramName = "userId";
        if (request.getParameter(paramName) == null) {
            return false;
        }

        int userId = Integer.valueOf(Objects.requireNonNull(request.getParameter(paramName)));
        int loggedUserId = appCtx.getBean(LoggedUser.class).getId();
        return userId == loggedUserId;
    }

    private void doAuthentication(HttpServletResponse response) throws IOException {
        response.sendRedirect("users");
    }
}
