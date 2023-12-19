package com.pszumanski.libraryregister.application;

import atlantafx.base.theme.PrimerLight;
import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import com.pszumanski.libraryregister.managers.dataManagers.FileManagerService;
import com.pszumanski.libraryregister.repositories.AuthorRepository;
import com.pszumanski.libraryregister.repositories.BookRepository;
import com.pszumanski.libraryregister.repositories.ReaderRepository;
import com.pszumanski.libraryregister.ui.FxmlUtils;
import com.pszumanski.libraryregister.ui.LoadController;
import com.pszumanski.libraryregister.ui.MainController;
import javafx.application.Application;
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

import java.util.ResourceBundle;

@SpringBootApplication(scanBasePackages = "com.pszumanski.libraryregister")
@EntityScan("com.pszumanski.libraryregister.data")
@EnableJpaRepositories(basePackages = "com.pszumanski.libraryregister.repositories")
@Slf4j
public class LibraryRegisterApplication extends SpringJavaFXApplication {

    public static final String LOAD_FXML = "/views/load.fxml";
    public static final String LIBRARY_REGISTER_LOGO_PNG = "/images/libraryLogoNew.png";
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

            // Setting up file manager
            FileManagerService fileManager = FileManager.getInstance();
            fileManager.setRepos(authorRepository, bookRepository, readerRepository);
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

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        log.info("Stage loaded");
    }

}
