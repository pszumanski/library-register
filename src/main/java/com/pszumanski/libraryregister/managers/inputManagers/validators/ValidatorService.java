package com.pszumanski.libraryregister.managers.inputManagers.validators;

import java.util.Map;

public interface ValidatorService {

    Map<String, String> validate(Map<String, String> fields);

}
