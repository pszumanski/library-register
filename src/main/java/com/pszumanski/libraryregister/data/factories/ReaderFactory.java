package com.pszumanski.libraryregister.data.factories;

import com.pszumanski.libraryregister.data.objects.Reader;
import com.pszumanski.libraryregister.data.managers.ReaderManagerService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ReaderFactory implements ReaderFactoryService {

    ReaderManagerService readerManager;

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
        List<Reader> readers = readerManager.get().stream()
                .sorted((reader1, reader2) -> {
                    return reader2.getId() - reader1.getId();
                })
                .toList();
        return readers.isEmpty() ? 0 : readers.getFirst().getId();
    }
}