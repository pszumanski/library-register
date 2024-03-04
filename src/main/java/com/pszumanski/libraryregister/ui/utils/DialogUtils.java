package com.pszumanski.libraryregister.ui.utils;

import com.pszumanski.libraryregister.service.TimeServiceImpl;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {

    public static void about() {
        ResourceBundle bundle = FxmlUtils.getResourceBundle();
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("aboutTitle"));
        informationAlert.setHeaderText(bundle.getString("aboutInfo"));
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> exitConfirmation() {
        ResourceBundle bundle = FxmlUtils.getResourceBundle();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("exitTitle"));
        confirmationAlert.setHeaderText(bundle.getString("exitHeader"));
        confirmationAlert.getButtonTypes().set(0, new ButtonType(bundle.getString("exitCancel"), ButtonBar.ButtonData.NO));
        confirmationAlert.getButtonTypes().set(1, new ButtonType(bundle.getString("exitSave"), ButtonBar.ButtonData.OK_DONE));
        confirmationAlert.getButtonTypes().add(new ButtonType(bundle.getString("exit"), ButtonBar.ButtonData.FINISH));
        return confirmationAlert.showAndWait();
    }

    public static LocalDate pickDate() {
        ResourceBundle bundle = FxmlUtils.getResourceBundle();
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("pickDate"));
        informationAlert.setHeaderText(bundle.getString("pickDate"));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(TimeServiceImpl.getInstance().getDate());
        informationAlert.getDialogPane().setContent(datePicker);
        informationAlert.showAndWait();
        /*informationAlert.getDialogPane().setContent(new DatePicker());
        informationAlert.showAndWait();
        DatePicker datePicker;
        datePicker = (DatePicker) informationAlert.getDialogPane().getChildren().stream()
                .filter(e -> e instanceof DatePicker)
                .toList()
                .getFirst();
        if (datePicker != null && datePicker.getValue() != null) {
            date = datePicker.getValue();
        }*/
        return datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now();
    }

    public static void error(String error) {
        ResourceBundle bundle = FxmlUtils.getResourceBundle();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("errorTitle"));
        errorAlert.setHeaderText(bundle.getString("errorMessage"));

        TextArea errorTextArea = new TextArea(error);
        errorTextArea.setEditable(false);
        errorAlert.getDialogPane().setContent(errorTextArea);

        errorAlert.showAndWait();
    }
}
