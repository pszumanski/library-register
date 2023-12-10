package com.pszumanski.libraryregister.ui;


import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

@ViewController
public class MainController {

    public static final String MAIN_FXML = "/views/main.fxml";

    @FXML
    private HBox loadBox;

    private static Stage stage;

    public static void setStage(Stage stage) {
        MainController.stage = stage;
    }

    @FXML
    private MenuButtonsController menuButtonsController;

    @FXML
    private void initialize() {
        System.out.println(menuButtonsController);
        menuButtonsController.setMainController(this);
    }

    @FXML
    private void addDay() {

    }

    @FXML
    private void addMonth() {

    }

    @FXML
    private void addWeek() {

    }

    @FXML
    private void chooseDate() {

    }

    @FXML
    private void darkmode() {
    //TODO: Set darkmode
    }

    @FXML
    private void lightmode() {
    //TODO: set light mode
    }

    @FXML
    private void exit() {
        switch (DialogUtils.exitConfirmation().get().getButtonData()) {
            case ButtonBar.ButtonData.OK_DONE:
                FileManager.getInstance().saveDatabase();
            case ButtonBar.ButtonData.FINISH:
                Platform.exit();
                break;
            case ButtonBar.ButtonData.NO:
                break;
        }
    }

    @FXML
    private void about() {
        DialogUtils.about();
    }

    @FXML
    private void load() {
        FileManager.getInstance().loadDatabase();
    }

    @FXML
    private void save() {
        FileManager.getInstance().saveDatabase();
    }

    @FXML
    private void setEnglish(ActionEvent event) {
        Locale.setDefault(new Locale.Builder().setLanguage("en").build());
        reloadMain();
    }

    @FXML
    private void setPolish(ActionEvent event) {
        Locale.setDefault(new Locale.Builder().setLanguage("pl").build());
        reloadMain();
    }

    public void setCenter(String fxmlPath) {
        loadBox.getChildren().removeAll();
        loadBox.getChildren().add(FxmlUtils.fmxlLoader(fxmlPath));
    }

    private void reloadMain() {
        Pane pane = FxmlUtils.fmxlLoader(MAIN_FXML);

        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());

        String css = this.getClass().getResource("/stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

}
