package org.qny;

import cn.hutool.core.util.ReUtil;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;
import io.vproxy.vfx.ui.layout.HPadding;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.table.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.data.Entity.JFinal.Standardenergyupdate;
import org.data.Entity.JFinal._MappingKit;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: VFXTest.java
 * @Description: (用一句话描述该文件做什么 ?)
 * @Package com.example.gradualcreate
 * @Time: 2023/1/26 23:10
 */
public class VFXVTableStandard extends Application  {
    private static final DecimalFormat roughFloatValueFormat = new DecimalFormat("0.0");

    Prop p = PropKit.useFirstFound("jdbc.properties", "app-config-pro.txt", "app-config-dev.txt");
    DruidPlugin dp = new DruidPlugin(p.get("jdbc.url"), p.get("jdbc.username"), p.get("jdbc.password").trim());
    ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
//    ObservableList<Standardenergyupdate> standards;
//    Standardenergyupdate standardDao;
    @Override
    public void start(Stage primaryStage) throws Exception {
        var borderPane = new BorderPane();
        var scene = new Scene(borderPane);
        var tableHBox= new HBox();

        _MappingKit.mapping(arp);///所有表映射上

        // 与 jfinal web 环境唯一的不同是要手动调用一次相关插件的start()方法
        dp.start();
        arp.start();
//        Professor professorDao= new Professor().dao();
        Standardenergyupdate standardDao= new Standardenergyupdate().dao();

        //find对应Record, query可以返回字符串，这是特别爽的地方
//        List<Record> list1=  Db.find("select distinct std_system_name from standardenergyupdate");
        List<String> list1=  Db.query("select distinct std_system_name from standardenergyupdate");
        Map<String,Pane> tablePaneMap =new HashMap<>();
        for(String std_system_name:list1){
            System.out.println(std_system_name);
            var tablePane1 = createPane(std_system_name,standardDao);
//            tablePane{std_system_name}=tablePane1;
            tablePaneMap.put(std_system_name,tablePane1);

        }
        // query*** queryInt  queryStr  queryLong等 满足聚合函数需要！
        int total = Db.queryInt("select count(*) as total from standardenergyupdate");
        //同理Db.query 而不是Db.find, find会返回一个Record对象
        System.out.println("总共: "+ total);


        //noinspection unchecked

        borderPane.setTop(new Label("总共: "+ total+"个标准"));
        borderPane.setCenter(tablePaneMap.get(list1.get(0)));
        tableHBox.getChildren().addAll(
                new HPadding(15),
                new Button(list1.get(0)) {{
                    setOnAction(e -> borderPane.setCenter(tablePaneMap.get(list1.get(0))));
                }}
        );
        for(int index1=1; index1 <  list1.size();index1++){

            int finalIndex = index1;
            tableHBox.getChildren().addAll(
                    new HPadding(5),
                    new Button(list1.get(finalIndex)) {{
                        setOnAction(e -> borderPane.setCenter(tablePaneMap.get(list1.get(finalIndex))));
                    }}
            );
        }

        borderPane.setBottom(new VBox(
                (Node) tableHBox,
                new VPadding(10)
        ));

        primaryStage.setScene(scene);
        primaryStage.setWidth(2500);
        primaryStage.setHeight(1377);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    private Pane createPane(String tableName,Standardenergyupdate standardDao){

        List<Standardenergyupdate> standards = standardDao.find("select * from standardenergyupdate where std_system_name = ?",tableName);
//        List<Standardenergyupdate> standards = standardDao.findAll();
        var tablePane = new Pane();
//        var table = new VTableView<DataVFXZero>();
        var table=new VTableView<Standardenergyupdate>();

        var colStrF = new VTableColumn<>("标准系统分类id", Standardenergyupdate::getStdSystemId);
        var colIntF = new VTableColumn<>("标准名字", Standardenergyupdate::getStdName);
        var colDoubleF = new VTableColumn<>("标准编号", Standardenergyupdate::getStdCode);
        var colNodeF = new VTableColumn<>("标准状态", Standardenergyupdate::getStdEditStatus);
        var colNodeCat = new VTableColumn<>("标准分类", Standardenergyupdate::getStdCategory);
        {
            colStrF.setPrefWidth(100);
            colStrF.setMinWidth(50);
            colStrF.setComparator(String::compareTo);
        }
        {
            colIntF.setMinWidth(500);
            colIntF.setMaxWidth(800);
            colIntF.setComparator(String::compareTo);
            colIntF.setAlignment(Pos.CENTER);


        }
        {
            colDoubleF.setMinWidth(80);
            colDoubleF.setPrefWidth(200);
//            colDoubleF.setTextBuilder(roughFloatValueFormat::format);
            colDoubleF.setComparator(String::compareTo);
        }
        {
            colNodeF.setMinWidth(50);
            colNodeF.setPrefWidth(200);
//            colNodeF.setNodeBuilder(n -> n);
        }

        {
            colNodeCat.setMinWidth(20);
            colNodeCat.setPrefWidth(100);
//            colNodeCat.setNodeBuilder(n -> n);
        }
        table.getColumns().addAll(Arrays.asList(colStrF, colIntF, colDoubleF, colNodeF,colNodeCat));

//    arp.addMapping("blog", Blog.class);

//            table.getItems().add(new DataVFXZero());
        for(Standardenergyupdate standard : standards){
            table.getItems().add(standard);
        }

        var hbox = new HBox();
        hbox.setLayoutX(15);
        hbox.setLayoutY(10);
        tablePane.getChildren().add(hbox);

        hbox.getChildren().add(table.getNode());
        hbox.getChildren().add(new HPadding(15));
        var buttons = new VBox();
        hbox.getChildren().add(buttons);

        var addBtn = new Button("add") {{
            setPrefWidth(120);
        }};
        var delBtn = new Button("del") {{
            setPrefWidth(120);
        }};
        buttons.getChildren().addAll(
                addBtn,
                new VPadding(5),
                delBtn
        );

        addBtn.setOnAction(e -> table.getItems().add(new Standardenergyupdate()));
        delBtn.setOnAction(e -> {
            var selected = table.getSelectedItem();
            if (selected == null) {
                return;
            }
            table.getItems().remove(selected);
        });

        tablePane.widthProperty().addListener((ob, old, now) -> {
            if (now == null) return;
            table.getNode().setPrefWidth(now.doubleValue() - 165);
        });
        tablePane.heightProperty().addListener((ob, old, now) -> {
            if (now == null) return;
            table.getNode().setPrefHeight(now.doubleValue() - 20);
        });

        return tablePane;
    }
}

