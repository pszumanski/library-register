package com.pszumanski.libraryregister.ui;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.*;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactory;
import com.pszumanski.libraryregister.managers.inputManagers.factories.BookFactoryService;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByAuthorName;
import com.pszumanski.libraryregister.strategy.bookSearch.BookFindByTitle;
import com.pszumanski.libraryregister.strategy.bookSearch.BookSearch;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class BooksController {

    private Book selectedBook;
    private Author selectedAuthor;
    private Reader selectedReader;
    String[] searchOptions = {"Search all", "Search by title", "Search by author"};
    private BookSearch searchType;
    private boolean valid;

    private static BooksController booksController;

    @FXML
    private ChoiceBox<String> searchList;
    @FXML
    private TextField searchQuery;
    @FXML
    private ChoiceBox<String> languageList;
    @FXML
    private CheckBox availableFilter;
    @FXML
    private Label date;
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
    private Tab searchTab;
    @FXML
    private Tab addTab;
    @FXML
    private Tab manageTab;

    @FXML
    private void initialize() {
        BooksController.booksController = this;
        searchList.getItems().addAll(searchOptions);
        searchList.setValue(searchOptions[0]);
        searchList.setOnAction(e -> setSearchOption());
        searchQuery.setDisable(true);
        searchQuery.setOnKeyTyped(e -> loadBooks());
        addBookButton.setDisable(true);
        addBookTitle.setOnKeyTyped(e -> validate());
        addBookPublisher.setOnKeyTyped(e -> validate());
        addBookPublishYear.setOnKeyTyped(e -> validate());
        addBookGenre.setOnKeyTyped(e -> validate());
        addBookLanguage.setOnKeyTyped(e -> validate());
    }

    public void loadBooks() {
        BookManagerService bookManager = new BookManager();
        List<Book> allBooks;

        if (searchType != null && !searchQuery.getText().isEmpty()) {
            bookManager.setSearch(searchType);
            allBooks = bookManager.search(searchQuery.getText());
        } else {
            allBooks = bookManager.get();
        }

        //TODO: FILTER

        ObservableList<Book> books = FXCollections.observableArrayList(allBooks);

        elementsFound.setText(String.valueOf(books.size()));

        //TODO: Adaptive book/books
        objectFound.setText(FxmlUtils.getResourceBundle().getString("books"));

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
    }

    public void loadAuthors() {
        AuthorManagerService authorManager = new AuthorManager();

        ObservableList<Author> authors = FXCollections.observableArrayList(authorManager.get());

        elementsFound.setText(String.valueOf(authors.size()));

        //TODO: Adaptive author
        objectFound.setText(FxmlUtils.getResourceBundle().getString("authors"));

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
    }

    public static void refresh() {
        booksController.tabChanged();
    }

    @FXML
    public void selectBook(Book book) {
        if (book != null) {
            selectedBook = book;
        }
    }

    @FXML
    public void selectAuthor(Author author) {
        if (author != null) {
            selectedAuthor = author;
            selectedAuthorField.setText(author.getName());
            validate();
        }
    }

    @FXML
    public void selectReader(Reader reader) {
        if (reader != null) {
            selectedReader = reader;
        }
    }

    @FXML
    public void manage() {

    }

    @FXML
    public void addBook() {
        BookManagerService bookManager = new BookManager();
        BookFactoryService bookFactory = new BookFactory(bookManager);
        Book book = bookFactory.create(Map.of(
                "title", addBookTitle.getText(),
                "authorId", selectedAuthor.getId().toString(),
                "publisher", addBookPublisher.getText(),
                "publishYear", addBookPublishYear.getText(),
                "genre", addBookGenre.getText(),
                "language", addBookLanguage.getText(),
                "isbn", addBookIsbn.getText()
                ));
        bookManager.add(book);
        addBookTitle.clear();
        addBookPublisher.clear();
        addBookPublishYear.clear();
        addBookGenre.clear();
        addBookLanguage.clear();
        addBookIsbn.clear();
        //TODO: komunikat że dodano
        tabChanged();
    }

    @FXML
    public void tabChanged() {
        if (searchTab.isSelected()) {
            loadBooks();
        } else if (addTab.isSelected()) {
            loadAuthors();
        } else if (manageTab.isSelected()) {
            loadReaders();
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

    public void filterAvailable(ActionEvent event) {
    }

    private void validate() {
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

}
