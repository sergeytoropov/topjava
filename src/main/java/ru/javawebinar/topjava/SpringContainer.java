package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sit on 15.12.15.
 */
public final class SpringContainer {
    private static final ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

    private SpringContainer() {}

    public static ConfigurableApplicationContext getAppCtx() {
        return appCtx;
    }
}
