package com.pszumanski.libraryregister.ui.controllers;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.managers.AuthorManager;
import com.pszumanski.libraryregister.data.managers.AuthorManagerService;
import com.pszumanski.libraryregister.data.managers.TimeManager;
import com.pszumanski.libraryregister.data.factories.AuthorFactory;
import com.pszumanski.libraryregister.data.factories.AuthorFactoryService;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindByName;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindByTitle;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorSearch;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import com.pszumanski.libraryregister.ui.utils.NotificationUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class AuthorsController {

    private Author selectedAuthor;
    String[] searchOptions = {FxmlUtils.getResourceBundle().getString("showAll"),
            FxmlUtils.getResourceBundle().getString("searchByName"),
            FxmlUtils.getResourceBundle().getString("searchByTitle")};
    private AuthorSearch searchType;
    private AuthorManagerService authorManager;
    DateTimeFormatter dateFormat;

    private static AuthorsController authorsController;

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
        AuthorsController.authorsController = this;

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

        for (TableColumn<?, ?> column : authorTable.getColumns()) {
            column.setReorderable(false);
        }

        loadAuthors();
    }

    private void loadAuthors() {
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
            this.authorTitlesColumn.setCellValueFactory(authorData -> new SimpleIntegerProperty(authorManager.fetchTitles(authorData.getValue()).size()).asObject());
            this.authorBornDateColumn.setCellValueFactory(authorData -> new ReadOnlyStringWrapper(authorData.getValue().getBornDate()));
            this.authorDeathDateColumn.setCellValueFactory(authorData -> new ReadOnlyStringWrapper(authorData.getValue().getDeathDate()));

            this.authorTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectAuthor(newValue)));
        } catch (NullPointerException ex) {}
    }

    public static void refresh() {
        if (authorsController != null) {
            authorsController.tabChanged();
        }
    }

    @FXML
    private void selectAuthor(Author author) {
        if (author != null && selectedAuthorField != null) {
            selectedAuthor = author;
            selectedAuthorField.setText(author.getName());
            loadAuthorInfo();
            validateManage();
        }
        authorTable.refresh();
    }

    @FXML
    private void addAuthor() {
        AuthorFactoryService authorFactory = new AuthorFactory(authorManager);
        Author author = authorFactory.create(Map.of(
                "name", addAuthorName.getText().substring(0,1).toUpperCase() + addAuthorName.getText().substring(1),
                "bornDate", addAuthorBornDate.getText(),
                "deathDate", addAuthorDeathDate.getText()
        ));
        authorManager.add(author);
        addAuthorName.clear();
        addAuthorBornDate.clear();
        addAuthorDeathDate.clear();
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("authorAdded"),
                author.getName() + " " +FxmlUtils.getResourceBundle().getString("authorGotAdded"));
        tabChanged();
    }

    @FXML
    private void tabChanged() {
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

    private void setSearchOption() {
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

        for (Author author : authorManager.get()) {
            if (addAuthorName.getText().equalsIgnoreCase(author.getName())) {
                errors++;
                addAuthorName.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                break;
            }
        }

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
        } catch (NumberFormatException ex) {}

        addAuthorButton.setDisable(errors != 0);
    }

    private void validateManage() {
        deleteAuthorButton.setDisable(true);
        if (!(selectedAuthor == null)) {
            if (!authorManager.fetchTitles(selectedAuthor).isEmpty()) {
            deleteAuthorButton.setText(FxmlUtils.getResourceBundle().getString("authorHasBooks"));
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
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("authorRemoved"),
                selectedAuthor.getName() + " " +FxmlUtils.getResourceBundle().getString("authorGotRemoved"));
        authorManager.remove(selectedAuthor);
        selectedAuthor = null;
        selectedAuthorField.setText(FxmlUtils.getResourceBundle().getString("authorWasRemoved"));
        selectedAuthorField.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        infoAuthorBornDate.setText(FxmlUtils.getResourceBundle().getString("authorWasRemoved"));
        infoAuthorBornDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        infoAuthorDeathDate.setText(FxmlUtils.getResourceBundle().getString("authorWasRemoved"));
        infoAuthorDeathDate.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        infoAuthorTitlesCount.setText(FxmlUtils.getResourceBundle().getString("authorWasRemoved"));
        infoAuthorTitlesCount.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
        deleteAuthorButton.setDisable(true);
        tabChanged();
    }
}

