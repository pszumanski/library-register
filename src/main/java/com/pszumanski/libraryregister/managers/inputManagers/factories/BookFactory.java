package com.pszumanski.libraryregister.managers.inputManagers.factories;

import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.managers.dataManagers.BookManagerService;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class BookFactory implements BookFactoryService {

    BookManagerService bookManager;

    @Override
    public Book create(Map<String, String> attributes) {
        //        log.info("Next index: " + (getMaxIndex() + 1));
        return new Book(
                (getMaxIndex() + 1),
                Integer.parseInt(attributes.get("authorId")),
                attributes.get("title"),
                attributes.get("publisher"),
                attributes.get("publishYear"),
                attributes.get("isbn"),
                attributes.get("genre"),
                attributes.get("language")
        );
    }

    private Integer getMaxIndex() {
        return bookManager.get().stream()
                .sorted((book1, book2) -> {
                    return book2.getId() - book1.getId();
                })
                .toList()
                .getFirst()
                .getId();
    }
}
