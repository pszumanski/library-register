package com.pszumanski.libraryregister.ui;

import javafx.fxml.FXML;

public class MenuButtonsController {

    private MainController mainController;

    @FXML
    private void showBooks() {
        mainController.setCenter("/views/books.fxml");
    }

    @FXML
    private void showAuthors() {
        System.out.println("XD");
    }

    @FXML
    private void showReaders() {
        System.out.println("XDDD");
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
