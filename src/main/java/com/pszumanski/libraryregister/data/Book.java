package com.pszumanski.libraryregister.data;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "books", schema = "LIBRARY_REGISTER")
@Getter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    private Integer id;
    @NonNull
    private Integer authorId;
    private Integer currentReaderId;
    //TODO: Date of sql?
    private String deadline;
    @NonNull
    private String title;
    @NonNull
    private String publisher;
    //TODO: Year
    @NonNull
    private String publishYear;
    @NonNull
    private String isbn;
    @NonNull
    private String genre;
    @NonNull
    private String language;

}
