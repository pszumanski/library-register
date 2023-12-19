package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PenaltyManager {

    private static final int RATE = 10;

    public static void calculate(LocalDate currentDate, LocalDate changedDate) {
        BookManagerService bookManager = new BookManager();
        ReaderManagerService readerManager = new ReaderManager();
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
