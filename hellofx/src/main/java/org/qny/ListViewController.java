package org.qny;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * 第64课 https://www.bilibili.com/video/BV1Lt411z7SL?spm_id_from=333.999.0.0
 * 第53课 https://www.bilibili.com/video/BV1Zt411v7xd?spm_id_from=333.999.0.0
 */
public class ListViewController implements Initializable {

    @FXML
    private ListView lv_stu;
    /**
     * 好处是设置监听  不能针对控件直接
     */
    private ObservableList<String> oblist;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * oblist获取listview的可观察数据源
         */
        oblist = lv_stu.getItems();
        oblist.addAll("IronMan",
                "Titanic",
                "Piano",
                "Pie",
                "小丑");
//        lv_stu.getItems().add(oblist); /*不需要添加直接绑定*/
//        lv_stu.getItems().addAll("IronMan",
//                "Titanic",
//                "Piano",
//                "Pie",
//                "小丑");
        lv_stu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv_stu.setEditable(true);

        /**
         * oblist可配置监听器  观察内容发生改变
         */
        oblist.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
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
    public void insertList(){
       oblist.add(0,"hello");
    }

    @FXML
    public void updateList(){
        /**
         * 替换其实是先添加 后删除 然后替换o
         * oblist.add(0,"update");
         * oblist.remove(0+1);
         */
        oblist.set(0,"update");
    }

    @FXML
    public void DeleteList(){
        oblist.remove(0);

    }

    @FXML
    public void sortList(){
       oblist.sort(new Comparator<String>() {
           @Override
           public int compare(String o1, String o2) {
               return o2.compareTo(o1);
           }
       });
    }
}
