package com.pszumanski.libraryregister.ui.utils;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public final class NotificationUtils {

    private NotificationUtils() {}

    public static void notification(String message, String title) {
        Notifications notification = Notifications.create();
        notification.title(message);
        notification.text(title);
        notification.hideAfter(Duration.seconds(5));
        notification.darkStyle();
        notification.position(Pos.BOTTOM_LEFT);
        notification.show();
    }
}