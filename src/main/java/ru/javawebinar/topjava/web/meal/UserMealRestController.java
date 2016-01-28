package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {
    static final String REST_URL = "/rest/meals";

    @RequestMapping(value = "create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> actionCreate(@RequestBody UserMeal userMeal) {
        final UserMeal created = super.create(userMeal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/create/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "read", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> actionRead() {
        return super.getAll();
    }

    @RequestMapping(value = "read/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal actionReadId(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void actionUpdate(@RequestBody UserMeal userMeal, @PathVariable("id") int id) {
        super.create(userMeal);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void actionUpdateId(@RequestBody UserMeal userMeal, @PathVariable("id") int id) {
        super.update(userMeal, id);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void actionDeleteId(@PathVariable("id") int id) {
        super.delete(id);
    }

    // Date {YYYY-MM-DD} {2016-01-01}, Time {HH:MI} {01:01}
    @RequestMapping(value = "filter/{startDate}/{endDate}/{startTime}/{endTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> filter(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, @PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime) {
        LocalDate startDate2 = TimeUtil.parseLocalDate(startDate, TimeUtil.MIN_DATE);
        LocalDate endDate2 = TimeUtil.parseLocalDate(endDate, TimeUtil.MAX_DATE);
        LocalTime startTime2 = TimeUtil.parseLocalTime(startTime, LocalTime.MIN);
        LocalTime endTime2 = TimeUtil.parseLocalTime(endTime, LocalTime.MAX);
        return super.getBetween(startDate2, startTime2, endDate2, endTime2);
    }
}