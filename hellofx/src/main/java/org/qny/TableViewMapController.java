package org.qny;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: TableViewMapController.java
 * @Description: 联系TableViewMap功能
 * @Package org.openjfx
 * @Time: 2021-09-28 22:09
 */
public class TableViewMapController implements Initializable{

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TableView<HashMap<String,SimpleStringProperty>> ttcm_view;

    @FXML
    private TableColumn<HashMap<String,SimpleStringProperty>,String> ttcm_name ;

    @FXML
    private TableColumn<HashMap<String,SimpleStringProperty>,String> ttcm_age ;
    @FXML
    private TableColumn<HashMap<String,SimpleStringProperty>,String> ttcm_sex ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * 第一步你会怎么做 ，建立Map
         */
        HashMap<String,SimpleStringProperty> mpq = new HashMap<String,SimpleStringProperty>();
        mpq.put("name",new SimpleStringProperty("340042"));
        mpq.put("age",new SimpleStringProperty("39014"));
        mpq.put("sex",new SimpleStringProperty("false"));


        HashMap<String,SimpleStringProperty> mpq2 = new HashMap<String,SimpleStringProperty>();
        mpq2.put("name",new SimpleStringProperty("yzl"));
        mpq2.put("age",new SimpleStringProperty("02"));
        mpq2.put("sex",new SimpleStringProperty("true"));

        ObservableList<HashMap<String,SimpleStringProperty>> list = FXCollections.observableArrayList();
        list.add(mpq);
        list.add(mpq2);

        ttcm_view.setItems(list);

        /**
         * 写法1 简化写法
         */
        ttcm_name.setCellValueFactory(new MapValueFactory("name"));
        /**
         * 写法2  原理写法
         * HashMap<String,SimpleStringProperty 转化为ObservableValue<String>类型
         */
//        ttcm_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap<String, SimpleStringProperty>, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<HashMap<String, SimpleStringProperty>, String> param) {
//                /**
//                 * 返回的其实就是SimpleStringPropertyProperty
//                 */
//                return param.getValue().get("name");
//            }
//        });


        ttcm_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap<String, SimpleStringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HashMap<String, SimpleStringProperty>, String> param) {
                /**
                 * 返回的其实就是SimpleStringPropertyProperty
                 */
                return param.getValue().get("age");
            }
        });


        ttcm_sex.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap<String, SimpleStringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HashMap<String, SimpleStringProperty>, String> param) {
                /**
                 * 返回的其实就是SimpleStringPropertyProperty
                 */
                return param.getValue().get("sex");
            }
        });

    }

}
