package com.pszumanski.libraryregister.managers.inputManagers.factories;

import com.pszumanski.libraryregister.data.Reader;
import com.pszumanski.libraryregister.managers.dataManagers.ReaderManagerService;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class ReaderFactory implements ReaderFactoryService {

    ReaderManagerService readerManager;

    @Override
    public Reader create(Map<String, String> attributes) {
        //        log.info("Next index: " + (getMaxIndex() + 1));
        return new Reader(
                (getMaxIndex() + 1),
                attributes.get("name"),
                attributes.get("bornDate"),
                Integer.parseInt(attributes.get("personalId")),
                attributes.get("addressFirst"),
                attributes.get("addressSecond"),
                attributes.get("email"),
                Integer.parseInt(attributes.get("phonenumber"))
        );
    }

    private Integer getMaxIndex() {
        return readerManager.get().stream()
                .sorted((reader1, reader2) -> {
                    return reader2.getId() - reader1.getId();
                })
                .toList()
                .getFirst()
                .getId();
    }
}
