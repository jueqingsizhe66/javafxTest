package org.openjfx;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void init() throws Exception {
        System.out.println("Initialize   ^-^"+ Thread.currentThread().getName());

        Screen screen = Screen.getPrimary();
        System.out.println("The current screen dpi (bigger and better"+ screen.getDpi());

        Rectangle2D rec1 = screen.getBounds();
        System.out.println("THe overall screen width and height coordinate ");
        System.out.println("Left up corner x = " + rec1.getMinX() + "   " + " left up corner y = "+ rec1.getMinY());
        System.out.println("Right down corner x = " + rec1.getMaxX() + "   " + " right down corner y = "+ rec1.getMaxY());
        System.out.println("Width = "+ rec1.getWidth() + "   "+  "  height = " + rec1.getHeight());


        Rectangle2D rec2 = screen.getVisualBounds();
        System.out.println("THe overall screen width and height coordinate in visual zone ");
        System.out.println("Left up corner x = " + rec2.getMinX() + "   " + " left up corner y = "+ rec2.getMinY());
        System.out.println("Right down corner x = " + rec2.getMaxX() + "   " + " right down corner y = "+ rec2.getMaxY());
        System.out.println("Width = "+ rec2.getWidth() + "   "+  "  height = " + rec2.getHeight());

        System.out.println("Whether support scene3D ->"+Platform.isSupported(ConditionalFeature.SCENE3D));
        System.out.println("Whether support fxml ->h"+Platform.isSupported(ConditionalFeature.FXML));

        /**
         * 打开网页
         */
//        HostServices host = getHostServices();
//        host.showDocument("https://www.dogedoge.com");
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Your window has been stoped"+  Thread.currentThread().getName());
    }

    private static Scene scene;

    /**
     * stage就是一个窗口类！！
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("start stage "+  Thread.currentThread().getName());
        scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        // 得放在resouces底下的 org.openjfx包下
        stage.getIcons().add(new Image(App.class.getResourceAsStream("avatar.jpg")));

        //https://stackoverflow.com/questions/29405658/using-fxid-as-css-id-in-fxml
        //stage.getScene().getStylesheets().add("/foo.css");
        stage.setScene(scene);
//        stage.setWidth(400);
//        stage.setHeight(200);
//        stage.setResizable(true);
//        stage.setMaximized(true);
//        stage.setFullScreen(true); // 必须设置scene！ scene就是桌子上的桌布，桌布上面可以摆上锅碗瓢盆
//        stage.setOpacity(0.5);// 半透明

        stage.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("The window's new x coordinate = "+ newValue);
            }
        });

        stage.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("The window's new x coordinate = "+ newValue);
            }
        });
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Run later thread "+ Thread.currentThread().getName());
//            }
//        });
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });
        stage.show();
    }

    static void setRootAn(AnchorPane an) throws IOException {
        // 不断地切换桌布的过程！ 一个桌布包含一个布局   一个玻璃纸包含一个布局  每一个桌布都是一张玻璃纸 看似没有 实则有！
        scene.setRoot(an);
    }
    static void setRoot(String fxml) throws IOException {
        // 不断地切换桌布的过程！ 一个桌布包含一个布局   一个玻璃纸包含一个布局  每一个桌布都是一张玻璃纸 看似没有 实则有！
        scene.setRoot(loadFXML(fxml));
    }


    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
       //写法1.
        // launch(args);
        //写法2
//        Application.launch(App.class,args);
        // 写法3
        // 新建一个 Launch类, 继承Application类，实现Start方法
//        Application.launch(Launch.class,args);

        // 写法4 最简单，什么也不添加！
        launch(args);
//        Application.launch(Launch.class,args); //最全面的写法，可以分离开main和Launch类，当前写法是集成在一起
        // 所以Javafx的起始点是Application类 https://www.bilibili.com/video/BV1nW411r7sW
    }

}