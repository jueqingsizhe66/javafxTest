package org.qny;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class WebViewController implements Initializable {


    @FXML
    private AnchorPane ap_web;
    @FXML
    private WebView wv_doge;
    WebEngine we;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        we= wv_doge.getEngine();
        we.load("https://www.dogedoge.com");
        wv_doge.setFontScale(0.5);
        wv_doge.setZoom(1.2);

//        wv_doge.setContextMenuEnabled(false);  //菜单右键禁用
        wv_doge.prefHeightProperty().bind(ap_web.heightProperty());
        wv_doge.prefWidthProperty().bind(ap_web.widthProperty());

    }
}
