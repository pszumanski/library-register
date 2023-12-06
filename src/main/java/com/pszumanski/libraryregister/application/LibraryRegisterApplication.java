package com.pszumanski.libraryregister.application;

import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.github.spring.boot.javafx.stage.BorderlessStage;
import com.github.spring.boot.javafx.stage.BorderlessStageWrapper;
import com.pszumanski.libraryregister.data.Author;
import com.pszumanski.libraryregister.managers.*;
import com.pszumanski.libraryregister.repositories.AuthorRepository;
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

    public static void main(String[] args) {
        Application.launch(LibraryRegisterApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDatabase(AuthorRepository authorRepository) {
        return (args) -> {
            log.info("Database loading");
            log.info(authorRepository.toString());

            AuthorManagerInterface authorManager = new AuthorManager();
            FileManageInterface fileManager = new FileManager(authorRepository, authorManager, new BookManager(), new ReaderManager());
            fileManager.loadDatabase();
            log.info(authorManager.get().toString());

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
