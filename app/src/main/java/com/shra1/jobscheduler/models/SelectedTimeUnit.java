package com.shra1.jobscheduler.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelectedTimeUnit {
    String name;
    TimeUnit timeUnit;

    public SelectedTimeUnit(String name, TimeUnit timeUnit) {

        this.name = name;
        this.timeUnit = timeUnit;
    }

    public SelectedTimeUnit() {

    }

    @Override
    public String toString() {
        return name;
    }

    public static List<SelectedTimeUnit> getList() {
        List<SelectedTimeUnit> l = new ArrayList<>();
        l.add(new SelectedTimeUnit("Seconds", TimeUnit.SECONDS));
        l.add(new SelectedTimeUnit("Minutes", TimeUnit.MINUTES));
        l.add(new SelectedTimeUnit("Hours", TimeUnit.HOURS));
        l.add(new SelectedTimeUnit("Days", TimeUnit.DAYS));
        return l;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
