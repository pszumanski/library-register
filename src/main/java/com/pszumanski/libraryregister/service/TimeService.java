package com.pszumanski.libraryregister.service;

import java.time.LocalDate;

public class TimeService {

    private static TimeService timeService;
    private LocalDate changedDate;
    private LocalDate currentDate;

    private TimeService() {
        currentDate = LocalDate.now();
        timeService = this;
    }

    public static TimeService getInstance() {
        if (timeService == null) {
            return new TimeService();
        }

        return timeService;
    }

    public LocalDate getDate() {
        return currentDate;
    }

    public void addDay() {
        changedDate = currentDate.plusDays(1);
        PenaltyService.calculate(currentDate, changedDate);
        currentDate = changedDate;
    }

    public void addWeek() {
        changedDate = currentDate.plusWeeks(1);
        PenaltyService.calculate(currentDate, changedDate);
        currentDate = changedDate;
    }

    public void addMonth() {
        changedDate = currentDate.plusMonths(1);
        PenaltyService.calculate(currentDate, changedDate);
        currentDate = changedDate;
    }

    public void chooseDate(LocalDate date) {
        PenaltyService.calculate(currentDate, date);
        currentDate = date;
    }

    public boolean isBefore(LocalDate dateToCheck) {
        return dateToCheck.isBefore(currentDate);
    }

}
