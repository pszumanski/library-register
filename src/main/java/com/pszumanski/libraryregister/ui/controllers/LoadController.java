package com.pszumanski.libraryregister.ui.controllers;

import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.service.FileManagerImpl;
import com.pszumanski.libraryregister.service.FileManager;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@ViewController
public class LoadController {

    public static final String MAIN_FXML = "/views/main.fxml";
    private static Stage stage;

    public static void setStage(Stage stage) {
        LoadController.stage = stage;
    }

    @FXML
    private void load() {
        FileManager fileManager = FileManagerImpl.getInstance();
        fileManager.loadDatabase();
        switchToMenu();
    }

    @FXML
    private void createNew() {
        FileManager fileManager = FileManagerImpl.getInstance();
        fileManager.createNew();
        switchToMenu();
    }

    private void switchToMenu() {
        Pane pane = FxmlUtils.fmxlLoader(MAIN_FXML);

        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

}
