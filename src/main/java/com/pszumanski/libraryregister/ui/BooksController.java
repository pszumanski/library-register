package com.pszumanski.libraryregister.ui;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.*;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactoryService;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;
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

public class BooksController {

    private Book selectedBook;
    private Author selectedAuthor;
    private Reader selectedReader;
    private LocalDate selectedDate;
    String[] searchOptions = {"Search all", "Search by title", "Search by author"};
    private BookSearch searchType;
    private List<BookFilter> filterList;
    private boolean valid;
    private BookManagerService bookManager;
    private AuthorManagerService authorManager;
    private ReaderManagerService readerManager;
    DateTimeFormatter dateFormat;


    private static BooksController booksController;

    @FXML
    private ChoiceBox<String> searchList;
    @FXML
    private TextField searchQuery;
    @FXML
    private ChoiceBox<String> languageList;
    @FXML
    private ChoiceBox<String> genreList;
    @FXML
    private CheckBox filterCheckBox;
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
    private TableColumn<Reader, Integer> readerPhoneNumberColumn;
    @FXML
    private Button addBookButton;
    @FXML
    private TextField addBookGenre;
    @FXML
    private TextField addBookIsbn;
    @FXML
    private TextField addBookLanguage;
    @FXML
    private TextField addBookPublishYear;
    @FXML
    private TextField addBookPublisher;
    @FXML
    private TextField addBookTitle;
    @FXML
    private TextField selectedAuthorField;
    @FXML
    private TextField selectedReaderField;
    @FXML
    private TextField lendBookTitle;
    @FXML
    private TextField lendBookAuthor;
    @FXML
    private TextField lendBookPublisher;
    @FXML
    private TextField lendBookPublishYear;
    @FXML
    private TextField lendBookLanguage;
    @FXML
    private TextField lendBookGenre;
    @FXML
    private TextField lendBookIsbn;
    @FXML
    private TextField lendBookId;
    @FXML
    private Button lendBookButton;
    @FXML
    private Button deleteBookButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Tab searchTab;
    @FXML
    private Tab addTab;
    @FXML
    private Tab manageTab;

    @FXML
    private void initialize() {
        BooksController.booksController = this;
        dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        dateField.setText(TimeManager.getInstance().getDate().format(dateFormat));

        bookManager = new BookManager();
        authorManager = new AuthorManager();
        readerManager = new ReaderManager();

        filterList = new ArrayList<>();
        searchList.getItems().addAll(searchOptions);
        searchList.setValue(searchOptions[0]);
        searchList.setOnAction(e -> setSearchOption());

        languageList.getItems().clear();
        languageList.getItems().add("Show all");
        languageList.getItems().addAll(new BookManager().fetchLanguages());
        languageList.setValue("Show all");
        languageList.setOnAction(e -> setLanguageFilter());

        genreList.getItems().clear();
        genreList.getItems().add("Any genre");
        genreList.getItems().addAll(new BookManager().fetchGenres());
        genreList.setValue("Any genre");
        genreList.setOnAction(e -> setGenreFilter());

        searchQuery.setDisable(true);
        searchQuery.setOnKeyTyped(e -> loadBooks());
        addBookButton.setDisable(true);

        addBookTitle.setOnKeyTyped(e -> validateBook());
        addBookPublisher.setOnKeyTyped(e -> validateBook());
        addBookPublishYear.setOnKeyTyped(e -> validateBook());
        addBookGenre.setOnKeyTyped(e -> validateBook());
        addBookLanguage.setOnKeyTyped(e -> validateBook());

        manageTab.setDisable(true);

        loadBooks();
    }

    public void loadBooks() {
        try {
            List<Book> allBooks;

            String currentLanguage = languageList.getValue();
            languageList.getItems().clear();
            languageList.getItems().add("Show all");
            languageList.getItems().addAll(new BookManager().fetchLanguages());
            languageList.setValue(currentLanguage);

            String currentGenre = genreList.getValue();
            genreList.getItems().clear();
            genreList.getItems().add("Any genre");
            genreList.getItems().addAll(new BookManager().fetchGenres());
            genreList.setValue(currentGenre);

            if (searchType != null && !searchQuery.getText().isEmpty()) {
                bookManager.setSearch(searchType);
                allBooks = bookManager.search(searchQuery.getText());
            } else {
                allBooks = bookManager.get();
            }

            for (BookFilter filter: filterList) {
                allBooks = filter.filter(allBooks);
            }

            ObservableList<Book> books = FXCollections.observableArrayList(allBooks);

            elementsFound.setText(String.valueOf(books.size()));

            if (books.size() > 4 || books.isEmpty()) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("manyBooks"));
            } else if (books.size() > 1) {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("coupleBooks"));
            } else {
                objectFound.setText(FxmlUtils.getResourceBundle().getString("singleBook"));
            }

            AuthorManager authorManager = new AuthorManager();
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
        } catch (Exception e) {}

    }

    public void loadAuthors() {
        ObservableList<Author> authors = FXCollections.observableArrayList(authorManager.get());

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
    }

    public void loadReaders() {
        ObservableList<Reader> readers = FXCollections.observableArrayList(readerManager.get());

        elementsFound.setText(String.valueOf(readers.size()));

        if (readers.size() != 1) {
            objectFound.setText(FxmlUtils.getResourceBundle().getString("manyReaders"));
        } else {
            objectFound.setText(FxmlUtils.getResourceBundle().getString("singleReader"));
        }

        this.readerTable.setItems(readers);
        this.readerIdColumn.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getId()).asObject());
        this.readerNameColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getName()));
        this.readerDateColumn.setCellValueFactory(readerData -> {
            return new SimpleStringProperty(readerData.getValue().getBornDate().toString());
        });
        this.readerAddressColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getAddress()));
        this.readerPhoneNumberColumn.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getPhoneNumber()).asObject());
        this.readerEmailColumn.setCellValueFactory(readerData -> new ReadOnlyStringWrapper(readerData.getValue().getEmail()));
        this.readerPenaltyColumn.setCellValueFactory(readerData -> new SimpleIntegerProperty(readerData.getValue().getPenalty()).asObject());

        this.readerTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectReader(newValue)));
    }

    public static void refresh() {
        booksController.tabChanged();
    }

    @FXML
    public void selectBook(Book book) {
        if (book != null) {
            selectedBook = book;
            manageTab.setDisable(false);
        } else {
            manageTab.setDisable(true);
        }
    }

    @FXML
    public void selectAuthor(Author author) {
        if (author != null) {
            selectedAuthor = author;
            selectedAuthorField.setText(author.getName());
            validateBook();
        }
    }

    @FXML
    public void selectReader(Reader reader) {
        if (reader != null) {
            selectedReader = reader;
            selectedReaderField.setText(reader.getName());
        }
        validateManage();
    }

    @FXML
    public void addBook() {
        BookFactoryService bookFactory = new BookFactory(bookManager);
        Book book = bookFactory.create(Map.of(
                "title", addBookTitle.getText().substring(0,1).toUpperCase() + addBookTitle.getText().substring(1),
                "authorId", selectedAuthor.getId().toString(),
                "publisher", addBookPublisher.getText().substring(0,1).toUpperCase() + addBookPublisher.getText().substring(1),
                "publishYear", addBookPublishYear.getText(),
                "genre", addBookGenre.getText().toLowerCase(),
                "language", addBookLanguage.getText().substring(0,1).toUpperCase() + addBookLanguage.getText().substring(1).toLowerCase(),
                "isbn", addBookIsbn.getText()
                ));
        bookManager.add(book);
        addBookTitle.clear();
        addBookPublisher.clear();
        addBookPublishYear.clear();
        addBookGenre.clear();
        addBookLanguage.clear();
        addBookIsbn.clear();
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("bookAdded"),
                book.getTitle() + " " +FxmlUtils.getResourceBundle().getString("gotAdded"));
        tabChanged();
    }

    @FXML
    public void tabChanged() {
        if (searchTab.isSelected()) {
            loadBooks();
        } else if (addTab.isSelected()) {
            loadAuthors();
        } else if (manageTab.isSelected()) {
            if (deleteBookButton != null && datePicker != null) {
                deleteBookButton.setDisable(false);
                selectedDate = null;
                datePicker.setValue(selectedDate);
                datePicker.setDisable(false);
            }
            loadBookInfo();
            loadReaders();
            validateManage();
        }
        if (dateField != null) {
            dateField.setText(TimeManager.getInstance().getDate().format(dateFormat));
        }
    }

    public void setSearchOption() {
        if (searchList.getValue().equals(searchOptions[1])) {
            searchType = new BookFindByTitle();
            searchQuery.setText("");
            searchQuery.setDisable(false);
        } else if ((searchList.getValue().equals(searchOptions[2]))) {
            searchType = new BookFindByAuthorName();
            searchQuery.setText("");
            searchQuery.setDisable(false);
        } else {
            searchQuery.setText("");
            searchQuery.setDisable(true);
            searchType = null;
        }
        loadBooks();
    }

    public void search(ActionEvent event) {
    }

    @FXML
    private void filterAvailable(ActionEvent event) {
        if (filterCheckBox.isSelected()) {
            filterList.add(new BookFilterAvailable());
        } else {
            for (BookFilter filter: filterList) {
                if (filter instanceof BookFilterAvailable) {
                    filterList.remove(filter);
                    break;
                }
            }
        }
        loadBooks();
    }

    private void setLanguageFilter() {
        if(languageList.getValue() == null) {
            return;
        }
        filterList.removeIf(filter -> filter instanceof BookFilterLanguage);
        if (!languageList.getValue().equals("Show all")) {
            filterList.add(new BookFilterLanguage(languageList.getValue()));
        }
        loadBooks();
    }

    private void setGenreFilter() {
        if(genreList.getValue() == null) {
            return;
        }
        filterList.removeIf(filter -> filter instanceof BookFilterGenre);
        if (!genreList.getValue().equals("Any genre")) {
            filterList.add(new BookFilterGenre(genreList.getValue()));
        }
        loadBooks();
    }

    private void validateBook() {
        //TODO: validate komunikaty / kolor
        valid = true;
        if (addBookTitle.getText().isEmpty()) {
            valid = false;
        }
        if (selectedAuthor == null) {
            valid = false;
        }
        if (addBookPublisher.getText().isEmpty()) {
            valid = false;
        }
        if (addBookPublishYear.getText().isEmpty()) {
            valid = false;
        }
        try {
            int currentYear = TimeManager.getInstance().getDate().getYear();
            if (Integer.parseInt(addBookPublishYear.getText()) > currentYear) {
                valid = false;
            }
        } catch (Exception ex) {
            valid = false;
        }
        if (addBookGenre.getText().isEmpty()) {
            valid = false;
        }
        if (addBookLanguage.getText().isEmpty()) {
            valid = false;
        }

        addBookButton.setDisable(!valid);
    }

    private void validateManage() {
        valid = true;
        if (selectedReader == null) {
            valid = false;
        }
        if (selectedDate == null || !selectedDate.isAfter(TimeManager.getInstance().getDate())) {
            valid = false;
        }
        lendBookButton.setDisable(!valid);
    }

    private void loadBookInfo() {
        lendBookTitle.setText(selectedBook.getTitle());
        int authorId = selectedBook.getAuthorId();
        AuthorManager authorManager = new AuthorManager();
        authorManager.setSearch(new AuthorFindById());
        lendBookAuthor.setText(authorManager.search(String.valueOf(authorId)).getFirst().getName());
        lendBookPublisher.setText(selectedBook.getPublisher());
        lendBookPublishYear.setText(String.valueOf(selectedBook.getPublishYear()));
        lendBookLanguage.setText(selectedBook.getLanguage());
        lendBookGenre.setText(selectedBook.getGenre());
        lendBookIsbn.setText(selectedBook.getIsbn());
        lendBookId.setText(selectedBook.getId().toString());
    }

    @FXML
    private void getDate() {
        selectedDate = datePicker.getValue();
        validateManage();
    }

    @FXML
    private void deleteBook() {
        deleteBookButton.setDisable(true);
        datePicker.setDisable(true);
        lendBookButton.setDisable(true);
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("bookRemoved"),
                selectedBook.getTitle() + " " +FxmlUtils.getResourceBundle().getString("gotRemoved"));
        bookManager.remove(selectedBook);
        selectedBook = null;
    }

    @FXML
    private void lendBook() {
        bookManager.lendBook(selectedBook, selectedReader, selectedDate);
        lendBookButton.setDisable(true);
        NotificationController.notification(FxmlUtils.getResourceBundle().getString("bookLent"),
                selectedBook.getTitle() + " " + FxmlUtils.getResourceBundle().getString("bookLentTo") + " " + selectedReader.getName()
        + " " + FxmlUtils.getResourceBundle().getString("bookLentUpTo") + " " + selectedDate.toString());
    }
}

/*
ReaderManagerService readerManager = new ReaderManager();
        ReaderFactoryService readerFactory = new ReaderFactory(readerManager);
        Reader reader = readerFactory.create(Map.of("name", "Maciej", "bornDate", "23-03-1989", "personalId", "58912734", "addressFirst", "Coconut Street 51/D", "addressSecond", "Gdansk", "email", "maciejunio@outlook.com", "phoneNumber", "589712341"));
        readerManager.add(reader);
 */