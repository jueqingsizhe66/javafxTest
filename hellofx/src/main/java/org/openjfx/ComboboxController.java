package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

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
        // 可编辑 也可以在界面上设置
        cb_priority.setEditable(true);

        cb_priority.getItems().addAll(
                "Highest",
                "High",
                "Normal",
                "Low",
                "Lowest"
        );

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
