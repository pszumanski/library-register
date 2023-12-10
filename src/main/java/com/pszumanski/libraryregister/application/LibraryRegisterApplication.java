package com.pszumanski.libraryregister.application;

import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import com.pszumanski.libraryregister.managers.dataManagers.FileManagerService;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManager;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManagerService;
import com.pszumanski.libraryregister.managers.inputManagers.factories.ReaderFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.ReaderFactoryService;
import com.pszumanski.libraryregister.repositories.AuthorRepository;
import com.pszumanski.libraryregister.repositories.BookRepository;
import com.pszumanski.libraryregister.repositories.ReaderRepository;
import com.pszumanski.libraryregister.ui.FxmlUtils;
import com.pszumanski.libraryregister.ui.LoadController;
import com.pszumanski.libraryregister.ui.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@SpringBootApplication(scanBasePackages = "com.pszumanski.libraryregister")
@EntityScan("com.pszumanski.libraryregister.data")
@EnableJpaRepositories(basePackages = "com.pszumanski.libraryregister.repositories")
@Slf4j
public class LibraryRegisterApplication extends SpringJavaFXApplication {

    public static final String LOAD_FXML = "/views/load.fxml";
    public static final String LIBRARY_REGISTER_LOGO_PNG = "/images/libraryRegisterLogo.png";
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;

    public static void main(String[] args) {
        launch(LibraryRegisterApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDatabase() {
        return (args) -> {
//            log.info("Started database loading");
//            log.info(authorRepository.toString() + " loaded");
//            log.info(bookRepository.toString() + " loaded");
//            log.info(readerRepository.toString() + " loaded");
//
//            AuthorManagerService authorManager = new AuthorManager();
//            BookManagerService bookManager = new BookManager();
//            ReaderManagerService readerManager = new ReaderManager();

//            fileManager = new FileManager(authorRepository, bookRepository, readerRepository);
//            log.info(fileManager.toString());

            // Setting up file manager
            FileManagerService fileManager = FileManager.getInstance();
            fileManager.setRepos(authorRepository, bookRepository, readerRepository);

//            fileManager.loadDatabase();
//            log.info("Database loaded");
//            FileManager fileManager = FileManager.getInstance();
//            fileManager.setRepos(authorRepository, bookRepository, readerRepository);


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

//            BookFactoryService bookFactory = new BookFactory(bookManager);
//            Book book = bookFactory.create(Map.of("authorId", "1", "title", "Wiedźmin", "publisher", "SuperNowa", "publishYear", "1999", "isbn", "123", "genre", "fantasy", "language", "polish"));
//            log.info(book.toString());
//            bookManager.add(book);
//
//            TimeManager timeManager = TimeManager.getInstance();
//            log.info("Current date: " + timeManager.getDate());
//
//            Map<String, String> testMap = new HashMap<>();
//            testMap.put("name", "Stefannn");
//            testMap.put("bornDate", "");
//            testMap.put("deathDate", "-");
//            ValidatorService authorValidator = new AuthorValidator();
//            if (authorValidator.validate(testMap)) {
//                log.info("author values valid");
//            } else {
//                log.info("author values invalid");
//            }
//            log.info(testMap.toString());
//
//            log.info("Books: " + bookManager.get().toString());
//
//            BookFilter bookFilter = new BookFilterAvailable();
//            log.info("Books available: " + bookFilter.filter(bookManager.get()).toString());
//
//            bookManager.setSearch(new BookFindByAuthorName());
//            log.info("Books of author 'Jack': " + bookManager.search("jACk").toString());
//            fileManager.saveDatabase();
//            log.info("Database saved");
        };
    }


    @Override
    public void start(Stage stage) throws Exception {
        log.info("Started stage loading");

        LoadController.setStage(stage);
        MainController.setStage(stage);

        Pane pane = FxmlUtils.fmxlLoader(LOAD_FXML);
        Scene scene = new Scene(pane);

        ResourceBundle bundle = FxmlUtils.getResourceBundle();

        Image icon = new Image(getClass().getResource(LIBRARY_REGISTER_LOGO_PNG).toExternalForm());
        stage.setTitle(bundle.getString("application.title"));
        stage.getIcons().add(icon);

        String css = this.getClass().getResource("/stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        log.info("Stage loaded");
    }

}
