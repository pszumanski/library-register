package com.pszumanski.libraryregister.application;

import atlantafx.base.theme.PrimerLight;
import com.github.spring.boot.javafx.SpringJavaFXApplication;
import com.pszumanski.libraryregister.repository.AuthorRepository;
import com.pszumanski.libraryregister.repository.BookRepository;
import com.pszumanski.libraryregister.repository.ReaderRepository;
import com.pszumanski.libraryregister.dao.DatabaseConnection;
import com.pszumanski.libraryregister.dao.DatabaseConnectionImpl;
import com.pszumanski.libraryregister.ui.controllers.LoadController;
import com.pszumanski.libraryregister.ui.controllers.MainController;
import com.pszumanski.libraryregister.ui.utils.AppStarter;
import com.pszumanski.libraryregister.ui.utils.DialogUtils;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ResourceBundle;

@SpringBootApplication(scanBasePackages = "com.pszumanski.libraryregister")
@Slf4j
public class LibraryRegisterApplication extends SpringJavaFXApplication {

    public static final String LOAD_FXML = "/views/load.fxml";
    public static final String LIBRARY_REGISTER_LOGO_PNG = "/images/libraryIcon.png";

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private AppStarter appStarter;

    public static void main(String[] args) {
        launch(LibraryRegisterApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDatabase() {
        return (args) -> {

            // Setting up file manager
            DatabaseConnection databaseConnection = DatabaseConnectionImpl.getInstance();
            databaseConnection.setRepos(authorRepository, bookRepository, readerRepository);
        };
    }


    @Override
    public void start(Stage stage) {
        log.info(String.valueOf(appStarter == null));
        log.info("Started stage loading");

        LoadController.setStage(stage);
        MainController.setStage(stage);

        Pane pane = FxmlUtils.fmxlLoader(LOAD_FXML);
        Scene scene = new Scene(pane);

        ResourceBundle bundle = FxmlUtils.getResourceBundle();

        Image icon = new Image(getClass().getResource(LIBRARY_REGISTER_LOGO_PNG).toExternalForm());
        stage.setTitle(bundle.getString("application.title"));
        stage.getIcons().add(icon);

        // Handle exit
        stage.setOnCloseRequest(event -> {
            switch (DialogUtils.exitConfirmation().get().getButtonData()) {
                case OK_DONE:
                    DatabaseConnectionImpl.getInstance().saveDatabase();
                case FINISH:
                    Platform.exit();
                    break;
                case NO:
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
