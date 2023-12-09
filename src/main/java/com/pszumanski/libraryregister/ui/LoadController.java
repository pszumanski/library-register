package com.pszumanski.libraryregister.ui;

import com.github.spring.boot.javafx.stereotype.ViewController;
import com.pszumanski.libraryregister.managers.dataManagers.FileManager;
import com.pszumanski.libraryregister.managers.dataManagers.FileManagerService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

@ViewController
public class LoadController {

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
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event) {
        Platform.exit();
    }

}
