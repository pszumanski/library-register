package com.pszumanski.libraryregister.data.managers;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class BookManager implements BookManagerService {

    private static List<Book> books;
    private BookSearch bookSearch;

    @Override
    public List<Book> get() {
        return BookManager.books;
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
        BookManager.books = books;
    }

    @Override
    public boolean isLent(Book book) {
        return book.getCurrentReaderId() != null;
    }

    @Override
    public boolean isOverDue(Book book) {
        return TimeManager.getInstance().isBefore(book.getDeadline());
    }

    @Override
    public List<String> fetchLanguages() {
        Set<String> languages = new HashSet<>();
        books.forEach(book ->
                languages.add(book.getLanguage()));
        return languages.stream().toList();
    }

    @Override
    public List<String> fetchGenres() {
        Set<String> genres = new HashSet<>();
        books.forEach(book ->
                genres.add(book.getGenre()));
        return genres.stream().toList();
    }

    @Override
    public void lendBook(Book book, Reader reader, LocalDate date) {
        book.setDeadline(date);
        book.setCurrentReaderId(reader.getId());
    }
}
