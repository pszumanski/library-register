package com.pszumanski.libraryregister.ui.controllers;

import javafx.fxml.FXML;

public class MenuButtonsController {

    public static final String VIEWS_BOOKS_FXML = "/views/books.fxml";
    public static final String VIEWS_AUTHORS_FXML = "/views/authors.fxml";
    public static final String VIEWS_READERS_FXML = "/views/readers.fxml";
    private MainController mainController;

    @FXML
    private void showBooks() {
        mainController.setCenter(VIEWS_BOOKS_FXML);
    }

    @FXML
    private void showAuthors() {
        mainController.setCenter(VIEWS_AUTHORS_FXML);
    }

    @FXML
    private void showReaders() {
        mainController.setCenter(VIEWS_READERS_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
