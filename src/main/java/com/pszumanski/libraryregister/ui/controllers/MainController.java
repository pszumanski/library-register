package com.pszumanski.libraryregister.ui.controllers;


import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.service.FileManagerImpl;
import com.pszumanski.libraryregister.service.TimeServiceImpl;
import com.pszumanski.libraryregister.ui.utils.DialogUtils;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import com.pszumanski.libraryregister.ui.utils.NotificationUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Locale;

@ViewController
public class MainController {

    private TimeServiceImpl timeService;

    private static Stage stage;
    private static final String MAIN_FXML = "/views/main.fxml";

    @FXML
    private HBox loadBox;
    @FXML
    private MenuButtonsController menuButtonsController;

    @FXML
    private void initialize() {
        menuButtonsController.setMainController(this);
        timeService = TimeServiceImpl.getInstance();
    }

    @FXML
    private void addDay() {
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                FxmlUtils.getResourceBundle().getString("addedDay"));
        timeService.addDay();
        refreshAll();
    }

    @FXML
    private void addWeek() {
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                FxmlUtils.getResourceBundle().getString("addedWeek"));
        timeService.addWeek();
        refreshAll();
    }

    @FXML
    private void addMonth() {
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                FxmlUtils.getResourceBundle().getString("addedMonth"));
        timeService.addMonth();
        refreshAll();
    }

    @FXML
    private void chooseDate() {
        LocalDate date = DialogUtils.pickDate();
        if (date.isBefore(TimeServiceImpl.getInstance().getDate())) {
            NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("dateNotChanged"),
                    date + " " + FxmlUtils.getResourceBundle().getString("dateNotBefore"));
        } else {
            BooksController.refresh();
            NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("dateChanged"),
                    FxmlUtils.getResourceBundle().getString("dateChangedTo") + " " + date);
            timeService.chooseDate(date);
            refreshAll();
        }
    }

    @FXML
    private void darkmode() {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("themeChanged"),
                FxmlUtils.getResourceBundle().getString("themeChangedTo") + " "
                        + FxmlUtils.getResourceBundle().getString("dark"));
    }

    @FXML
    private void lightmode() {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("themeChanged"),
                FxmlUtils.getResourceBundle().getString("themeChangedTo") + " "
                        + FxmlUtils.getResourceBundle().getString("light"));
    }

    @FXML
    private void exit() {
        switch (DialogUtils.exitConfirmation().get().getButtonData()) {
            case ButtonBar.ButtonData.OK_DONE:
                FileManagerImpl.getInstance().saveDatabase();
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
        FileManagerImpl.getInstance().loadDatabase();
        refreshAll();
    }

    @FXML
    private void save() {
        FileManagerImpl.getInstance().saveDatabase();
    }

    @FXML
    private void setEnglish() {
        Locale.setDefault(new Locale.Builder().setLanguage("en").build());
        NotificationUtils.notification("Language changed",
                "Language changed to English");
        reloadMain();
    }

    @FXML
    private void setPolish() {
        Locale.setDefault(new Locale.Builder().setLanguage("pl").build());
        NotificationUtils.notification("Zmieniono język",
                "Zmieniono język na polski");
        reloadMain();
    }

    public void setCenter(String fxmlPath) {
        if (loadBox.getChildren().isEmpty()) {
            loadBox.getChildren().add(new Label());
        }
        loadBox.getChildren().set(0, FxmlUtils.fmxlLoader(fxmlPath));
    }

    private void reloadMain() {
        Pane pane = FxmlUtils.fmxlLoader(MAIN_FXML);

        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());

        stage.setTitle(FxmlUtils.getResourceBundle().getString("application.title"));
        stage.setScene(scene);
    }

    public static void setStage(Stage stage) {
        MainController.stage = stage;
    }

    private void refreshAll() {
        BooksController.refresh();
        AuthorsController.refresh();
        ReadersController.refresh();
    }
}
