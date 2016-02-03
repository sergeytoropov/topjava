package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = UserMealAjaxController.AJAX_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMealAjaxController extends AbstractUserMealController {
    public static final String AJAX_URL = "/ajax/meals";

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }


    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> filter(
            @RequestParam(value = "startDate") String strStartDate,
            @RequestParam(value = "endDate") String strEndDate,
            @RequestParam(value = "startTime") String strStartTime,
            @RequestParam(value = "endTime") String strEndTime
    ) {
        LocalDate startDate = TimeUtil.parseLocalDate(strStartDate, TimeUtil.MIN_DATE);
        LocalDate endDate = TimeUtil.parseLocalDate(strEndDate, TimeUtil.MAX_DATE);
        LocalTime startTime = TimeUtil.parseLocalTime(strStartTime, LocalTime.MIN);
        LocalTime endTime = TimeUtil.parseLocalTime(strEndTime, LocalTime.MAX);
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam(value = "dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") int calories) {

        UserMeal userMeal = new UserMeal(dateTime, description, calories);
        super.create(userMeal);
    }
}
