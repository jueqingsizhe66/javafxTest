package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PrimaryController  {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToWarning() throws IOException {
        App.setRoot("alert");
    }

    @FXML
    private void switchToExtractInfo() throws IOException {
        App.setRoot("extractInfo");
    }

    @FXML
    private void switchToLongin() throws IOException {
        App.setRoot("longin");
    }

    @FXML
    private void switchToImageView() throws IOException {
        App.setRoot("imageView");
    }

    @FXML
    private void switchToChoicesButton() throws IOException {
        App.setRoot("testChoiceButton");
    }

    @FXML
    private void switchToMFbutton() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        App.setRoot("materialsFX/ButtonsDemo");
        Node n1 = new Button("Hello");
        String name = n1.getClass().getName();
        System.out.println(name);

        Class<?> classtype= Class.forName(name);
        Node n2 = (Node) classtype.getConstructor(null).newInstance(null);
//
        Button n3 = new Button("回到主页");
        n3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("primary");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        AnchorPane an =new AnchorPane();
        HBox h1 = new HBox();
        h1.getChildren().addAll(n1, n2,n3);
//        h1.getChildren().addAll(n1);
        an.getChildren().add(h1);
        App.setRootAn(an);
    }

    @FXML
    private void switchToMFTableView() throws IOException {
//        App.setRoot("materialsFX/TableViewsDemo");

        /**
         * 数据
         */
        ObservableList<XYChart.Data<String,Number>> data = FXCollections.observableArrayList();
        XYChart.Data<String,Number> n1 = new XYChart.Data<String,Number>("中国",90);
        XYChart.Data<String,Number> n2 = new XYChart.Data<String,Number>("日本",80);
        XYChart.Data<String,Number> n3 = new XYChart.Data<String,Number>("朝鲜",20);

        data.addAll(n1,n2,n3);
        /**
         * 界面属性设置
         */

        ObservableList<XYChart.Series<String,Number>> list_data = FXCollections.observableArrayList();
        XYChart.Series<String,Number> xy = new XYChart.Series<String,Number>();
        xy.setName("亚洲生产总值");
        xy.setData(data);
        list_data.add(xy); /*比较特殊写法*/

        CategoryAxis x = new CategoryAxis();
        x.setLabel("国家");
        NumberAxis y = new NumberAxis(0,100,10);
        y.setLabel("生产总值");


        /**
         * 总界面
         */
        BarChart<String,Number> barChart = new BarChart<>(x,y,list_data);
        barChart.setPrefWidth(400);
        barChart.setPrefWidth(600);

        Button returnBut = new Button("回到主页");
        returnBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("primary");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        AnchorPane an =new AnchorPane();
        HBox h1 = new HBox();
//        h1.getChildren().addAll(n1);
        h1.getChildren().addAll(barChart,returnBut);
        an.getChildren().add(h1);
        App.setRootAn(an);
    }

    @FXML
    private void switchToMFTreeView() throws IOException {
        // 文件名 大小写敏感
//        App.setRoot("materialsFX/TreeViewsDemo");

        /**
         * 数据, 不用ObserableList解决问题了 直接设置XYChart.Series和XYChart.Dta
         */
        XYChart.Series<String,Number> xy1 = new XYChart.Series<String,Number>();
        xy1.setName("中国");
        XYChart.Series<String,Number> xy2 = new XYChart.Series<String,Number>();
        xy2.setName("日本");
        XYChart.Series<String,Number> xy3 = new XYChart.Series<String,Number>();
        xy3.setName("韩国");

        XYChart.Data<String,Number> chinaGDP = new XYChart.Data<String,Number>("GDP",90);
        XYChart.Data<String,Number> japenGDP = new XYChart.Data<String,Number>("GDP",80);
        XYChart.Data<String,Number> KoreaGDP = new XYChart.Data<String,Number>("GDP",20);

        XYChart.Data<String,Number> chinaGNP = new XYChart.Data<String,Number>("GNP",190);
        XYChart.Data<String,Number> japenGNP = new XYChart.Data<String,Number>("GNP",180);
        XYChart.Data<String,Number> KoreaGNP = new XYChart.Data<String,Number>("GNP",90);

        xy1.getData().add(chinaGDP);
        xy1.getData().add(chinaGNP);

        xy2.getData().add(japenGDP);
        xy2.getData().add(japenGNP);

        xy3.getData().add(KoreaGDP);
        xy3.getData().add(KoreaGNP);
        /**
         * 界面属性设置
         */

        CategoryAxis x = new CategoryAxis();
        x.setLabel("国家");
        NumberAxis y = new NumberAxis(0,100,10);
        y.setLabel("生产总值");

        /**
         * 总界面
         */
        BarChart<String,Number> barChart = new BarChart<>(x,y);
        barChart.getData().add(xy1);
        barChart.getData().add(xy2);
        barChart.getData().add(xy3);
        barChart.setTitle("第二种方式");
        barChart.setPrefWidth(400);
        barChart.setPrefWidth(600);

        Button returnBut = new Button("回到主页");
        returnBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("primary");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        AnchorPane an =new AnchorPane();
        HBox h1 = new HBox();
//        h1.getChildren().addAll(n1);
        h1.getChildren().addAll(barChart,returnBut);
        an.getChildren().add(h1);
        App.setRootAn(an);
    }

    @FXML
    private void switchToMFListView() throws IOException {
        // 文件名 大小写敏感
//        App.setRoot("materialsFX/ListViewsDemo");

        /**
         * 数据, 不用ObserableList解决问题了 直接设置XYChart.Series和XYChart.Dta
         * 一种Series代表一种颜色
         * 两种则两种颜色
         *
         * 凡是Data里头标签名字一样的都放在一起！
         */
        XYChart.Series<String,Number> xy1 = new XYChart.Series<String,Number>();
        xy1.setName("GDP");
        XYChart.Series<String,Number> xy2 = new XYChart.Series<String,Number>();
        xy2.setName("GNP");

        XYChart.Data<String,Number> GDP1 = new XYChart.Data<String,Number>("中国",90);
        XYChart.Data<String,Number> GDP2 = new XYChart.Data<String,Number>("日本",80);
        XYChart.Data<String,Number> GDP3 = new XYChart.Data<String,Number>("韩国",20);

        XYChart.Data<String,Number> GNP1 = new XYChart.Data<String,Number>("中国",190);
        XYChart.Data<String,Number> GNP2 = new XYChart.Data<String,Number>("日本",180);
        XYChart.Data<String,Number> GNP3 = new XYChart.Data<String,Number>("韩国",90);

        xy1.getData().addAll(GDP1,GDP2,GDP3);
        xy2.getData().addAll(GNP1,GNP2,GNP3);


        /**
         * 界面属性设置
         */

        CategoryAxis x = new CategoryAxis();
        x.setLabel("国家");
        NumberAxis y = new NumberAxis(0,100,10);
        y.setLabel("生产总值");

        /**
         * 总界面
         */
        BarChart<String,Number> barChart = new BarChart<>(x,y);
        barChart.getData().add(xy1);
        barChart.getData().add(xy2);
        barChart.setTitle("第三种方式");
        barChart.setPrefWidth(400);
        barChart.setPrefWidth(600);

        Button returnBut = new Button("回到主页");
        returnBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App.setRoot("primary");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        AnchorPane an =new AnchorPane();
        HBox h1 = new HBox();
//        h1.getChildren().addAll(n1);
        h1.getChildren().addAll(barChart,returnBut);
        an.getChildren().add(h1);
        App.setRootAn(an);
    }

    @FXML
    private void switchToDynamicDrawing() throws IOException {
        App.setRoot("testDynamicDrawing");
    }
    @FXML
    private void switchToCombobox() throws IOException {
        App.setRoot("testCombobox");
    }

    @FXML
    private void switchToListview() throws IOException {
        App.setRoot("testListView");
    }

    @FXML
    private void switchToTableView() throws IOException {
        App.setRoot("testTableView");
    }


    @FXML
    private void switchToTableViewMap() throws IOException {
        App.setRoot("testTableViewMapController");
    }
    @FXML
    private void switchToTreeView() throws IOException {
        App.setRoot("testTreeView");
    }

    @FXML
    private void switchToPersonalOverview() throws IOException {
        App.setRoot("personalOverview");
    }

    @FXML
    private void switchToImageViewerWithBlur() throws IOException {
        App.setRoot("imageViewWithBlur");
    }

    @FXML
    private void switchToSystemTrap() throws IOException {
        App.setRoot("minimizeSystemTray");

    }

    @FXML
    private void switchToJAVAFXCollections() throws IOException {
        App.setRoot("FXCollectionsTest");

    }

    @FXML
    private void switchToWebView() throws IOException {
        App.setRoot("testWebView");

    }

    @FXML
    private void switchToBindTheory() throws IOException {
        App.setRoot("bindTheoryTest");

    }

    @FXML
    private void switchToTreeTableView() throws IOException {
        App.setRoot("treeTableView");
    }


    @FXML
    private void switchToPagination() throws IOException {
        App.setRoot("testPagination");
    }

    @FXML
    private void switchToSlider() throws IOException {
        App.setRoot("testSlider");
    }
    @FXML
    private void switchToListViewSSP() throws IOException {
        App.setRoot("testListViewSSP");

    }

    @FXML
    private void switchToListViewJavaBean() throws IOException {
        App.setRoot("testListViewSSPJavaBean");

    }

    @FXML
    private void switchToListViewJavaBeanSSP() throws IOException {
        App.setRoot("testListViewSSPJavaBeanSSP");

    }
}

