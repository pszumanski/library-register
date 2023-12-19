package com.pszumanski.libraryregister.data.managers;

import java.time.LocalDate;

public class TimeManager {

    private static TimeManager timeManager;
    private LocalDate changedDate;
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
        changedDate = currentDate.plusDays(1);
        PenaltyManager.calculate(currentDate, changedDate);
        currentDate = changedDate;
    }

    public void addWeek() {
        changedDate = currentDate.plusWeeks(1);
        PenaltyManager.calculate(currentDate, changedDate);
        currentDate = changedDate;
    }

    public void addMonth() {
        changedDate = currentDate.plusMonths(1);
        PenaltyManager.calculate(currentDate, changedDate);
        currentDate = changedDate;
    }

    public void chooseDate(LocalDate date) {
        PenaltyManager.calculate(currentDate, date);
        currentDate = date;
    }

    public boolean isBefore(LocalDate dateToCheck) {
        return dateToCheck.isBefore(currentDate);
    }

}
