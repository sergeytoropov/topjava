package ru.javawebinar.topjava.repository.jdbc;

import org.hibernate.SessionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Contact;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sit on 26.12.15.
 */
public class HibernateExample {

    public static void main(String[] args) {
        HibernateExample he = new HibernateExample();
        he.a();
    }

    @Transactional(readOnly = true)
    public void a() {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println("Database");
            SessionFactory session = appCtx.getBean(SessionFactory.class);


            //UserMeal userMeal = new UserMeal(100020, LocalDateTime.now(), "Мой Волшебный слон", 1331);
            //jdbc.save(userMeal, 100000);
            //jdbc.delete(100019, 100000);
            //JdbcUserMealRepositoryImpl jdbc = appCtx.getBean(JdbcUserMealRepositoryImpl.class);
            //for (int i = 0; i < 10000; i++) {
            //    UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Обед " + i, 1331);
            //    jdbc.save(userMeal, 100000);
            //}

            List<Contact> list = session.getCurrentSession().createQuery("from Contact c").list();
            list.stream().forEach(System.out::println);

        }

    }
}
