package com.pszumanski.libraryregister.managers.inputManagers.validators;

import java.util.Map;

public interface ValidatorService {

    boolean validate(Map<String, String> fields);

}
