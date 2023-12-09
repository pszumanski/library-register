package com.pszumanski.libraryregister.ui;


import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;

import java.util.Locale;
import java.util.ResourceBundle;

@ViewController
public class MainController {

    @FXML
    void addDay(ActionEvent event) {

    }

    @FXML
    void addMonth(ActionEvent event) {

    }

    @FXML
    void addWeek(ActionEvent event) {

    }

    @FXML
    void chooseDate(ActionEvent event) {

    }

    @FXML
    void darkmode(ActionEvent event) {

    }

    @FXML
    void lightmode(ActionEvent event) {

    }

    @FXML
    void exit() {
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
    void about() {
        DialogUtils.about();
    }

    @FXML
    void load(ActionEvent event) {
        FileManager.getInstance().loadDatabase();
    }

    @FXML
    void save(ActionEvent event) {
        FileManager.getInstance().saveDatabase();
    }

    @FXML
    void setEnglish(ActionEvent event) {
        //TODO: Change language
        Locale.setDefault(new Locale.Builder().setLanguage("en").build());
    }

    @FXML
    void setPolish(ActionEvent event) {
        //TODO: Change langauge
        Locale.setDefault(new Locale.Builder().setLanguage("pl").build());
    }

}
