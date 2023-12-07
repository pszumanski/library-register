package com.pszumanski.libraryregister.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors", schema = "LIBRARY_REGISTER")
@Getter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {

    @Id
    @NonNull
    private Integer id;

    @NonNull
    private String name;
    @NonNull
    private String bornDate;
    @NonNull
    private String deathDate;

}
