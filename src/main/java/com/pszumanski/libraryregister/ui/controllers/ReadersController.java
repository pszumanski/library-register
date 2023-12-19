package com.pszumanski.libraryregister.ui.controllers;

import com.pszumanski.libraryregister.data.factories.ReaderFactory;
import com.pszumanski.libraryregister.data.factories.ReaderFactoryService;
import com.pszumanski.libraryregister.data.managers.*;
import com.pszumanski.libraryregister.data.objects.*;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;
import com.pszumanski.libraryregister.strategy.readerFilter.*;
import com.pszumanski.libraryregister.strategy.readerSearch.*;
import com.pszumanski.libraryregister.ui.utils.FxmlUtils;
import com.pszumanski.libraryregister.ui.utils.NotificationUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadersController {

    private Book selectedBook;
    private Reader selectedReader;
    private LocalDate selectedDate;
    String[] searchOptions = {FxmlUtils.getResourceBundle().getString("showAll"),
            FxmlUtils.getResourceBundle().getString("searchByName"),
            FxmlUtils.getResourceBundle().getString("searchByTitle")};
    private ReaderSearch searchType;
    private List<ReaderFilter> filterList;
    private AuthorManagerService authorManager;
    private ReaderManagerService readerManager;
    DateTimeFormatter dateFormat;


    private static ReadersController readersController;

    @FXML
    private ChoiceBox<String> searchList;
    @FXML
    private TextField searchQuery;
    @FXML
    private Label dateField;
    @FXML
    private Label elementsFound;
    @FXML
    private Label objectFound;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> genreColumn;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> languageColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableView<Reader> readerTable;
    @FXML
    private TableColumn<Reader, String> readerAddressColumn;
    @FXML
    private TableColumn<Reader, String> readerDateColumn;
    @FXML
    private TableColumn<Reader, String> readerEmailColumn;
    @FXML
    private TableColumn<Reader, Integer> readerIdColumn;
    @FXML
    private TableColumn<Reader, String> readerNameColumn;
    @FXML
    private TableColumn<Reader, Integer> readerPenaltyColumn;
    @FXML
    private TableColumn<Reader, String> readerPhoneNumberColumn;
    @FXML
    private TableView<Reader> readerTableAdd;
    @FXML
    private TableColumn<Reader, String> readerAddressColumnAdd;
    @FXML
    private TableColumn<Reader, String> readerDateColumnAdd;
    @FXML
    private TableColumn<Reader, String> readerEmailColumnAdd;
    @FXML
    private TableColumn<Reader, Integer> readerIdColumnAdd;
    @FXML
    private TableColumn<Reader, String> readerNameColumnAdd;
    @FXML
    private TableColumn<Reader, Integer> readerPenaltyColumnAdd;
    @FXML
    private TableColumn<Reader, String> readerPhoneNumberColumnAdd;
    @FXML
    private Button addReaderButton;
    @FXML
    private TextField addReaderName;
    @FXML
    private DatePicker bornDatePicker;
    @FXML
    private TextField addReaderPersonalId;
    @FXML
    private TextField addReaderAddressFirst;
    @FXML
    private TextField addReaderAddressSecond;
    @FXML
    private TextField addReaderEmail;
    @FXML
    private TextField addReaderPhoneNumber;
    @FXML
    private TextField selectedBookField;
    @FXML
    private Button deleteReaderButton;
    @FXML
    private Button payPenaltyButton;
    @FXML
    private Button lengthenDeadlineButton;
    @FXML
    private Button returnBookButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Tab searchTab;
    @FXML
    private Tab addTab;
    @FXML
    private Tab manageTab;
    @FXML
    private CheckBox filterHasBooksCheckBox;
    @FXML
    private CheckBox filterOverdueBooksCheckBox;
    @FXML
    private CheckBox filterUnpaidPenaltyCheckBox;
    @FXML
    private TextField readerInfoName;
    @FXML
    private TextField readerInfoBornDate;
    @FXML
    private TextField readerInfoPersonalId;
    @FXML
    private TextField readerInfoEmail;
    @FXML
    private TextField readerInfoId;
    @FXML
    private TextField readerInfoPenalty;


    @FXML
    private void initialize() {
        ReadersController.readersController = this;
        dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        dateField.setText(TimeManager.getInstance().getDate().format(dateFormat));

        authorManager = new AuthorManager();
        readerManager = new ReaderManager();

        filterList = new ArrayList<>();
        searchList.getItems().addAll(searchOptions);
        searchList.setValue(searchOptions[0]);
        searchList.setOnAction(e -> setSearchOption());

        searchQuery.setDisable(true);
        searchQuery.setOnKeyTyped(e -> loadReaders());
        addReaderButton.setDisable(true);

        addReaderName.setOnKeyTyped(e -> validateReader());
        addReaderPersonalId.setOnKeyTyped(e -> validateReader());
        addReaderAddressFirst.setOnKeyTyped(e -> validateReader());
        addReaderAddressSecond.setOnKeyTyped(e -> validateReader());
        addReaderEmail.setOnKeyTyped(e -> validateReader());
        addReaderPhoneNumber.setOnKeyTyped(e -> validateReader());

        for (TableColumn<?, ?> column : readerTable.getColumns()) {
            column.setReorderable(false);
        }
        for (TableColumn<?, ?> column : readerTableAdd.getColumns()) {
            column.setReorderable(false);
        }
        for (TableColumn<?, ?> column : bookTable.getColumns()) {
            column.setReorderable(false);
        }

        manageTab.setDisable(true);

        loadReaders();
    }

    private void loadBooks() {
        try {
            List<Book> readersBooks = readerManager.fetchBooks(selectedReader);

            ObservableList<Book> books = FXCollections.observableArrayList(readersBooks);

            elementsFound.setText(String.valueOf(books.size()));

            if (books.size() > 4 || books.isEmpty()) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("manyBooks"));
            } else if (books.size() > 1) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("coupleBooks"));
            } else {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("singleBook"));
            }

            authorManager.setSearch(new AuthorFindById());

            this.bookTable.setItems(books);
            this.idColumn.setCellValueFactory(bookData -> new SimpleIntegerProperty(bookData.getValue().getId()).asObject());
            this.titleColumn.setCellValueFactory(bookData -> new ReadOnlyStringWrapper(bookData.getValue().getTitle()));
            this.authorColumn.setCellValueFactory(bookData -> {
                int authorId = bookData.getValue().getAuthorId();
                Author author = authorManager.search(String.valueOf(authorId)).getFirst();
                return new ReadOnlyStringWrapper(author.getName());
            });
            this.genreColumn.setCellValueFactory(bookData -> new ReadOnlyStringWrapper(bookData.getValue().getGenre()));
            this.languageColumn.setCellValueFactory(bookData -> new ReadOnlyStringWrapper(bookData.getValue().getLanguage()));
            this.publisherColumn.setCellValueFactory(bookData -> new ReadOnlyStringWrapper(bookData.getValue().getPublisher()));

            this.bookTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectBook(newValue)));
        } catch (NullPointerException ex) {}

    }

    public void loadReaders() {
        try {
            List<Reader> foundReaders;

            if (searchType != null && !searchQuery.getText().isEmpty()) {
                readerManager.setSearch(searchType);
                foundReaders = readerManager.search(searchQuery.getText());
            } else {
                foundReaders = readerManager.get();
            }

            for (ReaderFilter filter: filterList) {
                foundReaders = filter.filter(foundReaders);
            }

            ObservableList<Reader> readers = FXCollections.observableArrayList(foundReaders);

            elementsFound.setText(String.valueOf(readers.size()));

            if (readers.size() != 1) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("manyReaders"));
            } else {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("singleReader"));
            }

            this.readerTable.setItems(readers);
            this.readerIdColumn.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getId()).asObject());
            this.readerNameColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getName()));
            this.readerDateColumn.setCellValueFactory(readerData -> new SimpleStringProperty(readerData.getValue().getBornDate().toString()));
            this.readerAddressColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getAddress()));
            this.readerPhoneNumberColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getPhoneNumber()));
            this.readerEmailColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getEmail()));
            this.readerPenaltyColumn.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getPenalty()).asObject());

            this.readerTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectReader(newValue)));
        }
        catch (NullPointerException ex) {}
    }

    private void loadReadersAdd() {
        try {
            ObservableList<Reader> readers = FXCollections.observableArrayList(readerManager.get());

            elementsFound.setText(String.valueOf(readers.size()));

            if (readers.size() != 1) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("manyReaders"));
            } else {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("singleReader"));
            }

            this.readerTableAdd.setItems(readers);
            this.readerIdColumnAdd.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getId()).asObject());
            this.readerNameColumnAdd.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getName()));
            this.readerDateColumnAdd.setCellValueFactory(readerData -> new SimpleStringProperty(readerData.getValue().getBornDate().toString()));
            this.readerAddressColumnAdd.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getAddress()));
            this.readerPhoneNumberColumnAdd.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getPhoneNumber()));
            this.readerEmailColumnAdd.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getEmail()));
            this.readerPenaltyColumnAdd.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getPenalty()).asObject());

            this.readerTableAdd.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectReader(newValue)));
        }
        catch (NullPointerException ex) {}
    }

    public static void refresh() {
        if (readersController != null) {
            readersController.tabChanged();
        }
    }

    @FXML
    private void selectBook(Book book) {
        if (book != null) {
            selectedBook = book;
            selectedBookField.setText(selectedBook.getTitle());
            validateManage();
        }
        bookTable.refresh();
    }

    @FXML
    private void selectReader(Reader reader) {
        if (reader != null) {
            selectedReader = reader;
            manageTab.setDisable(false);
        } else {
            manageTab.setDisable(true);
        }
        readerTable.refresh();
    }

    @FXML
    private void addReader() {
        ReaderFactoryService readerFactory = new ReaderFactory(readerManager);
        Reader reader = readerFactory.create(Map.of(
                "name", addReaderName.getText().substring(0,1).toUpperCase() + addReaderName.getText().substring(1),
                "bornDate", bornDatePicker.getValue().toString(),
                "personalId", addReaderPersonalId.getText(),
                "addressFirst", addReaderAddressFirst.getText(),
                "addressSecond", addReaderAddressSecond.getText(),
                "email", addReaderEmail.getText(),
                "phoneNumber", addReaderPhoneNumber.getText()
                ));
        readerManager.add(reader);
        addReaderName.clear();
        bornDatePicker.setValue(TimeManager.getInstance().getDate());
        addReaderPersonalId.clear();
        addReaderAddressFirst.clear();
        addReaderAddressSecond.clear();
        addReaderEmail.clear();
        addReaderPhoneNumber.clear();
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("readerAdded"),
                reader.getName() + " " +FxmlUtils.getResourceBundle().getString("readerGotAdded"));
        tabChanged();
    }

    @FXML
    private void tabChanged() {
        if (searchTab.isSelected()) {
            loadReaders();
            readerTable.refresh();
        } else if (addTab.isSelected()) {
            loadReadersAdd();
            readerTableAdd.refresh();
        } else if (manageTab.isSelected()) {
            if (deleteReaderButton != null && datePicker != null) {
                deleteReaderButton.setDisable(false);
                selectedDate = null;
                datePicker.setValue(null);
                datePicker.setDisable(false);
                selectedBookField.setStyle("");
            }
            loadBooks();
            loadReaderInfo();
            validateManage();
        }
        if (dateField != null) {
            dateField.setText(TimeManager.getInstance().getDate().format(dateFormat));
        }
    }

    private void setSearchOption() {
        if (searchList.getValue().equals(searchOptions[1])) {
            searchType = new ReaderFindByName();
            searchQuery.setText("");
            searchQuery.setDisable(false);
        } else if (searchList.getValue().equals(searchOptions[2])) {
            searchType = new ReaderFindByTitle();
            searchQuery.setText("");
            searchQuery.setDisable(false);
        } else {
            searchQuery.setText("");
            searchQuery.setDisable(true);
            searchType = null;
        }
        loadReaders();
    }

    @FXML
    private void setFilterHasBooks() {
        if (filterHasBooksCheckBox.isSelected()) {
            filterList.add(new ReaderFilterHasBooks());
        } else {
            filterList.removeIf(filter -> filter instanceof ReaderFilterHasBooks);
        }
        loadReaders();
    }

    @FXML
    private void setFilterOverdue() {
        if (filterOverdueBooksCheckBox.isSelected()) {
            filterList.add(new ReaderFilterHasOverdueBooks());
        } else {
            filterList.removeIf(filter -> filter instanceof ReaderFilterHasOverdueBooks);
        }
        loadReaders();
    }

    @FXML
    private void setFilterUnpaidPenalty() {
        if (filterUnpaidPenaltyCheckBox.isSelected()) {
            filterList.add(new ReaderFilterUnpaidPenalty());
        } else {
            filterList.removeIf(filter -> filter instanceof ReaderFilterUnpaidPenalty);
        }
        loadReaders();
    }

    @FXML
    private void validateReader() {
        int errors = 0;
        errors += checkEmpty(addReaderName);
        try {
            LocalDate currentDate = TimeManager.getInstance().getDate();
            if (bornDatePicker.getValue().isAfter(currentDate)) {
                bornDatePicker.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                errors++;
            } else {
                bornDatePicker.setStyle("");
            }
        } catch (NullPointerException ex) {
            bornDatePicker.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
            errors++;
        }
        errors += checkEmpty(addReaderPersonalId);
        try {
            Long.parseLong(addReaderPersonalId.getText());
        } catch (NumberFormatException ex) {
            addReaderPersonalId.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
            errors++;
        }
        errors += checkEmpty(addReaderAddressFirst);
        errors += checkEmpty(addReaderAddressSecond);
        errors += checkEmpty(addReaderEmail);
        errors += checkEmpty(addReaderPhoneNumber);

        addReaderButton.setDisable(errors != 0);
    }

    private void validateManage() {
        checkEmpty(selectedBookField);
        if (selectedBook == null) {
            selectedBookField.setText(FxmlUtils.getResourceBundle().getString("selectBookFirst"));
            selectedBookField.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
            returnBookButton.setDisable(true);
            lengthenDeadlineButton.setDisable(true);
        } else if (selectedBook.getCurrentReaderId() == null) {
            selectedBookField.setText(FxmlUtils.getResourceBundle().getString("bookIsReturned"));
            selectedBookField.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
            returnBookButton.setDisable(true);
            lengthenDeadlineButton.setDisable(true);
        } else {
            if (selectedDate != null && selectedDate.isAfter(selectedBook.getDeadline())) {
                datePicker.setStyle("");
                lengthenDeadlineButton.setDisable(false);
            } else {
                datePicker.setValue(selectedBook.getDeadline());
                datePicker.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
                lengthenDeadlineButton.setDisable(true);
            }
            selectedBookField.setStyle("");
            returnBookButton.setDisable(false);
        }


        if (selectedReader == null) {
            readerInfoName.setText(FxmlUtils.getResourceBundle().getString("readerGotDeleted"));
            readerInfoName.setStyle("-fx-background-color: darkred; -fx-text-fill: white");
            deleteReaderButton.setDisable(true);
            payPenaltyButton.setDisable(true);
        } else {
            if (selectedReader.getPenalty() == 0) {
                payPenaltyButton.setDisable(true);
            } else {
                payPenaltyButton.setDisable(false);
            }
            if (!readerManager.fetchBooks(selectedReader).isEmpty()) {
                deleteReaderButton.setDisable(true);
            } else {
                deleteReaderButton.setDisable(false);
            }
        }

    }

    private void loadReaderInfo() {
        readerInfoName.setText(selectedReader.getName());
        readerInfoBornDate.setText(selectedReader.getBornDate().toString());
        readerInfoPersonalId.setText(selectedReader.getPersonalId());
        readerInfoEmail.setText(selectedReader.getEmail());
        readerInfoId.setText(selectedReader.getId().toString());
        readerInfoPenalty.setText(selectedReader.getPenalty().toString());
    }

    @FXML
    private void getDate() {
        selectedDate = datePicker.getValue();
        validateManage();
    }

    @FXML
    private void deleteReader() {
        deleteReaderButton.setDisable(true);
        datePicker.setDisable(true);
        lengthenDeadlineButton.setDisable(true);
        returnBookButton.setDisable(true);
        payPenaltyButton.setDisable(true);
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("readerRemoved"),
                selectedReader.getName() + " " +FxmlUtils.getResourceBundle().getString("readerGotRemoved"));
        readerManager.remove(selectedReader);
        selectedReader = null;
        manageTab.setDisable(true);
        validateManage();
    }

    @FXML
    private void lengthenDeadline() {
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("deadlineLengthened"),
                selectedBook.getTitle() + " " + FxmlUtils.getResourceBundle().getString("ofReader") + " " + selectedReader.getName()
                        + FxmlUtils.getResourceBundle().getString("gotLengthenedTo") + selectedDate.toString()
        + " " + FxmlUtils.getResourceBundle().getString("from") + " " + selectedBook.getDeadline().toString());
        selectedBook.setDeadline(selectedDate);
        validateManage();
    }

    @FXML
    private void returnBook() {
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("bookReturned"),
                selectedReader.getName() + " " + FxmlUtils.getResourceBundle().getString("returned") + " " + selectedBook.getTitle());
        selectedBook.setDeadline(null);
        selectedBook.setCurrentReaderId(null);
        selectedBook = null;
        selectedBookField.clear();
        loadBooks();
        validateManage();
    }

    @FXML
    private void payPenalty() {
        NotificationUtils.notification(FxmlUtils.getResourceBundle().getString("penaltyPayed"),
                selectedReader.getName() + " " + FxmlUtils.getResourceBundle().getString("paid") + " " + selectedReader.getPenalty());
        selectedReader.setPenalty(0);
        loadReaderInfo();
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
}
