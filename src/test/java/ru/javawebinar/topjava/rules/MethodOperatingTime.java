package ru.javawebinar.topjava.rules;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestName;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class MethodOperatingTime extends ExternalResource {
    private TestName testName;
    private LocalTime start;

    public MethodOperatingTime(TestName testName) {
        this.testName = testName;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return super.apply(base, description);
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        start = LocalTime.now();
    }

    @Override
    protected void after() {
        super.after();
        LocalTime end = LocalTime.now();
        LocalTime result = end.minus(start.toNanoOfDay(), ChronoUnit.NANOS);
        System.out.println("Method name is a " + testName.getMethodName() + " worked " + result.getNano() / 1000000 + " ms.");
    }
}
