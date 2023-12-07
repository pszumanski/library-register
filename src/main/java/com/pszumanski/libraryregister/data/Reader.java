package com.pszumanski.libraryregister.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "readers", schema = "LIBRARY_REGISTER")
@Getter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reader {

    @Id
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    //TODO: Date util/sql?
    @NonNull
    private String bornDate;
    @NonNull
    private Integer personalId;
    @NonNull
    private String addressFirst;
    @NonNull
    private String addressSecond;
    //TODO: Email?
    @NonNull
    private String email;
    @NonNull
    private Integer phoneNumber;
}
