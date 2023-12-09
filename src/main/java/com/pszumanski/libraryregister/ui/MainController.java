package com.pszumanski.libraryregister.ui;


import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

@ViewController
public class MainController {



    @FXML
    Tab booksTab;
    @FXML
    Tab authorsTab;
    @FXML
    Tab readersTab;

    @FXML
    void addDay() {

    }

    @FXML
    void addMonth() {

    }

    @FXML
    void addWeek() {

    }

    @FXML
    void chooseDate() {

    }

    @FXML
    void darkmode() {

    }

    @FXML
    void lightmode() {

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
    void load() {
        FileManager.getInstance().loadDatabase();
    }

    @FXML
    void save() {
        FileManager.getInstance().saveDatabase();
    }

    @FXML
    void setEnglish() {
        //TODO: Change language
        Locale.setDefault(new Locale.Builder().setLanguage("en").build());
    }

    @FXML
    void setPolish() {
        //TODO: Change langauge
        Locale.setDefault(new Locale.Builder().setLanguage("pl").build());
    }

    public void showBooks() {
        if (booksTab.isSelected()) {

        }
    }

    public void showAuthors() {
        if (authorsTab.isSelected()) {

        }
    }

    public void showReaders() {
        if (readersTab.isSelected()) {

        }
    }
}
