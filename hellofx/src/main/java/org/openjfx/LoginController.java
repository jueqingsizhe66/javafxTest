package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static org.openjfx.App.loadFXML;

public class LoginController {
    //https://www.yiibai.com/javafx/javafx_filechooser.html
    @FXML
    public void open() throws IOException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter mdFilter = new FileChooser.ExtensionFilter("markdown Files (*.md)", "*.md");
        fileChooser.getExtensionFilters().addAll(txtFilter,mdFilter);
        Stage stage1= new Stage();
        Scene scene = new Scene(loadFXML("longin"));
        stage1.setScene(scene);

        File file = fileChooser.showOpenDialog(stage1);
        System.out.printf("filename is : " + file);
    }


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
