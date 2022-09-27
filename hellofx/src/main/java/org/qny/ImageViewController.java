package org.qny;

import javafx.fxml.FXML;

import java.io.IOException;

public class ImageViewController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
