package com.pszumanski.libraryregister.dao;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.service.TimeService;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BookDaoImpl implements BookDao {

    private static List<Book> books;
    private BookSearch bookSearch;

    @Override
    public List<Book> get() {
        return BookDaoImpl.books;
    }

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public void remove(Book book) {
        books.remove(book);
    }

    @Override
    public List<Book> search(String query) {
        return bookSearch.search(query);
    }

    @Override
    public void setSearch(BookSearch bookSearch) {
        this.bookSearch = bookSearch;
    }

    @Override
    public void load(List<Book> books) {
        BookDaoImpl.books = books;
    }

    @Override
    public boolean isLent(Book book) {
        return book.getCurrentReaderId() != null;
    }

    @Override
    public boolean isOverDue(Book book) {
        return TimeService.getInstance().isBefore(book.getDeadline());
    }

    @Override
    public List<String> getLanguages() {
        return books.stream()
                .map(book -> book.getLanguage())
                .distinct()
                .toList();
    }

    @Override
    public List<String> getGenres() {
        return books.stream()
                .map(book -> book.getGenre())
                .distinct()
                .toList();
    }

    @Override
    public void lendBook(Book book, Reader reader, LocalDate date) {
        book.setDeadline(date);
        book.setCurrentReaderId(reader.getId());
    }
}
