package com.pszumanski.libraryregister.service;

import com.pszumanski.libraryregister.dao.BookDao;
import com.pszumanski.libraryregister.dao.BookDaoImpl;
import com.pszumanski.libraryregister.dao.ReaderDao;
import com.pszumanski.libraryregister.dao.ReaderDaoImpl;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PenaltyService {

    private static final int RATE = 10;

    public static void calculate(LocalDate currentDate, LocalDate changedDate) {
        BookDao bookManager = new BookDaoImpl();
        ReaderDao readerManager = new ReaderDaoImpl();
        readerManager.setSearch(new ReaderFindById());

        bookManager.get().stream()
                .filter(book -> book.getDeadline() != null)
                .filter(book -> changedDate.isAfter(book.getDeadline()))
                .forEach(book -> {
                    int daysDifference = (int) (Math.min(
                            ChronoUnit.DAYS.between(book.getDeadline(), changedDate),
                            ChronoUnit.DAYS.between(currentDate, changedDate)));
                    Reader reader = readerManager.search(book.getCurrentReaderId().toString()).getFirst();
                    reader.setPenalty(reader.getPenalty() + daysDifference * RATE);
                });

    }
}
