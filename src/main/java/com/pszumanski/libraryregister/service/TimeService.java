package com.pszumanski.libraryregister.service;

import java.time.LocalDate;

public interface TimeService {

    public LocalDate getDate();

    public void addDay();

    public void addWeek();

    public void addMonth();

    public void chooseDate(LocalDate date);

    public boolean isBefore(LocalDate dateToCheck);

}
