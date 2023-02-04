package org.qny;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;
import io.vproxy.vfx.control.scroll.ScrollDirection;
import io.vproxy.vfx.control.scroll.VScrollPane;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.button.FusionImageButton;
import io.vproxy.vfx.ui.layout.HPadding;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.pane.FusionPane;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.stage.VStage;
import io.vproxy.vfx.ui.table.VTableColumn;
import io.vproxy.vfx.ui.table.VTableView;
import io.vproxy.vfx.ui.wrapper.FusionW;
import io.vproxy.vfx.util.FXUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.data.Entity.JFinal.Standardenergyupdate;
import org.data.Entity.JFinal._MappingKit;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: VFXTest.java
 * @Description: (用一句话描述该文件做什么 ?)
 * @Package com.example.gradualcreate
 * @Time: 2023/1/26 23:10
 */
public class VFXVTableStandardUpdate extends Application  {
    private static final DecimalFormat roughFloatValueFormat = new DecimalFormat("0.0");

    Prop p = PropKit.useFirstFound("jdbc.properties", "app-config-pro.txt", "app-config-dev.txt");
    DruidPlugin dp = new DruidPlugin(p.get("jdbc.url"), p.get("jdbc.username"), p.get("jdbc.password").trim());
    ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
//    ObservableList<Standardenergyupdate> standards;
//    Standardenergyupdate standardDao;
    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO 4 添加menu小图片
        ImageManager.get().loadBlackAndChangeColor("images/menu.png", Map.of("white", 0xffffffff));
        //TODO 5  添加自定义的VStage
        var stage = new VStage(primaryStage);
        // 可忽略
        var table1Scene = new VScene(VSceneRole.MAIN);
        var sceneGroup = new VSceneGroup(table1Scene);
        var rootPane = stage.getInitialScene().getContentPane();
        FXUtils.observeWidthHeight(stage.getInitialScene().getNode(),rootPane);

        _MappingKit.mapping(arp);///所有表映射上

        // 与 jfinal web 环境唯一的不同是要手动调用一次相关插件的start()方法
        dp.start();
        arp.start();
//        Professor professorDao= new Professor().dao();
        Standardenergyupdate standardDao= new Standardenergyupdate().dao();

        //find对应Record, query可以返回字符串，这是特别爽的地方
//        List<Record> list1=  Db.find("select distinct std_system_name from standardenergyupdate");
        List<String> list1=  Db.query("select distinct std_system_name from standardenergyupdate");
        //TODO 8 修改数据结构
        Map<String,VTableView> tableViewMap =new HashMap<>();
        Map<String,VScene> sceneMap =new HashMap<>();

        for(String std_system_name:list1){
            System.out.println(std_system_name);
            //TODO 8  修改调用
            var table = createPane(std_system_name,standardDao);
//            tablePane{std_system_name}=tablePane1;
            if(table == null){
                System.out.println("为空了");
                return;
            }
            tableViewMap.put(std_system_name,  table);

        }
        // query*** queryInt  queryStr  queryLong等 满足聚合函数需要！
        int total = Db.queryInt("select count(*) as total from standardenergyupdate");
        //同理Db.query 而不是Db.find, find会返回一个Record对象
        System.out.println("总共: "+ total);


        //noinspection unchecked

        //TOD 9 borderPane需要修改了
        var scrollPane = new VScrollPane(ScrollDirection.HORIZONTAL);
        var bottomButtonPane = new FusionPane();
        bottomButtonPane.getContentPane().getChildren().add(scrollPane.getNode());
        FXUtils.observeWidth(bottomButtonPane.getContentPane(),scrollPane.getNode());

        var tableHBox= new HBox();
        tableHBox.setSpacing(5);
        scrollPane.setContent(tableHBox);

//        borderPane.setTop(new Label("总共: "+ total+"个标准"));
//        borderPane.setCenter(tablePaneMap.get(list1.get(0)));
        //第一个Tab

        var hbox = new HBox();
        hbox.setLayoutX(30);
        hbox.setLayoutY(10);
//        tablePane.getChildren().add(hbox);

//        FXUtils.observeWidthHeight(rootPane.getContent,,rootPane.)
        hbox.getChildren().add(tableViewMap.get(list1.get(0)).getNode());
        hbox.getChildren().add(new HPadding(15));
        var buttons = new VBox();
        //TODO 2 添加buttonPane 给所有button添加一个Pane 添加并修改下面3行  依然
        var buttonPane = new FusionPane(false);
        buttonPane.getContentPane().getChildren().add(buttons);
        hbox.getChildren().add(buttonPane.getNode());

        var addBtn = new FusionButton("添加") {{
            setPrefWidth(100);
        }};
        var delBtn = new FusionButton("删除") {{
            setPrefWidth(100);
        }};
        buttons.getChildren().addAll(
                addBtn,
                new VPadding(30),
                delBtn
        );

        addBtn.setOnAction(e -> tableViewMap.get(list1.get(0)).getItems().add(new Standardenergyupdate()));
        delBtn.setOnAction(e -> {
            var selected = tableViewMap.get(list1.get(0)).getSelectedItem();
            if (selected == null) {
                return;
            }
            tableViewMap.get(list1.get(0)).getItems().remove(selected);
        });
        table1Scene.getContentPane().getChildren().add(hbox);
        //TODO 4 tablePane. 改变为sceneGroup.getNote.
        sceneGroup.getNode().widthProperty().addListener((ob, old, now) -> {
            if (now == null) return;
            tableViewMap.get(list1.get(0)).getNode().setPrefWidth(now.doubleValue() - 165);
        });
        sceneGroup.getNode().heightProperty().addListener((ob, old, now) -> {
            if (now == null) return;
            tableViewMap.get(list1.get(0)).getNode().setPrefHeight(now.doubleValue() - 20);
        });

        tableHBox.getChildren().addAll(
                new HPadding(10),
                new FusionButton(list1.get(0)) {{
//                    setOnAction(e -> borderPane.setCenter(tablePaneMap.get(list1.get(0))));
                    setPrefWidth(120);
                    setOnAction(e -> sceneGroup.show(table1Scene, VSceneShowMethod.FROM_RIGHT));
                }}
        );
//        FXUtils.observeHeight(scrollPane.getNode(),tableHBox);
        // 第2个至最后一个
        for(int index1=1; index1 <  list1.size();index1++){

            var hboxTemp = new HBox();
            hboxTemp.setLayoutX(30);
            hboxTemp.setLayoutY(10);
//        tablePane.getChildren().add(hbox);

            hboxTemp.getChildren().add(tableViewMap.get(list1.get(index1)).getNode());
            hboxTemp.getChildren().add(new HPadding(15));
            var buttonsTemp = new VBox();
            //TODO 2 添加buttonPane 给所有button添加一个Pane 添加并修改下面3行  依然

            //TODO 2 添加buttonPane 给所有button添加一个Pane 添加并修改下面3行  依然
            var buttonPaneTemp = new FusionPane(false);
            hboxTemp.getChildren().add(buttonPaneTemp.getNode());
            buttonPaneTemp.getContentPane().getChildren().add(buttonsTemp);

            var addBtnTemp = new FusionButton("添加") {{
                setPrefWidth(100);
            }};
            var delBtnTemp = new FusionButton("删除") {{
                setPrefWidth(100);
            }};
            buttonsTemp.getChildren().addAll(
                    addBtnTemp,
                    new VPadding(30),
                    delBtnTemp
            );
            int finalIndex = index1;
            addBtnTemp.setOnAction(e -> tableViewMap.get(list1.get(finalIndex)).getItems().add(new Standardenergyupdate()));
            int finalIndex1 = index1;
            delBtnTemp.setOnAction(e -> {
                var selected = tableViewMap.get(list1.get(finalIndex1)).getSelectedItem();
                if (selected == null) {
                    return;
                }
                tableViewMap.get(list1.get(finalIndex)).getItems().remove(selected);
            });
            var tablesceneTemp= new VScene(VSceneRole.MAIN);
            tablesceneTemp.getContentPane().getChildren().add(hboxTemp);
            sceneGroup.addScene(tablesceneTemp); // 不增加这句话有问题  无法切换了
//            sceneGroup.addScene(new VScene(VSceneRole.MAIN).getContentPane().getChildren().add(hbox));
            // sceneGroup更新
            sceneGroup.getNode().widthProperty().addListener((ob, old, now) -> {
                if (now == null) return;
                tableViewMap.get(list1.get(finalIndex)).getNode().setPrefWidth(now.doubleValue() - 165);
            });
            sceneGroup.getNode().heightProperty().addListener((ob, old, now) -> {
                if (now == null) return;
                tableViewMap.get(list1.get(finalIndex)).getNode().setPrefHeight(now.doubleValue() - 20);
            });
            // tableHBox更新
            tableHBox.getChildren().addAll(
                    new HPadding(15),
                    new FusionButton(list1.get(finalIndex1)) {{
                        // 必须修改  不然不会切换
//                        setOnAction(e -> sceneGroup.show(table1Scene,VSceneShowMethod.FROM_RIGHT));
                        setPrefWidth(150);
                        setOnAction(e -> sceneGroup.show(tablesceneTemp,VSceneShowMethod.FROM_TOP));
                    }}
            );
        }

        //这边设置都没用 在stage.getInitialScene().getNode().widthProperty().设置即可
//        bottomButtonPane.getNode().setPrefHeight(44); //getNode 而不是getContentPane
//        scrollPane.getNode().setPrefHeight(44);
        // 如果没有加上这句话 不会显示
        rootPane.getChildren().add(sceneGroup.getNode());
        // 试试看会不会超过


//        bottomButtonPane.getContentPane().getChildren().add(
//                tableHBox
//        );

        stage.getInitialScene().getNode().widthProperty().addListener((ob, old, now)->{
            if (now == null) {
                return;
            }
            var w = now.doubleValue();
//            bottomButtonPane.getNode().setPrefHeight(w-240);
            // 得查看w的值，如果是2400多，那么设置1/50即可
            bottomButtonPane.getNode().setPrefHeight(w/50);
        });
//        bottomButtonPane.getNode().setPrefHeight(45);

        var bottomScene = new VScene(VSceneRole.DRAWER_HORIZONTAL);
        bottomScene.getNode().setPrefHeight(240);
        sceneGroup.addScene(bottomScene, VSceneHideMethod.TO_BOTTOM);
        bottomScene.getContentPane().getChildren().add(bottomButtonPane.getNode());
        // 只有添加了 才能进行observerWidhtHeight
        FXUtils.observeWidthHeight(bottomScene.getContentPane(),bottomButtonPane.getNode());

/*
        borderPane.setBottom(new VBox(
                (Node) tableHBox,
                new VPadding(10)
        ));

*/
        // TODO 7

        var menuBtn = new FusionImageButton(ImageManager.get().load("images/menu.png:white")) {{
            setPrefWidth(40);
            setPrefHeight(VStage.TITLE_BAR_HEIGHT - 4);
            getImageView().setFitHeight(15);
            setLayoutX(2);
            setLayoutY(3);
        }};
        stage.getRoot().getContentPane().getChildren().add(menuBtn);
        menuBtn.setOnAction(e -> {
            if (sceneGroup.isShowing(bottomScene)) {
                sceneGroup.hide(bottomScene, VSceneHideMethod.TO_BOTTOM);
            } else {
                sceneGroup.show(bottomScene, VSceneShowMethod.FROM_BOTTOM);
            }
        });
        //TODO 10
        FXUtils.observeWidthHeight(rootPane,sceneGroup.getNode());
//        primaryStage.setScene(scene);
        primaryStage.setWidth(2500);
        primaryStage.setHeight(1377);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    private VTableView<Standardenergyupdate> createPane(String tableName, Standardenergyupdate standardDao){

        List<Standardenergyupdate> standards = standardDao.find("select * from standardenergyupdate where std_system_name = ?",tableName);
//        List<Standardenergyupdate> standards = standardDao.findAll();
//        var table = new VTableView<DataVFXZero>();
        var table=new VTableView<Standardenergyupdate>();

        var colStrF = new VTableColumn<>("标准系统分类id", Standardenergyupdate::getStdSystemId);
        var colIntF = new VTableColumn<>("标准名字", Standardenergyupdate::getStdName);
        var colDoubleF = new VTableColumn<>("标准编号", Standardenergyupdate::getNodeStdCode);
//        var colDoubleF = new VTableColumn<>("标准编号", Standardenergyupdate::getStdCode);
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
//            colDoubleF.setComparator(String::compareTo);

            colDoubleF.setNodeBuilder(n-> {
                if(n instanceof  TextInputControl){
                    return new FusionW((TextInputControl) n);
                }else if( n instanceof ComboBox){
                    return new FusionW((ComboBox<?>) n);
                }else{
                    return n;
                }
            });
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

        //TODO 1


        return  table;
        //TODO 3 添加效果
//        var sceneGroup = new VSceneGroup(table1Scene);

        //TODO 所有东西已保存在sceneGroup中 需要返回sceneGroup
//        return tablePane;
    }
}

