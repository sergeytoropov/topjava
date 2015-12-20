package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {
    public static class LocalDateTimeEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(final String text) throws IllegalArgumentException {
            setValue(LocalDateTime.parse(text));
            // date time in ISO8601 format
            // (yyyy-MM-ddTHH:mm:ss.SSSZZ)
        }
        @Override
        public void setValue(final Object value) {
            super.setValue(value == null || value instanceof LocalDateTime ? value
                    : LocalDateTime.parse(value.toString()));
        }
        @Override
        public LocalDateTime getValue() {
            return (LocalDateTime) super.getValue();
        }
        @Override
        public String getAsText() {
            return getValue().toString();
            // date time in ISO8601 format
            // (yyyy-MM-ddTHH:mm:ss.SSSZZ)
        }
    }

    public static class LocalDateTimeBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {
        @Override
        protected void initBeanWrapper(BeanWrapper bw) {
            bw.registerCustomEditor(LocalDateTime.class, new LocalDateTimeEditor());
        }
    }

    //private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);
    private static final LocalDateTimeBeanPropertyRowMapper<UserMeal> ROW_MAPPER = LocalDateTimeBeanPropertyRowMapper.newInstance(UserMeal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //private SimpleJdbcInsert insertUser;
    public SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());

        if (userMeal.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET datetime=:datetime, description=:description, calories=:calories " +
                            "WHERE id=:id and user_id=:user_id", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? and user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> listUserMeal = jdbcTemplate.query("SELECT * FROM meals WHERE id=? and user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(listUserMeal);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return null;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}
