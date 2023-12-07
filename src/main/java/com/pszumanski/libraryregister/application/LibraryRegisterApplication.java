package com.pszumanski.libraryregister.application;

import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.pszumanski.libraryregister.managers.dataManagers.*;
import com.pszumanski.libraryregister.repositories.AuthorRepository;
import com.pszumanski.libraryregister.repositories.BookRepository;
import com.pszumanski.libraryregister.repositories.ReaderRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
            BookManagerService boookManager = new BookManager();
            ReaderManagerService readerManager = new ReaderManager();

            FileManagerService fileManager = new FileManager(authorRepository, bookRepository, readerRepository);
            fileManager.loadDatabase();

            log.info(authorManager.get().toString());
            log.info(boookManager.get().toString());
            log.info(readerManager.get().toString());

//            authorManager.add(new Author("Marek", "123", "123"));

            fileManager.saveDatabase();
            log.info("Database saved");

//            repository.deleteAll();
//
//            repository.save(new Author("Jack", "1324", "1412"));
//            repository.save(new Author("Chloe", "2413", "5212"));
//            repository.save(new Author("Kim", "4123", "4214"));
//            repository.save(new Author("David", "9120", "1424"));
//            repository.save(new Author("Michelle", "1323", "1240"));
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
