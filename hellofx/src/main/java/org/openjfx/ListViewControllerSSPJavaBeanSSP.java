package org.openjfx;

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

/**
 * 一个逐条展示记录的列表控件
 */
public class ListViewControllerSSPJavaBeanSSP implements Initializable {

    @FXML
    private ListView lv_stu;
    private DataSSP s1 ;
    private DataSSP s2 ;
    private DataSSP s3 ;
    private DataSSP s4 ;
    private DataSSP s5 ;
    /**
     * 好处是设置监听  不能针对控件直接
     */
    private ObservableList<DataSSP> oblist;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

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

        /**
         * Callback必须返回Observable[] 数组，也就是里面地对象是实现Observable接口的Property对象
         */
//        oblist = FXCollections.observableArrayList(new Callback<DataSSP, Observable[]>() {
//            @Override
//            public Observable[] call(DataSSP param) {
//                Observable[] arr = new DataSSP[]{param};
//                return arr;
//            }
//        });
//        lv_stu.setItems(oblist);
        oblist = FXCollections.observableArrayList(new Callback<DataSSP, Observable[]>() {
            @Override
            public Observable[] call(DataSSP param) {
                SimpleStringProperty[] arr = new SimpleStringProperty[]{param.nameProperty(),param.ageProperty()};
                return arr;
            }
        });
        oblist = lv_stu.getItems();
        s1=new DataSSP(new SimpleStringProperty("Ironman"),new SimpleStringProperty("30"));
        s2=new DataSSP(new SimpleStringProperty("Titanic"),new SimpleStringProperty("302"));
        s3=new DataSSP(new SimpleStringProperty("Piano"),new SimpleStringProperty("149"));
        s4=new DataSSP(new SimpleStringProperty("Pie"),new SimpleStringProperty("391"));
        s5=new DataSSP(new SimpleStringProperty("小丑"),new SimpleStringProperty("39"));
        oblist.addAll(s1,s2,s3,s4,s5);
//        lv_stu.getItems().add(oblist); /*不需要添加直接绑定*/
//        lv_stu.getItems().addAll("IronMan",
//                "Titanic",
//                "Piano",
//                "Pie",
//                "小丑");
        lv_stu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv_stu.setEditable(true);

        lv_stu.setCellFactory(TextFieldListCell.forListView(new StringConverter<DataSSP>() {

            @Override
            public String toString(DataSSP object) {

                return object.getName() + " -- "+ object.getAge();
            }

            @Override
            public DataSSP fromString(String string) {
                return null;
            }
        }));
        /**
         * oblist可配置监听器  观察内容发生改变
         */
        oblist.addListener(new ListChangeListener<DataSSP>() {
            @Override
            public void onChanged(Change<? extends DataSSP> c) {
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
                ObservableList<DataSSP> data = (ObservableList<DataSSP>) observable;
                data.forEach(new Consumer<DataSSP>() {

                    @Override
                    public void accept(DataSSP simpleStringProperty) {
                        System.out.println(simpleStringProperty.getName()+ "--" +simpleStringProperty.getAge());
                    }
                });
            }
        });

    }

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
    @FXML
    public void insertList()
    {
       oblist.add(0,new DataSSP(new SimpleStringProperty("hello"),new SimpleStringProperty("430")));

    }

    @FXML
    public void updateList(){
        /**
         * 替换其实是先添加 后删除 然后替换o
         * oblist.add(0,"update");
         * oblist.remove(0+1);
         */
//        oblist.set(0,new DataSSP("update"));
        /**
         * 因为是DataSSP对象，有另外一种写法
         */
//        s1.setName("hellloProperty");
        oblist.get(lv_stu.getSelectionModel().getSelectedIndex()).setName("hello");
        /**
         *
         * javabeanSSP模式下需要加上
         * 因为已经有Property属性了，只要添加到Callback数组即可
         */
        // 可以先注释掉 ，应为添加了Callback函数
//        lv_stu.refresh();/*加载所有最新元素 效率不好！！！！！！！如果上万条数据呐  但是listview顶多加上一整屏幕*/
        /**
         * 之所以需要refresh（） 因为当前oblist只管对象层级，不管属性层级，如果oblist要是能够同时管属性层级不就ok了
         */

        /**
         * 方法2 解决属性层级监控的方法，添加listview的属性层级的Callback， 对应类型发生改变，则执行相应操作，
         */
    }

    @FXML
    public void DeleteList(){
        oblist.remove(0);

    }

    @FXML
    public void sortList(){
       oblist.sort(new Comparator<DataSSP>() {
           @Override
           public int compare(DataSSP o1, DataSSP o2) {
               return o2.getName().compareTo(o1.getName());
           }
       });
    }
}

class DataSSP{
    private SimpleStringProperty name;
    private SimpleStringProperty age;

    /**
     * 一般写成 String类型好些
     * @param name
     * @param age
     */
    public DataSSP(SimpleStringProperty name, SimpleStringProperty age) {
        this.name = name;
        this.age = age;
    }

    public DataSSP(SimpleStringProperty name) {
        this.name = name;
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

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }
}

