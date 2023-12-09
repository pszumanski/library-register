package com.pszumanski.libraryregister.data.objects;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "books", schema = "LIBRARY_REGISTER")
@Getter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @NonNull
    private Integer id;
    @NonNull
    private Integer authorId;
    @Setter
    private Integer currentReaderId;
    @Setter
    private LocalDate deadline;
    @NonNull
    private String title;
    @NonNull
    private String publisher;
    @NonNull
    private Year publishYear;
    @NonNull
    private String isbn;
    @NonNull
    private String genre;
    @NonNull
    private String language;

}
