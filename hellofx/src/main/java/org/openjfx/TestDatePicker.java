package org.openjfx;

import com.feilong.core.DatePattern;
import com.feilong.core.date.DateUtil;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    @FXML
    private Label lb_file1;
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
//        Date date = DateUtils.parseDate("2007-02-14","yyyy-MM-dd");
        System.out.println(DateUtil.toString(new Date(), DatePattern.COMMON_DATE_AND_TIME));

    }

    /**
     * FileChooser fc=new FileChooser();
     * FileChooser.ExtensionFilter fileExtensions =new FileChooser.ExtensionFilter("music files","*.mp3", "*.mp4");
     * fc.getExtensionFilters().add(fileExtensions);
     * List<File> selectedFiles=fc.showOpenMultipleDialog(null);
     * if(selectedFiles!=null){
     *      for(int i=0;i<selectedFiles.size();i++){
     *            System.out.println(selectedFiles.get(i).toString());
     *      }
     * }
     */
    /**
     * 单选
     *
     */
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
//            lb_file.setText(selectedFile.toString());
            lb_file.textProperty().bind(Bindings.format("你已经选择了：",selectedFile.toString()));
        }
    }
    @FXML
    public void showFilesDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        List<File> selectedFiles =  fileChooser.showOpenMultipleDialog(App.getYourStage());
        System.out.println(selectedFiles);
        if(null != selectedFiles){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selectedFiles.size()-1; i++) {
                sb.append(selectedFiles.get(i));
                sb.append("\n");
            }
            sb.append(selectedFiles.get(selectedFiles.size()-1));
            lb_file1.setText(sb.toString());
        }
//        lb_file1.textProperty().bind(new CreateStringConverter<List<File>>(){
//
//            @Override
//            public String toString(List<File> object) {
//                return null;
//            }
//
//            @Override
//            public List<File> fromString(String string) {
//                return null;
//            }
//        });
    }

}

/**
 * The DatePicker class extends TextField so you can use it wherever and however you would use a TextField.
 * The popup is activated when you click it with the mouse but you could also have it popup when it is focused (this was actually initially the case but I myself rarely used the popup so I made it less intrusive).
 * In the DatePicker class you can see on line 185 how the popup is activated on click.
 */