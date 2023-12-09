package com.pszumanski.libraryregister.ui;

import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import com.pszumanski.libraryregister.managers.dataManagers.FileManagerService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

@ViewController
public class LoadController {

    public static final String MAIN_FXML = "/views/main.fxml";

    public void load(ActionEvent event) throws IOException {
        FileManagerService fileManager = FileManager.getInstance();
        fileManager.loadDatabase();
        switchToMenu(event);
    }

    public void createNew(ActionEvent event) throws IOException {
        FileManagerService fileManager = FileManager.getInstance();
        fileManager.createNew();
        switchToMenu(event);
    }

    private void switchToMenu(ActionEvent event) throws IOException{
        Pane pane = FxmlUtils.fmxlLoader(MAIN_FXML);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());

        String css = this.getClass().getResource("/stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void exit() {
        Platform.exit();
    }

}
