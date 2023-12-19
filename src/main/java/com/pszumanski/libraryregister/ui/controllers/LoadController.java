package com.pszumanski.libraryregister.ui.controllers;

import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.data.managers.FileManager;
import com.pszumanski.libraryregister.data.managers.FileManagerService;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@ViewController
public class LoadController {

    private static Stage stage;

    public static void setStage(Stage stage) {
        LoadController.stage = stage;
    }

    public static final String MAIN_FXML = "/views/main.fxml";

    public void load() {
        FileManagerService fileManager = FileManager.getInstance();
        fileManager.loadDatabase();
        switchToMenu();
    }

    public void createNew() {
        FileManagerService fileManager = FileManager.getInstance();
        fileManager.createNew();
        switchToMenu();
    }

    private void switchToMenu() {
        Pane pane = FxmlUtils.fmxlLoader(MAIN_FXML);

        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());

        String css = this.getClass().getResource("/stylesheets/lightmode.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public void exit() {
        Platform.exit();
    }

}
