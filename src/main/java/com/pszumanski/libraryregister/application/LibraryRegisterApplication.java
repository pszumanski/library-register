package com.pszumanski.libraryregister.application;

import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.data.Book;
import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.*;
import com.pszumanski.libraryregister.managers.inputManagers.factories.AuthorFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.AuthorFactoryService;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactoryService;
import com.pszumanski.libraryregister.managers.inputManagers.validators.AuthorValidator;
import com.pszumanski.libraryregister.managers.inputManagers.validators.ValidatorService;
import com.pszumanski.libraryregister.repositories.AuthorRepository;
import com.pszumanski.libraryregister.repositories.BookRepository;
import com.pszumanski.libraryregister.repositories.ReaderRepository;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindByName;
import com.pszumanski.libraryregister.strategy.bookFilter.BookFilter;
import com.pszumanski.libraryregister.strategy.bookFilter.BookFilterAvailable;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByAuthorName;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindById;
import com.pszumanski.libraryregister.strategy.readerSearch.ReaderFindById;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.pszumanski.libraryregister")
@EntityScan("com.pszumanski.libraryregister.data")
@EnableJpaRepositories(basePackages = "com.pszumanski.libraryregister.repositories")
@Slf4j
public class LibraryRegisterApplication extends SpringJavaFXApplication {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;

    public static void main(String[] args) {
        Application.launch(LibraryRegisterApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDatabase(AuthorRepository authorRepository) {
        return (args) -> {
            log.info("Database loading");
            log.info(authorRepository.toString() + " loaded");
            log.info(bookRepository.toString() + " loaded");
            log.info(readerRepository.toString() + " loaded");

            AuthorManagerService authorManager = new AuthorManager();
            BookManagerService bookManager = new BookManager();
            ReaderManagerService readerManager = new ReaderManager();

            FileManagerService fileManager = new FileManager(authorRepository, bookRepository, readerRepository);
            fileManager.loadDatabase();
            log.info("Database loaded");


//            authorManager.setSearch(new AuthorFindByName());
//            Author author = authorManager.search("David daVId").get(0);
//            log.info("Result of search 'David David': " + author.toString());
//
//            readerManager.setSearch(new ReaderFindById());
//            Reader reader = readerManager.search("1").getFirst();
//            log.info("Result of search reader id=1: " + reader.toString());
//
//            boookManager.setSearch(new BookFindById());
//            Book book = boookManager.search("1").getFirst();
//            book.setCurrentReaderId(reader.getId());
//            log.info("Result of search book id=1: " + book);
//
//            log.info("Fetched books of reader: " + reader.getName() + ": " + readerManager.fetchBooks(reader).toString());
//
//            authorManager.setSearch(new AuthorFindById());
//            author = authorManager.search("602").get(0);
//            log.info("Fethced books titles of author: " + author.getName() + ":" + authorManager.fetchTitles(author).toString());

//            AuthorFactoryService authorFactory = new AuthorFactory(authorManager);
//            Author author = authorFactory.create(Map.of("name", "Charmander", "bornDate", "2115", "deathDate", "2225"));
//            log.info(author.toString());
//            authorManager.add(author);

            BookFactoryService bookFactory = new BookFactory(bookManager);
            Book book = bookFactory.create(Map.of("authorId", "1", "title", "Wiedźmin", "publisher", "SuperNowa", "publishYear", "1999", "isbn", "123", "genre", "fantasy", "language", "polish"));
            log.info(book.toString());
            bookManager.add(book);

            TimeManager timeManager = TimeManager.getInstance();
            log.info("Current date: " + timeManager.getDate());

            Map<String, String> testMap = new HashMap<>();
            testMap.put("name", "Stefannn");
            testMap.put("bornDate", "");
            testMap.put("deathDate", "-");
            ValidatorService authorValidator = new AuthorValidator();
            if (authorValidator.validate(testMap)) {
                log.info("author values valid");
            } else {
                log.info("author values invalid");
            }
            log.info(testMap.toString());

            log.info("Books: " + bookManager.get().toString());

            BookFilter bookFilter = new BookFilterAvailable();
            log.info("Books available: " + bookFilter.filter(bookManager.get()).toString());

            bookManager.setSearch(new BookFindByAuthorName());
            log.info("Books of author 'Jack': " + bookManager.search("jACk").toString());
            fileManager.saveDatabase();
            log.info("Database saved");
        };
    }

    @Override
    public void start(Stage stage) throws Exception {
//        var myBorderlessStage = new BorderlessStageWrapper(stage);
//        super.start(stage);
//        myBorderlessStage.setHeader(20);
//        myBorderlessStage.setResizeBorder(2);
        log.info("Stage loaded");
    }
}
