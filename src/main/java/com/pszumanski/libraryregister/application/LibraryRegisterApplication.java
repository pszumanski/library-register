package com.pszumanski.libraryregister.application;

import atlantafx.base.theme.PrimerLight;
import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.pszumanski.libraryregister.data.managers.FileManager;
import com.pszumanski.libraryregister.data.managers.FileManagerService;
import com.pszumanski.libraryregister.data.repositories.AuthorRepository;
import com.pszumanski.libraryregister.data.repositories.BookRepository;
import com.pszumanski.libraryregister.data.repositories.ReaderRepository;
import com.pszumanski.libraryregister.ui.utils.DialogUtils;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import com.pszumanski.libraryregister.ui.controllers.LoadController;
import com.pszumanski.libraryregister.ui.controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
@EnableJpaRepositories(basePackages = "com.pszumanski.libraryregister.data.repositories")
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
    public void start(Stage stage) {
        log.info("Started stage loading");

        LoadController.setStage(stage);
        MainController.setStage(stage);

        Pane pane = FxmlUtils.fmxlLoader(LOAD_FXML);
        Scene scene = new Scene(pane);

        ResourceBundle bundle = FxmlUtils.getResourceBundle();

        Image icon = new Image(getClass().getResource(LIBRARY_REGISTER_LOGO_PNG).toExternalForm());
        stage.setTitle(bundle.getString("application.title"));
        stage.getIcons().add(icon);
        stage.setOnCloseRequest(event -> {
            switch (DialogUtils.exitConfirmation().get().getButtonData()) {
                case ButtonBar.ButtonData.OK_DONE:
                    FileManager.getInstance().saveDatabase();
                case ButtonBar.ButtonData.FINISH:
                    Platform.exit();
                    break;
                case ButtonBar.ButtonData.NO:
                    event.consume();
                    break;
            }
        });

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        log.info("Stage loaded");
    }

}
