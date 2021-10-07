package org.openjfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: TestPagination.java
 * @Description: 测试
 * @Package org.openjfx
 * @Time: 2021-09-30 17:36
 */
public class TestPagination implements Initializable {

    @FXML
    private Pagination pg_board;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * https://openjfx.io/javadoc/17/index.html
         * Pagination最新版说明，注意显示格式比较完整！ 内容可读性较强
         *
         * java 开发手册
         * https://docs.oracle.com/en/java/javase/16/docs/api/index.html
         */
        pg_board.setStyle("-fx-background-color: #ffff55");
        pg_board.setPrefWidth(599);
        pg_board.setPrefHeight(599);
//        pg_board.setPageCount(Pagination.INDETERMINATE);
//        pg_board.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pg_board.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("NewValue is "+ newValue);
            }
        });
        pg_board.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {

                if (param==0) {
                    HBox box = new HBox();
                    box.setStyle("-fx-background-color: #FF7F24");
                    return box;
                }else{
                    return new Button("Button"+param.intValue());
                }
            }
        });
    }
}
