package org.openjfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.data.Entity.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComboboxController implements Initializable {

    @FXML
    private ComboBox cb_name;
    @FXML
    private ComboBox cb_email;
    @FXML
    private ComboBox cb_priority;
    @FXML
    private Label lb_text;
    @FXML
    private TextField tf_subject;
    @FXML
    private TextArea ta_body;

    @FXML
    private Button bt_modified;
    @FXML
    private ComboBox<Student> bt_stu;

    private int index;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

//    https://stackoverflow.com/questions/13032257/combo-box-javafx-with-fxml
// https://blog.csdn.net/moakun/article/details/83050547  如何使用combobox
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options  = FXCollections.observableArrayList(
                "漳州",
                "厦门",
                "福州",
                "南平"
        );
//        cb_name.setValue("漳州");
        cb_name.setItems(options);
        // 或者通过getItems.addAll("漳州","福州"); 进行添加

        cb_email.getItems().addAll(
                "jacob.smith@example.com",
                "isabella.johnson@example.com",
                "ethan.williams@example.com",
                "emma.jones@example.com",
                "michael.brown@example.com"
        );
        cb_email.setPromptText("请输入邮箱");
        /**
         * 出现一个下拉框
         */
        cb_email.setVisibleRowCount(4);
        // 可编辑 也可以在界面上设置
        cb_priority.setEditable(true);

        cb_priority.getItems().addAll(
                "Highest",
                "High",
                "Normal",
                "Low",
                "Lowest"
        );
        cb_priority.setVisibleRowCount(3);

        cb_priority.setValue("Normal");
        cb_priority.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<String>() {
                            {
                                super.setPrefWidth(100);
                            }
                            @Override public void updateItem(String item,
                                                             boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    if (item.contains("High")) {
                                        setTextFill(Color.RED);
                                    }
                                    else if (item.contains("Low")){
                                        setTextFill(Color.GREEN);
                                    }
                                    else {
                                        setTextFill(Color.BLACK);
                                    }
                                }
                                else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                });

        Student s1 = new Student("zh","299",10,"15101077342","回龙观");
        Student s2 = new Student("zh2","299",10,"15101077342","回龙观");
        Student s3 = new Student("zh3","299",10,"15101077342","回龙观");
        Student s4 = new Student("zh4","299",10,"15101077342","回龙观");
        Student s5 = new Student("zh5","299",10,"15101077342","回龙观");

        //bt_stu = new ComboBox<Student>();
        bt_stu.setPrefWidth(200);
        bt_stu.getItems().addAll(s1,s2,s3,s4,s5);
        bt_stu.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student object) {
                if (object==null) {
                    return "Please Select option"; /*为空时候进行判断  少不了the little scheme*/
                }
                String value = object.getSname()+" - " + object.getSage()+" - "+object.getSphone();
                return value;
            }

            @Override
            public Student fromString(String string) {
                return null;
            }
        });
        bt_stu.setVisibleRowCount(3);

        bt_stu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                index= newValue.intValue();
                System.out.println(" your new value is "+index);
            }
        });

        bt_modified.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int temp =index;
//                System.out.println(bt_stu.getItems().get(index).getSname());
//                System.out.println("Current Index is "+ index);
                bt_stu.getItems().get(temp).setSname("修改的人民");
                bt_stu.getSelectionModel().clearSelection(); /*index 发生改变 变为-1. 所以需要加上temp*/
                bt_stu.getSelectionModel().select(temp);
                System.out.println(bt_stu.getItems().get(index).getSname());
            }
        });

    }

    @FXML
    public void emailSentNotify(){
        if (cb_email.getValue() != null &&
                !cb_email.getValue().toString().isEmpty()){
            lb_text.setText("Your message was successfully sent"
                    + " to " + cb_email.getValue());
            cb_email.setValue(null);
            if (cb_priority.getValue() != null &&
                    !cb_priority.getValue().toString().isEmpty()){
                cb_priority.setValue(null);
            }
            tf_subject.clear();
            ta_body.clear();
        }
        else {
            lb_text.setText("You have not selected a recipient!");
        }
    }

}
