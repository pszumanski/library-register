package com.pszumanski.libraryregister.factory;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.dao.ReaderDao;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;

@RequiredArgsConstructor
public class ReaderFactory implements Factory<Reader> {

    private final ReaderDao readerManager;

    @Override
    public Reader create(Map<String, String> attributes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Reader reader = new Reader(
                (getMaxIndex() + 1),
                attributes.get("name"),
                LocalDate.parse(attributes.get("bornDate"), formatter),
                attributes.get("personalId"),
                attributes.get("addressFirst"),
                attributes.get("addressSecond"),
                attributes.get("email"),
                attributes.get("phoneNumber")
        );
        reader.setPenalty(0);
        return reader;
    }

    private Integer getMaxIndex() {
        return readerManager.get().stream()
                .map(reader -> reader.getId())
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
