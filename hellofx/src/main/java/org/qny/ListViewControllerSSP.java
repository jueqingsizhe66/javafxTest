package org.qny;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * 第64课 https://www.bilibili.com/video/BV1Lt411z7SL?spm_id_from=333.999.0.0
 * 第53课 https://www.bilibili.com/video/BV1Zt411v7xd?spm_id_from=333.999.0.0
 */
public class ListViewControllerSSP implements Initializable {

    @FXML
    private ListView lv_stu;
    private SimpleStringProperty s1 ;
    private SimpleStringProperty s2 ;
    private SimpleStringProperty s3 ;
    private SimpleStringProperty s4 ;
    private SimpleStringProperty s5 ;
    /**
     * 好处是设置监听  不能针对控件直接
     */
    private ObservableList<SimpleStringProperty> oblist;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    //region 初始化
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * oblist获取listview的可观察数据源
         * Callback的目的就是我要监听Property对象，说明切实Property对象 都实现了Callback接口
         * https://www.bilibili.com/video/BV1db411k7tt/?spm_id_from=333.788.recommend_more_video.0
         * 只要我的数据源列表实现了Callback功能，那么其内部管理的Property内容发生变化，我也会第一时间发现，报警.
         * 也就是Callback相当于在Property和oblist之间建立了所有需要的电话线
         *
         *
         */

        oblist = FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty param) {
                SimpleStringProperty[] arr = new SimpleStringProperty[]{param};
                return arr;
            }
        });
        lv_stu.setItems(oblist);
        s1=new SimpleStringProperty("Ironman");
        s2=new SimpleStringProperty("Titanic");
        s3=new SimpleStringProperty("Piano");
        s4=new SimpleStringProperty("Pie");
        s5=new SimpleStringProperty("小丑");
        oblist.addAll(s1,s2,s3,s4,s5);
//        lv_stu.getItems().add(oblist); /*不需要添加直接绑定*/
//        lv_stu.getItems().addAll("IronMan",
//                "Titanic",
//                "Piano",
//                "Pie",
//                "小丑");
        lv_stu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv_stu.setEditable(true);

        lv_stu.setCellFactory(TextFieldListCell.forListView(new StringConverter<SimpleStringProperty>() {

            @Override
            public String toString(SimpleStringProperty object) {

                return object.getValue();
            }

            @Override
            public SimpleStringProperty fromString(String string) {
                return null;
            }
        }));
        /**
         * oblist可配置监听器  观察内容发生改变
         */
        oblist.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                /**
                 * 可以监听oblist发生变化的事件
                 *  2021-09-20 18:09
                 */
//                System.out.println(c);
                if (c.next()) {
                    if (c.wasAdded()) {
                        System.out.println("Insert Action");
                    }
                    if (c.wasRemoved()) {
                        System.out.println("Remove Action");
                    }
                    if (c.wasUpdated()) {
                        System.out.println("Update Action");
                    }
                    if (c.wasReplaced()) {
                        System.out.println("Replace Action");
                    }
                    if (c.wasPermutated()) {
                        System.out.println("Sort Action");
                    }
                }
            }

        });
        /**
         * 打印所有listview的数据源！
         * 数据源一发生改变就整体输出！
         */
        oblist.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                ObservableList<SimpleStringProperty> data = (ObservableList<SimpleStringProperty>) observable;
                data.forEach(new Consumer<SimpleStringProperty>() {

                    @Override
                    public void accept(SimpleStringProperty simpleStringProperty) {
                        System.out.println(simpleStringProperty.getValue());
                    }
                });
            }
        });

    }
    //endregion

    //region 展示列表
    @FXML
    public void showMovieList(){
       if(!lv_stu.getSelectionModel().getSelectedItems().isEmpty()){
           String message = "";
           ObservableList<String> movies;
           movies = lv_stu.getSelectionModel().getSelectedItems();

           for (String movie : movies) {
               message += movie + "\n";
           }
           System.out.println(message);
       }else{
           System.out.println("You have not selected movie(s) what you wanno watch");
       }
    }
    //endregion
    //region 插入列表
    @FXML
    public void insertList()
    {
       oblist.add(0,new SimpleStringProperty("hello"));

    }
    //endregion

    //region 更新列表
    @FXML
    public void updateList(){
        /**
         * 替换其实是先添加 后删除 然后替换o
         * oblist.add(0,"update");
         * oblist.remove(0+1);
         */
//        oblist.set(0,new SimpleStringProperty("update"));
        /**
         * 因为是SimpleStringProperty对象，有另外一种写法
         */
        s1.set("hellloProperty");

        // 可以先注释掉 ，应为添加了Callback函数
//        lv_stu.refresh();/*加载所有最新元素 效率不好！！！！！！！如果上万条数据呐  但是listview顶多加上一整屏幕*/
        /**
         * 之所以需要refresh（） 因为当前oblist只管对象层级，不管属性层级，如果oblist要是能够同时管属性层级不就ok了
         */

        /**
         * 方法2 解决属性层级监控的方法，添加listview的属性层级的Callback， 对应类型发生改变，则执行相应操作，
         */
    }
    //endregion

    //region 删除列表
    @FXML
    public void DeleteList(){
        oblist.remove(0);

    }
    //endregion

    //region 排序列表
    @FXML
    public void sortList(){
       oblist.sort(new Comparator<SimpleStringProperty>() {
           @Override
           public int compare(SimpleStringProperty o1, SimpleStringProperty o2) {
               return o2.getValue().compareTo(o1.getValue());
           }
       });
    }
    //endregion
}
