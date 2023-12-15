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
        mainController.setCenter("/views/authors.fxml");
    }

    @FXML
    private void showReaders() {
        mainController.setCenter("/views/readers.fxml");
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
