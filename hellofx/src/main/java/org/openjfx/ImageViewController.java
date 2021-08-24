package org.openjfx;

import javafx.fxml.FXML;

import java.io.IOException;

public class ImageViewController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
