package com.pszumanski.libraryregister.data.factory;

import com.pszumanski.libraryregister.data.model.Book;
import com.pszumanski.libraryregister.service.BookService;
import lombok.AllArgsConstructor;

import java.time.Year;
import java.util.Comparator;
import java.util.Map;

@AllArgsConstructor
public class BookFactory implements Factory<Book> {

    BookService bookManager;

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
