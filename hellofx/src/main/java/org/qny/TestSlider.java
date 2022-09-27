package org.qny;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: TestSlider.java
 * @Description: 测试Slider常见用法
 * @Package org.openjfx
 * @Time: 2021-10-03 23:24
 */
public class TestSlider implements Initializable {
    @FXML
    private Slider bt_slider;

    @FXML
    private Slider bt_slider2;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_slider.setPrefWidth(200);

        bt_slider.setShowTickMarks(true);
        bt_slider.setShowTickLabels(true);
        bt_slider.setMajorTickUnit(20);
        bt_slider.setValue(20);
        bt_slider.setOrientation(Orientation.VERTICAL);

        bt_slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

//                System.out.println(event.getSource());
                System.out.println("nothing in click" + event.getClickCount());
            }
        });
        bt_slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                if (object.doubleValue() == 0) {
                    return "零";
                } else if (object.doubleValue() == 20) {
                    return "贰拾";
                } else if (object.doubleValue() == 40) {
                    return "肆拾";
                } else if (object.doubleValue() == 60) {
                    return "陆拾";
                } else {
                    return "未知";
                }
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        bt_slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                System.out.println("newValue is " + newValue);
                bt_slider.setValueChanging(true);
            }
        });

        bt_slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("Slider is moving now? " + newValue);
            }
        });
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 200; i++) {
                    bt_slider.setValue(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        bt_slider2.setPrefWidth(100);
        bt_slider2.setMajorTickUnit(10);
        //        bt_slider2.setValue(0);
        /**
         *
         *
         ExecutorService threadPool = new ThreadPoolExecutor(
         2,
         4,
         //                Runtime.getRuntime().availableProcessors(),
         2,
         TimeUnit.SECONDS,
         new LinkedBlockingQueue<>(2),
         Executors.defaultThreadFactory(),
         new ThreadPoolExecutor.AbortPolicy()
         );
         try {
         for (int j = 0; j < 100; j++) {
         int temp = j;
         //TODO: handle 有点问题
         //2021-10-06 0:41 产生问题
         //2021-10-07 19:31   不支持多线程？！
         threadPool.execute(() -> {
         bt_slider2.setValue(temp);
         System.out.println("TempValue is " + temp);
         //                    bt_slider2.setValueChanging(true);
         });
         }
         } catch (Exception e) {
         e.printStackTrace();
         } finally {
         threadPool.shutdown();
         }
         */
        /**
         * 1. 创建线程池服务
         * 可行啊！ 这种方法不错  跑多线程没问题
         */
        ExecutorService service = Executors.newFixedThreadPool(8);
        service.execute(()->{
            for (int j = 0; j < 100; j++) {
                int temp = j;
                bt_slider2.setValue(temp);
                System.out.println("TempValue is " + temp);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * 2. 关闭服务
         */
        service.shutdown();
    }

    /**
     *
     *
     */

}

