package com.pszumanski.libraryregister.managers.dataManagers;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByAuthorId;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AuthorManager implements AuthorManagerService {

    private static List<Author> authors;
    private static AuthorSearch authorSearch;

    @Override
    public void add(Author author) {
        authors.add(author);
    }

    @Override
    public void remove(Author author) {
        authors.remove(author);
    }

    @Override
    public List<Author> search(String query) {
        return authorSearch.search(query);
    }

    @Override
    public void setSearch(AuthorSearch authorSearch) {
        AuthorManager.authorSearch = authorSearch;
    }

    @Override
    public void load(List<Author> authors) {
        AuthorManager.authors = authors;
    }

    @Override
    public List<Author> get() {
        return AuthorManager.authors;
    }

    @Override
    public List<String> fetchTitles(Author author) {
        BookManager bookManager = new BookManager();
        bookManager.setSearch(new BookFindByAuthorId());
        List<Book> books = bookManager.search(author.getId().toString());
        Set<String> titles = new TreeSet<>();
        books.forEach(book -> {
            if (book.getAuthorId().equals(author.getId())) {
                titles.add(book.getTitle());
            }
        });
        return titles.stream().toList();
    }
}
