package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);

    private UserMealServiceImpl service;

    public UserMealRestController() {}

    public UserMealServiceImpl getService() {
        return service;
    }

    public void setService(UserMealServiceImpl service) {
        this.service = service;
    }

    public void editForm(HttpServletRequest request) {
        LOG.info("editForm");
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(userMeal.isNew() ? "Create " : "Update ", userMeal);
        service.save(userMeal);
    }

    public void filterForm(HttpServletRequest request) {
        LOG.info("filterForm");
        String strBeginDate = request.getParameter("beginDate");
        String strEndDate = request.getParameter("endDate");
        String strBeginTime = request.getParameter("beginTime");
        String strEndTime = request.getParameter("endTime");

        LocalDate beginDate = RequestUtil.parseDate(LocalDate.MIN, strBeginDate);
        LocalDate endDate = RequestUtil.parseDate(LocalDate.MAX, strEndDate);
        LocalTime beginTime = RequestUtil.parseTime(LocalTime.MIN, strBeginTime);
        LocalTime endTime = RequestUtil.parseTime(LocalTime.MAX, strEndTime);

        request.setAttribute("beginDate", strBeginDate);
        request.setAttribute("endDate", strEndDate);
        request.setAttribute("beginTime", strBeginTime);
        request.setAttribute("endTime", strEndTime);

        LOG.info("filter " + strBeginDate + " - " + strEndDate + " : " + strBeginTime + " - " + strEndTime);
        request.setAttribute("mealList", service.filter(beginDate, endDate, beginTime, endTime));
    }

    public void read(HttpServletRequest request) {
        LOG.info("read");
        request.setAttribute("mealList", service.getAll());
    }

    public void delete(HttpServletRequest request) {
        int id = RequestUtil.getId(request);
        LOG.info("Delete {}", id);
        service.delete(id);
    }

    public void create(HttpServletRequest request) {
        LOG.info("create");
        createUpdate(request, new UserMeal(LocalDateTime.now(), "", 1000));
    }

    public void update(HttpServletRequest request) {
        int id = RequestUtil.getId(request);
        LOG.info("update {}" + id);
        createUpdate(request, service.get(id));
    }

    private void createUpdate(HttpServletRequest request, UserMeal userMeal) {
        request.setAttribute("meal", userMeal);
        service.save(userMeal);
    }
}


