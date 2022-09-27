package org.qny;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ExtractInfoController {
    // 通过id注入id=tf01的信息
   //这里的Button对象有需要加@FXML注解，然后变量的名称为你刚才在FXML文件中声明的Button的id属性
    @FXML
    private TextField tf01;
    @FXML
    public void getMessage() throws IOException {
        System.out.println(tf01.getText());
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
