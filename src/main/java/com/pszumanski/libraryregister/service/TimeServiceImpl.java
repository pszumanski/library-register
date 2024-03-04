package com.pszumanski.libraryregister.service;

import java.time.LocalDate;

public class TimeServiceImpl implements TimeService {

    private static TimeServiceImpl timeService;
    private LocalDate changedDate;
    private LocalDate currentDate;

    private TimeServiceImpl() {
        currentDate = LocalDate.now();
        timeService = this;
    }

    public static TimeServiceImpl getInstance() {
        if (timeService == null) {
            return new TimeServiceImpl();
        }

        return timeService;
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
