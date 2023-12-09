package com.pszumanski.libraryregister.ui;

import com.pszumanski.libraryregister.data.objects.Author;
import com.pszumanski.libraryregister.data.objects.Book;
import com.pszumanski.libraryregister.managers.dataManagers.AuthorManager;
import com.pszumanski.libraryregister.managers.dataManagers.BookManager;
import com.pszumanski.libraryregister.strategy.authorSearch.AuthorFindById;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class BooksController {

    private ObservableList<Book> books;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private Label date;

    @FXML
    private Label elementsFound;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> languageColumn;

    @FXML
    private TableColumn<Book, String> manageColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    public void loadBooks() {
        List<Book> allBooks = new BookManager().get();

        //TODO: SEARCH AND FILTER


        books = FXCollections.observableArrayList(allBooks);

        //TODO: książek/książkę/książki/książęk
        elementsFound.setText(String.valueOf(books.size()));

        AuthorManager authorManager = new AuthorManager();
        authorManager.setSearch(new AuthorFindById());

        this.booksTable.setItems(books);
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
        this.manageColumn.setCellValueFactory(bookData -> new ReadOnlyStringWrapper(bookData.getValue().getPublisher()));

        this.booksTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            select(newValue);
        }));
    }

    @FXML
    public void select(Book book) {
        if (book != null) {
            //TODO: Open select book view =)
        }
    }

}
