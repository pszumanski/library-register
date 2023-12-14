package com.pszumanski.libraryregister.ui;


import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import com.pszumanski.libraryregister.managers.dataManagers.TimeManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

@ViewController
public class MainController {

    private TimeManager timeManager;

    private static Stage stage;
    private static final String MAIN_FXML = "/views/main.fxml";

    @FXML
    private HBox loadBox;
    @FXML
    private MenuButtonsController menuButtonsController;

    @FXML
    private void initialize() {
        menuButtonsController.setMainController(this);
        timeManager = TimeManager.getInstance();
    }

    @FXML
    private void addDay() {
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                FxmlUtils.getResourceBundle().getString("addedDay"));
        timeManager.addDay();
        BooksController.refresh();
        AuthorController.refresh();
    }

    @FXML
    private void addWeek() {
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                FxmlUtils.getResourceBundle().getString("addedWeek"));
        timeManager.addWeek();
        BooksController.refresh();
        AuthorController.refresh();
    }

    @FXML
    private void addMonth() {
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                FxmlUtils.getResourceBundle().getString("addedMonth"));
        timeManager.addMonth();
        BooksController.refresh();
        AuthorController.refresh();
    }

    @FXML
    private void chooseDate() {
        LocalDate date = DialogUtils.pickDate();
        if (date.isBefore(TimeManager.getInstance().getDate())) {
            NotificationController.notification(FxmlUtils.getResourceBundle().getString("dateNotChanged"),
                    date.toString() + " " + FxmlUtils.getResourceBundle().getString("dateNotBefore"));
        } else {
            BooksController.refresh();
            NotificationController.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                    FxmlUtils.getResourceBundle().getString("dateChangedTo") + " " + date.toString());
            timeManager.chooseDate(date);
            BooksController.refresh();
            AuthorController.refresh();
        }
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
        BooksController.refresh();
        AuthorController.refresh();
    }

    @FXML
    private void save() {
        FileManager.getInstance().saveDatabase();
    }

    @FXML
    private void setEnglish(ActionEvent event) {
        Locale.setDefault(new Locale.Builder().setLanguage("en").build());
        NotificationController.notification("Language changed",
                "Language changed to English");
        reloadMain();
    }

    @FXML
    private void setPolish(ActionEvent event) {
        Locale.setDefault(new Locale.Builder().setLanguage("pl").build());
        NotificationController.notification("Zmieniono język",
                "Zmieniono język na polski");
        reloadMain();
    }

    public void setCenter(String fxmlPath) {
        if (loadBox.getChildren().isEmpty()) {
            loadBox.getChildren().add(new Label());
        }
        loadBox.getChildren().set(0, FxmlUtils.fmxlLoader(fxmlPath));
    }

    public void reloadMain() {
        Pane pane = FxmlUtils.fmxlLoader(MAIN_FXML);

        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());

        String css = this.getClass().getResource("/stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public static void setStage(Stage stage) {
        MainController.stage = stage;
    }
}
