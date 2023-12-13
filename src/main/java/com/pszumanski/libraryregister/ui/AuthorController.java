package com.pszumanski.libraryregister.ui;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.*;
import com.pszumanski.libraryregister.managers.inputManagers.factories.AuthorFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.AuthorFactoryService;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactoryService;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindByName;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindByTitle;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;
import com.pszumanski.libraryregister.strategy.bookFilter.BookFilter;
import com.pszumanski.libraryregister.strategy.bookFilter.BookFilterAvailable;
import com.pszumanski.libraryregister.strategy.bookFilter.BookFilterGenre;
import com.pszumanski.libraryregister.strategy.bookFilter.BookFilterLanguage;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByAuthorName;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByTitle;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorController {

    private Author selectedAuthor;
    String[] searchOptions = {"Search all", "Search by name", "Search by title"};
    private AuthorSearch searchType;
    private AuthorManagerService authorManager;
    DateTimeFormatter dateFormat;

    private static AuthorController authorController;

    @FXML
    private ChoiceBox<String> searchList;
    @FXML
    private TextField searchQuery;
    @FXML
    private Label elementsFound;
    @FXML
    private Label objectFound;
    @FXML
    private TableView<Author> authorTable;
    @FXML
    private TableColumn<Author, String> authorBornDateColumn;
    @FXML
    private TableColumn<Author, String> authorDeathDateColumn;
    @FXML
    private TableColumn<Author, Integer> authorIdColumn;
    @FXML
    private TableColumn<Author, String> authorNameColumn;
    @FXML
    private TableColumn<Author, Integer> authorTitlesColumn;
    @FXML
    private Button addAuthorButton;
    @FXML
    private TextField selectedAuthorField;
    @FXML
    private TextField addAuthorName;
    @FXML
    private TextField addAuthorBornDate;
    @FXML
    private TextField addAuthorDeathDate;
    @FXML
    private Button deleteAuthorButton;
    @FXML
    private TextField infoAuthorName;
    @FXML
    private TextField infoAuthorBornDate;
    @FXML
    private TextField infoAuthorDeathDate;
    @FXML
    private TextField infoAuthorTitlesCount;
    @FXML
    private Tab searchTab;
    @FXML
    private Tab addTab;
    @FXML
    public Label dateField;

    @FXML
    private void initialize() {
        AuthorController.authorController = this;

        authorManager = new AuthorManager();

        searchList.getItems().addAll(searchOptions);
        searchList.setValue(searchOptions[0]);
        searchList.setOnAction(e -> setSearchOption());

        searchQuery.setDisable(true);
        searchQuery.setOnKeyTyped(e -> loadAuthors());

        deleteAuthorButton.setDisable(true);
        addAuthorButton.setDisable(true);

        addAuthorName.setOnKeyTyped(e -> validateAuthor());
        addAuthorBornDate.setOnKeyTyped(e -> validateAuthor());
        addAuthorDeathDate.setOnKeyTyped(e -> validateAuthor());

        dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        dateField.setText(TimeManager.getInstance().getDate().format(dateFormat));

        loadAuthors();
    }

    public void loadAuthors() {
        try {
            List<Author> foundAuthors;

            if (searchType != null & !searchQuery.getText().isEmpty()) {
                authorManager.setSearch(searchType);
                foundAuthors = authorManager.search(searchQuery.getText());
            } else {
                foundAuthors = authorManager.get();
            }

            ObservableList<Author> authors = FXCollections.observableArrayList(foundAuthors);

            elementsFound.setText(String.valueOf(authors.size()));

            if (authors.size() != 1) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("manyAuthors"));
            } else {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("singleAuthor"));
            }

            this.authorTable.setItems(authors);
            this.authorIdColumn.setCellValueFactory(authorData -> new SimpleIntegerProperty(authorData.getValue().getId()).asObject());
            this.authorNameColumn.setCellValueFactory(authorData -> new ReadOnlyStringWrapper(authorData.getValue().getName()));
            this.authorTitlesColumn.setCellValueFactory(authorData -> {
                return new SimpleIntegerProperty(authorManager.fetchTitles(authorData.getValue()).size()).asObject();
            });
            this.authorBornDateColumn.setCellValueFactory(authorData -> new ReadOnlyStringWrapper(authorData.getValue().getBornDate()));
            this.authorDeathDateColumn.setCellValueFactory(authorData -> new ReadOnlyStringWrapper(authorData.getValue().getDeathDate()));

            this.authorTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectAuthor(newValue)));
        } catch (NullPointerException ex) {}
    }

    public static void refresh() {
        if (authorController != null) {
            authorController.tabChanged();
        }
    }

    @FXML
    public void selectAuthor(Author author) {
        if (author != null && selectedAuthorField != null) {
            selectedAuthor = author;
            selectedAuthorField.setText(author.getName());
            loadAuthorInfo();
            validateManage();
        }
    }

    @FXML
    public void addAuthor() {
        AuthorFactoryService authorFactory = new AuthorFactory(authorManager);
        Author author = authorFactory.create(Map.of(
                "name", addAuthorName.getText().substring(0,1).toUpperCase() + addAuthorName.getText().substring(1),
                "bornDate", addAuthorBornDate.getText(),
                "deathDate", addAuthorDeathDate.getText().substring(0,1).toUpperCase() + addAuthorDeathDate.getText().substring(1)
        ));
        authorManager.add(author);
        addAuthorName.clear();
        addAuthorBornDate.clear();
        addAuthorDeathDate.clear();
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("authorAdded"),
                author.getName() + " " +FxmlUtils.getResourceBundle().getString("authorGotAdded"));
        tabChanged();
    }

    @FXML
    public void tabChanged() {
        if (searchTab.isSelected()) {
            loadAuthors();
            loadAuthorInfo();
            validateManage();
        } else if (addTab.isSelected()) {
            loadAuthors();
        }
        if (dateField != null) {
            dateField.setText(TimeManager.getInstance().getDate().format(dateFormat));
        }
    }

    public void setSearchOption() {
        if (searchList.getValue().equals(searchOptions[1])) {
            searchType = new AuthorFindByName();
            searchQuery.setText("");
            searchQuery.setDisable(false);
        } else if ((searchList.getValue().equals(searchOptions[2]))) {
            searchType = new AuthorFindByTitle();
            searchQuery.setText("");
            searchQuery.setDisable(false);
        } else {
            searchQuery.setText("");
            searchQuery.setDisable(true);
            searchType = null;
        }
        loadAuthors();
    }

    private void validateAuthor() {
        int errors = 0;
        errors += checkEmpty(addAuthorName);
        int currentYear = TimeManager.getInstance().getDate().getYear();
        try {
            if (Integer.parseInt(addAuthorBornDate.getText()) > currentYear) {
                addAuthorBornDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                errors++;
            } else {
                addAuthorBornDate.setStyle("");
            }
        } catch (NumberFormatException ex) {
            if (!addAuthorBornDate.getText().isEmpty()) {
                addAuthorBornDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                errors++;
            } else {
                addAuthorBornDate.setStyle("");
            }
        }
        try {
            if (Integer.parseInt(addAuthorDeathDate.getText()) > currentYear) {
                addAuthorDeathDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                errors++;
            } else {
                addAuthorDeathDate.setStyle("");
            }
        } catch (NumberFormatException ex) {
            if (!addAuthorDeathDate.getText().isEmpty()) {
                addAuthorDeathDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                errors++;
            } else {
                addAuthorDeathDate.setStyle("");
            }
        }
        try {
            if (Integer.parseInt(addAuthorBornDate.getText()) >= Integer.parseInt(addAuthorDeathDate.getText())) {
                addAuthorBornDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                addAuthorDeathDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                errors++;
            }
        } catch (NullPointerException ex) {}

        addAuthorButton.setDisable(errors != 0);
    }

    private void validateManage() {
        deleteAuthorButton.setDisable(true);
        if (!(selectedAuthor == null)) {
            if (!authorManager.fetchTitles(selectedAuthor).isEmpty()) {
            deleteAuthorButton.setText("Author has books");
            } else {
                deleteAuthorButton.setText(FxmlUtils.getResourceBundle().getString("removeAuthor"));
                deleteAuthorButton.setDisable(false);
            }
        } else {
            deleteAuthorButton.setText(FxmlUtils.getResourceBundle().getString("removeAuthor"));
        }
    }

    private void loadAuthorInfo() {
        if (selectedAuthor != null) {
            selectedAuthorField.setText(selectedAuthor.getName());
            selectedAuthorField.setStyle("");
            infoAuthorBornDate.setText(selectedAuthor.getBornDate());
            infoAuthorBornDate.setStyle("");
            infoAuthorDeathDate.setText(selectedAuthor.getDeathDate());
            infoAuthorDeathDate.setStyle("");
            infoAuthorTitlesCount.setText(String.valueOf(authorManager.fetchTitles(selectedAuthor).size()));
            infoAuthorTitlesCount.setStyle("");
        } else {
            selectedAuthorField.clear();
            infoAuthorBornDate.clear();
            infoAuthorDeathDate.clear();
            infoAuthorTitlesCount.clear();
        }
    }

    private int checkEmpty(TextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
            return 1;
        } else {
            textField.setStyle("");
            return 0;
        }
    }

    @FXML
    private void deleteAuthor() {
        deleteAuthorButton.setDisable(true);
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("authorRemoved"),
                selectedAuthor.getName() + " " +FxmlUtils.getResourceBundle().getString("authorGotRemoved"));
        authorManager.remove(selectedAuthor);
        selectedAuthor = null;
        selectedAuthorField.setText("Author got deleted");
        selectedAuthorField.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        infoAuthorBornDate.setText("Author got deleted");
        infoAuthorBornDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        infoAuthorDeathDate.setText("Author got deleted");
        infoAuthorDeathDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        infoAuthorTitlesCount.setText("Author got deleted");
        infoAuthorTitlesCount.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        deleteAuthorButton.setDisable(true);
        tabChanged();
    }
}

/*
ReaderManagerService readerManager = new ReaderManager();
        ReaderFactoryService readerFactory = new ReaderFactory(readerManager);
        Reader reader = readerFactory.create(Map.of("name", "Maciej", "bornDate", "23-03-1989", "personalId", "58912734", "addressFirst", "Coconut Street 51/D", "addressSecond", "Gdansk", "email", "maciejunio@outlook.com", "phoneNumber", "589712341"));
        readerManager.add(reader);
 */

