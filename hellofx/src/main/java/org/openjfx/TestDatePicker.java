package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: TestDatePicker.java
 * @Description: 日期控件
 *
 * @Package org.openjfx
 * @Time: 2021-10-08 10:52
 */
public class TestDatePicker implements Initializable {
    /**
     * https://stackoverflow.com/questions/19027656/date-formatting-textbox/19028232#19028232
     * https://openjfx.io/javadoc/17/javafx.controls/javafx/scene/control/DatePicker.html
     */

    /**
     *  DatePicker datePicker = new DatePicker();
     *  datePicker.setOnAction(e -> {
     *      LocalDate date = datePicker.getValue();
     *      System.err.println("Selected date: " + date);
     *  });
     * @param url
     * @param resourceBundle
     */
    @FXML
    private DatePicker dp_get;
    @FXML
    private Label lb_dir;

    @FXML
    private Label lb_file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       dp_get.setOnAction(e -> {
           LocalDate date = dp_get.getValue();
           System.err.println("Selected date: " + date);
       });
    }
    @FXML
    public void showDCDialog(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File("e:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(App.getYourStage());
        System.out.println(selectedDirectory);
        if(null != selectedDirectory){
            lb_dir.setText(selectedDirectory.toString());
        }
    }
    @FXML
    public void showFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(App.getYourStage());
        System.out.println(selectedFile);
        if(null != selectedFile){
            lb_file.setText(selectedFile.toString());
        }
    }

}

/**
 * The DatePicker class extends TextField so you can use it wherever and however you would use a TextField.
 * The popup is activated when you click it with the mouse but you could also have it popup when it is focused (this was actually initially the case but I myself rarely used the popup so I made it less intrusive).
 * In the DatePicker class you can see on line 185 how the popup is activated on click.
 */