package org.openjfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: testDynamicDrawing.java
 * @Description: 动态绘图
 * https://www.bilibili.com/video/BV1Gb41157b2/?spm_id_from=333.788.recommend_more_video.1
 * @Package org.openjfx
 * @Time: 2021-09-12 15:19
 */
public class testDynamicDrawing implements Initializable {

    @FXML
    private Button bt_run;

    @FXML
    private Button bt_pause;

    @FXML
    private AnchorPane ap_run;

    @FXML
    private Button bt_primary;

    XYChart.Series<Number,Number> xy1;
    XYChart.Series<Number,Number> xy2;
    NumberAxis x;
    NumberAxis y;
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /**
     *  任务及按钮监听事件初始化
     */
    @FXML
    private void switchMonitor(){
       DataTask dt = new DataTask();
       dt.setDelay(Duration.seconds(0)); /*0s直接气动*/
       dt.setPeriod(Duration.seconds(0.5)); /*1s钟执行一次  类似设置风机周期*/
        /*0.5s周期也可以 1s周期也可以  0.5往下太快了，失去监控意义*/
//        System.out.println("I have been runing");
       dt.valueProperty().addListener(new ChangeListener<ArrayList<Integer>>() {
           @Override
           public void changed(ObservableValue<? extends ArrayList<Integer>> observable, ArrayList<Integer> oldValue, ArrayList<Integer> newValue) {
               /**
                * 接受数据部分，最核心的地方，产生数据的源泉
                */
               if (newValue != null) {
                  int xValue = newValue.get(0);
                  int yValue = newValue.get(1);
                   System.out.println("GetData"+xValue+"---"+yValue);

                   int currentIndex = xy1.getData().size();
                   /**
                    * 动态x 轴增加边界
                    */
                   if (currentIndex > 18) {
                      x.setLowerBound(x.getLowerBound()+1);
                      x.setUpperBound(x.getUpperBound()+1);
                   }
                   /**
                    * 数据list 源控制  一般到几千都没问题
                    */
                   if (currentIndex >30) {
                       xy1.getData().clear();
                       xy2.getData().clear();

                       currentIndex =0 ; /*无数据状态*/
                       x.setLowerBound(0);
                       x.setUpperBound(20);
                   }

                   XYChart.Data<Number, Number> data1 = new XYChart.Data<Number, Number>(currentIndex,xValue);
                   XYChart.Data<Number, Number> data2 = new XYChart.Data<Number, Number>(currentIndex,yValue);

                   /**
                    * xy1 xy2 持续膨胀，会有问题，经过一定时间后，数据持久化(定期 定量持久化即可)
                    * 删掉xy1 xy2 不关心数据
                    */
                   xy1.getData().add(data1);
                   xy2.getData().add(data2);

               }

           }
       });
        /**
         * 冒泡EventHandler
         *
         */
       bt_run.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               /**
                * 开始执行任务
                */

//               System.out.println("I have been runing button start");
               /**
                * 如果为false才启动
                */
               if (dt.isRunning() ==false ) {
                   dt.start();
               }
           }
       });

       bt_pause.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {

//               System.out.println("I have been runing button pause");
              dt.cancel();
              dt.reset();
           }
       });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        x = new NumberAxis("X轴",0,20,1);
        y = new NumberAxis("Y轴",0,100,10);
        LineChart<Number,Number> chart =new LineChart<Number,Number>(x,y);

        chart.setPrefWidth(800);
        chart.setPrefHeight(500);
        chart.setAnimated(false);
//        chart.setAnimated(true);/*动画比较不好看*/


        xy1= new XYChart.Series<Number,Number>();
        xy1.setName("xy1");
        xy2= new XYChart.Series<Number,Number>();
        xy2.setName("xy2");

        chart.getData().addAll(xy1,xy2);
        ap_run.getChildren().add(chart);
        AnchorPane.setTopAnchor(chart, 20.0);
        AnchorPane.setLeftAnchor(chart, 20.0);
    }
}

/**
 * 产生一定数据结构的task
 * 根据Task制订动态获取过程
 */
class DataTask extends ScheduledService<ArrayList<Integer>> {

    @Override
    protected Task<ArrayList<Integer>> createTask() {
        Task<ArrayList<Integer>> task = new Task<ArrayList<Integer>>() {
            @Override
            protected ArrayList<Integer> call() throws Exception {
                Random ra = new Random();
                int value1 = ra.nextInt(100);
                int value2 = ra.nextInt(100);

                ArrayList<Integer> result = new ArrayList<Integer>();
                result.add(value1);
                result.add(value2);
                return result;
            }
        };

        return task;
    }
}
