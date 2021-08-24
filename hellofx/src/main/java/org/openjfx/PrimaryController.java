package org.openjfx;

import javafx.fxml.FXML;

import java.io.IOException;

public class PrimaryController  {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToWarning() throws IOException {
        App.setRoot("alert");
    }

    @FXML
    private void switchToExtractInfo() throws IOException {
        App.setRoot("extractInfo");
    }

    @FXML
    private void switchToLongin() throws IOException {
        App.setRoot("longin");
    }

    @FXML
    private void switchToImageView() throws IOException {
        App.setRoot("imageView");
    }

    @FXML
    private void switchToChoicesButton() throws IOException {
        App.setRoot("testChoiceButton");
    }

    @FXML
    private void switchToMFbutton() throws IOException {
//        App.setRoot("materialsFX/ButtonsDemo");
    }

    @FXML
    private void switchToMFTableView() throws IOException {
//        App.setRoot("materialsFX/TableViewsDemo");
    }

    @FXML
    private void switchToMFTreeView() throws IOException {
        // 文件名 大小写敏感
//        App.setRoot("materialsFX/TreeViewsDemo");
    }

    @FXML
    private void switchToMFListView() throws IOException {
        // 文件名 大小写敏感
//        App.setRoot("materialsFX/ListViewsDemo");
    }
    @FXML
    private void switchToCombobox() throws IOException {
        App.setRoot("testCombobox");
    }

    @FXML
    private void switchToListview() throws IOException {
        App.setRoot("testListView");
    }

    @FXML
    private void switchToTableView() throws IOException {
        App.setRoot("testTableView");
    }

    @FXML
    private void switchToTreeView() throws IOException {
        App.setRoot("testTreeView");
    }

    @FXML
    private void switchToPersonalOverview() throws IOException {
        App.setRoot("personalOverview");
    }

    @FXML
    private void switchToImageViewerWithBlur() throws IOException {
        App.setRoot("imageViewWithBlur");
    }

    @FXML
    private void switchToSystemTrap() throws IOException {
        App.setRoot("minimizeSystemTray");

    }

    @FXML
    private void switchToJAVAFXCollections() throws IOException {
        App.setRoot("FXCollectionsTest");

    }

    @FXML
    private void switchToWebView() throws IOException {
        App.setRoot("testWebView");

    }

    @FXML
    private void switchToBindTheory() throws IOException {
        App.setRoot("bindTheoryTest");

    }
}

