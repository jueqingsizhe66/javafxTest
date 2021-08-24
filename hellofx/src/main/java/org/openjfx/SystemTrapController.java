package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class SystemTrapController{

    @FXML
    private AnchorPane ap_sys;
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    // 这时候的关掉不代表真正的关掉！！！
    @FXML
    private void miniTrap(){
        MySystemTray.getInstance().listen((Stage)ap_sys.getScene().getWindow());
    }

    //    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        //https://blog.csdn.net/cdc_csdn/article/details/80712726
//    }

}
