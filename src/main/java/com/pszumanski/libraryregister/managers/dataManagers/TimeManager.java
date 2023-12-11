package com.pszumanski.libraryregister.managers.dataManagers;

import java.time.LocalDate;

public class TimeManager {

    private static TimeManager timeManager;
    private LocalDate currentDate;

    private TimeManager() {
        currentDate = LocalDate.now();
        System.out.println(currentDate);
        timeManager = this;
    }

    public static TimeManager getInstance() {
        if (timeManager == null) {
            return new TimeManager();
        }

        return timeManager;
    }

    public LocalDate getDate() {
        return currentDate;
    }

    public void addDay() {
        currentDate = currentDate.plusDays(1);
    }

    public void addWeek() {currentDate = currentDate.plusWeeks(1);}

    public void addMonth() {currentDate = currentDate.plusMonths(1);}

    public void chooseDate(LocalDate date) {currentDate = date;}

    public LocalDate calculateDate(int days) {
        return currentDate.plusDays(days);
    }

    public boolean isBefore(LocalDate dateToCheck) {
        return dateToCheck.isBefore(currentDate);
    }

}
