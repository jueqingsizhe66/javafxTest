package org.openjfx;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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
public class ListViewControllerJavaBean implements Initializable {

    @FXML
    private ListView lv_stu;
    private DataP s1 ;
    private DataP s2 ;
    private DataP s3 ;
    private DataP s4 ;
    private DataP s5 ;
    /**
     * 好处是设置监听  不能针对控件直接
     */
    private ObservableList<DataP> oblist;

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
//        oblist = FXCollections.observableArrayList(new Callback<DataP, Observable[]>() {
//            @Override
//            public Observable[] call(DataP param) {
//                Observable[] arr = new DataP[]{param};
//                return arr;
//            }
//        });
//        lv_stu.setItems(oblist);
        oblist = lv_stu.getItems();
        s1=new DataP("Ironman","30");
        s2=new DataP("Titanic","302");
        s3=new DataP("Piano","149");
        s4=new DataP("Pie","391");
        s5=new DataP("小丑","39");
        oblist.addAll(s1,s2,s3,s4,s5);
//        lv_stu.getItems().add(oblist); /*不需要添加直接绑定*/
//        lv_stu.getItems().addAll("IronMan",
//                "Titanic",
//                "Piano",
//                "Pie",
//                "小丑");
        lv_stu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv_stu.setEditable(true);

        /**
         * 设置ListView风格
         * LIstView，自定义选项样式，自定义可编辑状态的样式
         * https://www.bilibili.com/video/BV1Kb411C7kR
         */
        /**
         * 如果不设置setCellFactory默认打印list对象出来
         */
        /**
        lv_stu.setCellFactory(TextFieldListCell.forListView(new StringConverter<DataP>() {

            @Override
            public String toString(DataP object) {

                return object.getName() + " -- "+ object.getAge();
            }

            @Override
            public DataP fromString(String string) {
                return null;
            }
        }));
        */

        /**
         * 自定义风格
         */
        lv_stu.setCellFactory(new Callback<ListView<DataP>, ListCell<DataP>>() {

            /**
             * 不能放在call函数内部， 而应该放在Callback函数体内！
             * call执行真正过程动作
             * listview公共变量
             */
            int index=0;
            DataP temp =null; /*方便setOnEditStart 和startEdit配合使用*/
            ListCell<DataP> cell =null; /*为了可以在starEdit中使用*/
            @Override
            public ListCell<DataP> call(ListView<DataP> param) {
                /**
                 * 每一次执行startEdit都会执行该动作，获取当前选择的索引,不断修改temp值
                 * 加载当前选项项
                 */
                param.setOnEditStart(new EventHandler<ListView.EditEvent<DataP>>() {
                    @Override
                    public void handle(ListView.EditEvent<DataP> event) {
                        index = event.getIndex();
                        temp =param.getItems().get(index);
                    }
                });

                /**
                 * 资源可以共用
                 * 加载资源
                 */
                ImageView iv = new ImageView(new Image(App.class.getResourceAsStream("avatar.jpg")));
                iv.setPreserveRatio(true);
                iv.setFitHeight(30);



                ListCell<DataP> listCell = new ListCell<DataP>(){

                    public Button getButton(DataP temp){

                        Button bu = new Button(temp.getName() + "button");
                        bu.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println(temp.getName() + "------------"+ temp.getAge());
                            }
                        });
                        return bu;
                    }
                    /**
                     * 为了让startEdit获取当前选择的对象，增加start
                     */
                    @Override
                    public void startEdit() {
                        cell = this; /*无法直接使用listCell 只能间接cell = this 类似this.setGraphics*/
                        super.startEdit();

                        /**
                         * startEdit
                         * 2021-10-08 0:21   再次学习
                         */

                        HBox hbox = new HBox();
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        Button bu = getButton(temp);
                        TextField tb_name = new TextField(temp.getName());
                        TextField tb_age = new TextField(temp.getAge());
                        tb_name.setPrefWidth(100);
                        tb_age.setPrefWidth(100);
                        hbox.getChildren().addAll(iv, bu, tb_name, tb_age);
                        this.setGraphic(hbox);
                        /**
                         * name编辑完后执行commitEdit动作
                         */
                        tb_name.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
                                    if (tb_name.getText().equals("")) {
                                        /*无法直接使用this*/
                                        cell.commitEdit(temp);
                                    }else{
                                        temp.setName(tb_name.getText());
                                        temp.setAge(tb_age.getText());
                                        cell.commitEdit(temp);
                                    }

                                }

                            }
                        });

                        /**
                         * 开始编辑之后enter 之后会发出什么动作？
                         */
                        tb_age.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
                                    if (tb_age.getText().equals("")) {
                                        /*无法直接使用this*/
                                        cell.commitEdit(temp);
                                    }else{
                                        temp.setName(tb_name.getText());
                                        temp.setAge(tb_age.getText());
                                        cell.commitEdit(temp);
                                    }

                                }

                            }
                        });
                    }

                    /**
                     * 如果取消编辑？
                     * 重载canCleEdit即可
                     */
                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();

                        /**
                         *  cancelEdit
                         */

                        HBox hbox = new HBox();
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        Button bu = getButton(temp);
                        Label lb_name = new Label(temp.getName()+"can");
                        Label lb_age = new Label(temp.getAge()+"can");
                        lb_name.setPrefWidth(100);
                        lb_age.setPrefWidth(100);
                        hbox.getChildren().addAll(iv, bu, lb_name, lb_age);
                        this.setGraphic(hbox);
                    }

                    /**
                     * 如果提交编辑？
                     * 可以删除
                     * @param newValue
                     */
                    @Override
                    public void commitEdit(DataP newValue) {
                        super.commitEdit(newValue);
                        System.out.println("提交编辑");
                    }

                    /**
                     * Alt+insert   调出Override方法窗口
                     * @param item
                     * @param empty
                     */
                    @Override
                    protected void updateItem(DataP item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            HBox hbox =new HBox();
                            hbox.setAlignment(Pos.CENTER_LEFT);
//                            ImageView iv = new ImageView(new Image(App.class.getResourceAsStream("avatar.jpg")));
//                            iv.setPreserveRatio(true);
//                            iv.setFitHeight(30);

                            Button bu = getButton(item);
                            Label lb = new Label(item.getName());
                            Label age = new Label(item.getAge());
                            hbox.getChildren().addAll(iv,bu,lb,age);
                            this.setGraphic(hbox);

//                            this.setGraphic(new Label(item.getName()+ "*****"+item.getAge()));

                        }
                    }
                };
                return listCell;
            }
        });

        /**
         * oblist可配置监听器  观察内容发生改变
         */
        oblist.addListener(new ListChangeListener<DataP>() {
            @Override
            public void onChanged(Change<? extends DataP> c) {
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
                ObservableList<DataP> data = (ObservableList<DataP>) observable;
                data.forEach(new Consumer<DataP>() {

                    @Override
                    public void accept(DataP simpleStringProperty) {
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
       oblist.add(0,new DataP("hello","430"));

    }

    @FXML
    public void updateList(){
        /**
         * 替换其实是先添加 后删除 然后替换o
         * oblist.add(0,"update");
         * oblist.remove(0+1);
         */
//        oblist.set(0,new DataP("update"));
        /**
         * 因为是DataP对象，有另外一种写法
         */
        s1.setName("hellloProperty");
        /**
         * javabean模式下需要加上
         */
        lv_stu.refresh();
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
       oblist.sort(new Comparator<DataP>() {
           @Override
           public int compare(DataP o1, DataP o2) {
               return o2.getName().compareTo(o1.getName());
           }
       });
    }
}
class DataP{
    private String name;

    public DataP(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age;
}
