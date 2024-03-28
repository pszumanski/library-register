package com.pszumanski.libraryregister.factory;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.dao.BookDao;
import lombok.RequiredArgsConstructor;

import java.time.Year;
import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor
public class BookFactory implements Factory<Book> {

    private final BookDao bookManager;

    @Override
    public Book create(Map<String, String> attributes) {
        return new Book(
                (getMaxIndex() + 1),
                Integer.parseInt(attributes.get("authorId")),
                attributes.get("title"),
                attributes.get("publisher"),
                Year.parse(attributes.get("publishYear")),
                attributes.get("isbn"),
                attributes.get("genre"),
                attributes.get("language")
        );
    }

    private Integer getMaxIndex() {
        return bookManager.get().stream()
                .map(book -> book.getId())
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
