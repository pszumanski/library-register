package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.managers.BookManagerService;
import lombok.AllArgsConstructor;

import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class BookFactory implements BookFactoryService {

    BookManagerService bookManager;

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
