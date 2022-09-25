package org.openjfx;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.leewyatt.rxcontrols.controls.RXHighlightText;
import com.leewyatt.rxcontrols.utils.StringUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeeWyatt
 */
public class HighlightListApp extends Application {

    {
        //1 方法1 直接写入url username 和password
//        DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/professors", "root", "457866");
        //方法2: 通过Jfinal Prop库进行加载

        Prop p = PropKit.useFirstFound("jdbc.properties","app-config-pro.txt", "app-config-dev.txt");
        DruidPlugin dp = new DruidPlugin(p.get("jdbc.url"), p.get("jdbc.username"),p.get("jdbc.password").trim());
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
//    arp.addMapping("blog", Blog.class);

        // 与 jfinal web 环境唯一的不同是要手动调用一次相关插件的start()方法
        dp.start();
        arp.start();

    }

        // 通过上面简单的几行代码，即可立即开始使用
        //List<Record> users = Db.use("mysql").find("select * from user");
      private  List<String> titleList = Db.query("select Name from professor");


//        private ObservableList<String> strList = FXCollections.observableArrayList("sky678hawabcak@wyx.com",
//                "wdafsABC132t@qqxz.com",
//                "star1321udy@xyz.com",
//                "AbC1fa321afis@abc.com",
//                "135931213112",
//                "13232100453",
//                "12322113533",
//                "132664588",
//                "97451835");

    ObservableList<String> strList =FXCollections.observableArrayList(titleList);

    private FilteredList<String> filteredList = new FilteredList<>(strList,param->true);
    private TextField searchField;
    private ComboBox<RXHighlightText.MatchRules> comboBox;
    private ListView<String> listView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        searchField = new TextField();
        //下拉框: 匹配规则分别是 1正则表达式匹配,2区分大小写匹配字符串,3忽略大小写匹配字符串
        comboBox = new ComboBox<>(FXCollections.observableArrayList(RXHighlightText.MatchRules.values()));
        comboBox.getSelectionModel().select(0);

        HBox topBox = new HBox(searchField, comboBox);
        root.setTop(topBox);

        listView = new ListView<>();
        listView.setCellFactory(param -> new HighlightCell());
        listView.setItems(filteredList);

        root.setCenter(listView);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        searchField.textProperty().addListener((ob, ov, nv) ->{
            searchKeywords();
        });
        comboBox.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) ->{
            searchKeywords();
        });

    }

    private void searchKeywords() {
        filteredList.setPredicate(param-> {
            //如果关键字为空, 那么全部匹配
            if (searchField.getText().trim().isEmpty()) {
                return true;
            }

            RXHighlightText.MatchRules rule = comboBox.getSelectionModel().getSelectedItem();
            //匹配结果
            ArrayList<Pair<String, Boolean>> result ;
            if (rule.equals(RXHighlightText.MatchRules.REGEX)) {
                result =  StringUtil.matchText(param, searchField.getText());
            } else if (rule.equals(RXHighlightText.MatchRules.IGNORE_CASE)) {
                result = StringUtil.parseText(param, searchField.getText(), true);
            }else {
                result = StringUtil.parseText(param, searchField.getText(), false);
            }
            for (Pair<String, Boolean> stringBooleanPair : result) {
                if (stringBooleanPair.getValue()) {
                    //如果找到一个匹配结果,那么返回匹配成功
                    return true;
                }
            }
            //没有找到, 那么返回匹配失败
            return false ;
        });
        listView.refresh();
    }

    private class HighlightCell extends ListCell<String> {
        RXHighlightText highlightText;
        public HighlightCell() {
            highlightText = new RXHighlightText();

        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
                return;
            }
            highlightText.setText(item);
            highlightText.setKeywords(searchField.getText());
            highlightText.setMatchRules(comboBox.getSelectionModel().getSelectedItem());
            setGraphic(highlightText);
        }
    }


    public static void main(String[] args) {

        launch(args);
    }
}
