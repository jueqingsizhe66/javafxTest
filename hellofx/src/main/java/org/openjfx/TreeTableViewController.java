package org.openjfx;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.util.Callback;
import org.data.Entity.Data3;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.ProgressBarTreeTableCell.*;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: TreeTableViewController.java
 * @Description: 测试TreeTableView相关功能
 * https://www.bilibili.com/video/BV1gb411h7dV/?spm_id_from=333.788.recommend_more_video.1
 * 107 课
 * https://www.bilibili.com/video/BV1sb411B7QT/?spm_id_from=333.788.recommend_more_video.9
 * @Package org.openjfx
 * @Time: 2021-09-19 17:08
 */
public class TreeTableViewController implements Initializable {

    Data3 d1 ;
    Data3  d2 ;
    Data3  d3 ;
    Data3  d4 ;
    Data3  d5 ;
    @FXML
    private TreeTableView ttv_data;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    private void changeD1Name(){
        d1.setName("hhello");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Data3必须是Public属性
         * Data1 和Data2无法使用@@
         */
        d1 = new Data3("yz",20,false);
        d2 = new Data3("yz2",30,true);
        d3 = new Data3("yz3",40,false);
        d4 = new Data3("yz4",50,true);
        d5 = new Data3("yz5",50,false);

        TreeItem<Data3> root = new TreeItem<>(d1);
        root.setExpanded(true);/*默认展开*/
        TreeItem<Data3> td2 = new TreeItem<>(d2);

        TreeItem<Data3> root11 = new TreeItem<>(d3);
        root11.setExpanded(true);/*默认展开*/
        TreeItem<Data3> td4 = new TreeItem<>(d4);
        TreeItem<Data3> td5 = new TreeItem<>(d5);

        root.getChildren().addAll(td2,root11);
        root11.getChildren().addAll(td4,td5);
        ttv_data.setRoot(root);
        /**
         * 各列等大小展示！ 效果比较好看些
         * TableView也有这种效果
         */
        ttv_data.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        /**
         * 默认单选  整列选择   也可以多选 单个cell选择
         */
        ttv_data.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ttv_data.getSelectionModel().setCellSelectionEnabled(true);

        /**
         * 打印选择的值！
         *
         */
        ttv_data.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Data3>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Data3>> observable, TreeItem<Data3> oldValue, TreeItem<Data3> newValue) {

                System.out.println(newValue.getValue().getAge());
            }
        });

        /**
         * 多选选择出来的结果
         * 多选row
         */
        ttv_data.getSelectionModel().getSelectedItems().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                ObservableList<TreeItem<Data3>> list =(ObservableList<TreeItem<Data3>>)observable ;
                System.out.println(list);
            }
        });

        /**
         * 多选 cell
         */
        ttv_data.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                ObservableList<TreeTablePosition<Data3,?>>  list = (ObservableList<TreeTablePosition<Data3,?>>)observable;
                for (int i = 0; i < list.size(); i++) {
                    TreeTablePosition<Data3, ?> ttp = list.get(i);
                    Object obj = ttp.getTableColumn().getCellData(ttp.getRow());
                    System.out.println("Row ="+ ttp.getRow() +" Column ="+ttp.getColumn()+" Value= "+obj.toString());
                }
            }
        });


        /*以下为TableView设置*/
        TreeTableColumn<Data3, String> name_column = new TreeTableColumn<>("姓名");
        TreeTableColumn<Data3, Integer> age_column = new TreeTableColumn<>("年龄");
        TreeTableColumn<Data3, Boolean> sex_column = new TreeTableColumn<>("性别");
        name_column.setPrefWidth(200);

        ttv_data.getColumns().add(name_column);
        ttv_data.getColumns().add(age_column);
        ttv_data.getColumns().add(sex_column);

        /**
         * 如果只加载姓名，那么就注释掉age_column和sex_column的工厂属性
         */
        name_column.setCellValueFactory(new TreeItemPropertyValueFactory<Data3,String>("name"));
//        name_column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Data3, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Data3, String> param) {
//                return param.getValue().getValue().nameProperty();
//            }
//        });
        age_column.setCellValueFactory(new TreeItemPropertyValueFactory<Data3,Integer>("age"));
       // sex_column.setCellValueFactory(new TreeItemPropertyValueFactory<Data3,Boolean>("sex"));
        /**
         * 以下写法也是可以   ValueFactory相对比较些
         */
        sex_column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Data3, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<Data3, Boolean> param) {
                return param.getValue().getValue().sexProperty();
            }
        });

        /**
         * 默认不可编辑，可以设置可编辑 编辑的风格 TreeTableCell有5种实现形式
         */
        ttv_data.setEditable(true);
//        name_column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
//        name_column.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn("A","YX","DF"));
        name_column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn("A","YX","DF"));
        /*以上为TreeView设置*/

        /**
         * 有意思 把所有的true 和false 转变为Checkbox写法
         */
        sex_column.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(new TreeTableColumn<Data3,Boolean>()));


    }
}

class Data1{
    private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
    private ReadOnlyIntegerWrapper age = new ReadOnlyIntegerWrapper();
    private ReadOnlyBooleanWrapper sex = new ReadOnlyBooleanWrapper();

    public Data1(String yz, int i, boolean b) {
        name.set(yz);
        age.set(i);
        sex.set(b);
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringWrapper nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public ReadOnlyIntegerWrapper ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public boolean isSex() {
        return sex.get();
    }

    public ReadOnlyBooleanWrapper sexProperty() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex.set(sex);
    }
}

class Data2{
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty age = new SimpleIntegerProperty();
    private SimpleBooleanProperty sex = new SimpleBooleanProperty();

    public Data2(String name1, int age, boolean sex) {
        this.name.set(name1);
        this.age.set(age);
        this.sex.set(sex);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public boolean isSex() {
        return sex.get();
    }

    public SimpleBooleanProperty sexProperty() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex.set(sex);
    }
}
