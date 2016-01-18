package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class UserMealController {

    @Autowired
    UserMealRestController umrc;

    @RequestMapping(value = "/meals/create", method = RequestMethod.GET)
    public ModelAndView actionMealsCreate() {
        return new ModelAndView("mealEdit", "meal", new UserMeal(LocalDateTime.now(), "", 1000));
    }

    @RequestMapping(value = {"/meals", "/meals/read"}, method = RequestMethod.GET)
    public String actionMealsRead(Model model) {
        model.addAttribute("mealList", umrc.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals/update/{id}", method = RequestMethod.GET)
    public ModelAndView actionMealsUpdateId(@PathVariable int id) {
        return new ModelAndView("mealEdit", "meal", umrc.get(id));
    }

    @RequestMapping(value = "/meals/delete/{id}", method = RequestMethod.GET)
    public String actionMealsDeleteId(@PathVariable int id) {
        umrc.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String actionMealsSave(@ModelAttribute("meal") UserMeal userMeal) {
        if (userMeal.isNew()) {
            umrc.create(userMeal);
        } else {
            umrc.update(userMeal);
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public ModelAndView actionMealsFilter(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String startTime, @RequestParam String endTime) {
        LocalDate startDt = TimeUtil.parseLocalDate(startDate, TimeUtil.MIN_DATE);
        LocalDate endDt = TimeUtil.parseLocalDate(endDate, TimeUtil.MAX_DATE);
        LocalTime startTm = TimeUtil.parseLocalTime(startTime, LocalTime.MIN);
        LocalTime endTm = TimeUtil.parseLocalTime(endTime, LocalTime.MAX);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("mealList");
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("startTime", startTime);
        mav.addObject("endTime", endTime);
        mav.addObject("mealList", umrc.getBetween(startDt, startTm, endDt, endTm));
        return mav;
    }
}
