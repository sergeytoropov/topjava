package ru.javawebinar.topjava.web.meal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;

public class UserMealControllerTest extends AbstractControllerTest {
    private UserMealWithExceed umwe;

    @Before
    public void setUp() {
        umwe = UserMealsUtil.getWithExceeded(USER_MEALS).get(0);
    }

    @Test
    public void testUserMealList() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.ALL))
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(6)))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("id", is(umwe.getId())),
                                hasProperty("exceed", is(umwe.isExceed())),
                                hasProperty("dateTime", is(umwe.getDateTime())),
                                hasProperty("description", is(umwe.getDescription())),
                                hasProperty("calories", is(umwe.getCalories()))
                        )
                )));
    }
}
