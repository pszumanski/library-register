package com.pszumanski.libraryregister.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {

    private static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static void about() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("aboutTitle"));
        informationAlert.setHeaderText(bundle.getString("aboutInfo"));
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> exitConfirmation() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("exitTitle"));
        confirmationAlert.setHeaderText(bundle.getString("exitHeader"));
        confirmationAlert.getButtonTypes().set(0, new ButtonType(bundle.getString("exitCancel"), ButtonBar.ButtonData.NO));
        confirmationAlert.getButtonTypes().set(1, new ButtonType(bundle.getString("exitSave"), ButtonBar.ButtonData.OK_DONE));
        confirmationAlert.getButtonTypes().add(new ButtonType(bundle.getString("exit"), ButtonBar.ButtonData.FINISH));
        return confirmationAlert.showAndWait();
    }
}
