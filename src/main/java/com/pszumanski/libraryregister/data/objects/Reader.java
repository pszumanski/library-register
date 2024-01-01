package com.pszumanski.libraryregister.data.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "readers", schema = "LIBRARY_REGISTER")
@Getter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reader implements Comparable {

    @Id
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate bornDate;
    @NonNull
    private String personalId;
    @NonNull
    private String addressFirst;
    @NonNull
    private String addressSecond;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    @Setter
    private Integer penalty;

    public String getAddress() {
        return addressFirst + ", " + addressSecond;
    }


    @Override
    public int compareTo(Object o) {
        return this.id - ((Reader) o).getId();
    }
}
