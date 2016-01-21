package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.UserTestData;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

public class UserMealRestControllerTest extends AbstractControllerTest {
    public static final String REST_URL = UserMealRestController.REST_URL;

    @Test
    public void testCreate() throws Exception {
        UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Мой завтрак", 777);
        ResultActions action = mockMvc
                .perform(post(REST_URL + "/create").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(userMeal)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        userMeal.setId(returned.getId());

        MATCHER.assertEquals(userMeal, returned);
    }

    @Test
    public void testRead() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + "/read"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String json = action.andReturn().getResponse().getContentAsString();
        List<UserMeal> meals = JsonUtil.readValues(json, UserMeal.class);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), meals);
    }

    @Test
    public void testReadId() throws Exception {
        mockMvc.perform(get(REST_URL + "/read/" + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = duplicate(MEAL1);

        updated.setDescription("updatedUserMeal");
        mockMvc.perform(put(REST_URL + "/update/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userMealService.get(MEAL1_ID, UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/delete/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        userMealService.get(MEAL1_ID, UserTestData.USER_ID);
    }
}
