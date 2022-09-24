package org.openjfx;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageViewWithBlurController implements Initializable {
    @FXML
    private AnchorPane ap_main;
    @FXML
    private ImageView iv_huailai;
    @FXML
    private Button btn_pullout;
    @FXML
    private Button btn_pushback;

    private TranslateTransition tt;
    private ImageView iv_blur;  // 截取图片

    // 目的让隐藏层宽度为300  放在-300 -0 区域
    // 然后拉出运动的时候从-300 到0
    // 然后推回运动的时候从0 到-300
    private int hidepaneWidth = 200;
    private int gaussBlurConstant = 30;
    // 指定拉出动作的时间
    private double pullTime = 0.5;


    @FXML
    public void pullout() {

        tt.setFromX(-hidepaneWidth);
        tt.setToX(0);
        tt.play();
    }

    @FXML
    public void pushback() {
        tt.setFromX(0);
        tt.setToX(-hidepaneWidth);
        tt.play();

    }

    ;


    //region 初始化
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iv_huailai.fitHeightProperty().bind(ap_main.heightProperty());
        iv_huailai.fitWidthProperty().bind(ap_main.widthProperty());
        Node node = getView(ap_main);
        ap_main.getChildren().add(node);

        tt = new TranslateTransition(Duration.seconds(this.pullTime), node);
        tt.setInterpolator(Interpolator.EASE_OUT);

        //region ChangeListener<Number>
        node.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int w = hidepaneWidth - (-newValue.intValue());
                // 图像从 -hidepaneWidth 到0 拉出来
                // 图像从 00 到 -hidepaneWidth 推回去
                System.out.println(newValue.intValue());

                int h = (int) ap_main.getHeight();
                if (w > 0) {
                    WritableImage wi = new WritableImage(w, h);
                    iv_huailai.snapshot(new SnapshotParameters(), wi);
                    iv_blur.setImage(wi);
                }
            }
        });
        //endregion
    }
    //endregion


    // 形成一个隐藏层
    public Node getView(AnchorPane ap_main) {
        StackPane sp = new StackPane();

        AnchorPane ap_hide = new AnchorPane();

        iv_blur = new ImageView();

        // 居然使用AnchorPane 这是我没考虑到的
        ap_main.setRightAnchor(iv_blur, 0.0);

        iv_blur.setEffect(new GaussianBlur(this.gaussBlurConstant));

        ap_hide.getChildren().add(iv_blur);

        VBox vbox = new VBox(20);
        vbox.setPrefWidth(hidepaneWidth);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setBorder(new Border(new BorderStroke(Color.valueOf("#45454555"), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
//  -fx-background-color: #ffffff22 更浅
//  -fx-background-color: #ffffff99 更黑
        vbox.setStyle("-fx-background-color: #ffffff22");

        for (int i = 0; i < 10; i++) {
            vbox.getChildren().add(new Text("Hello javafx         ! " + i));
        }

        sp.getChildren().addAll(ap_hide, vbox);
        sp.setTranslateX(-hidepaneWidth);

        vbox.prefHeightProperty().bind(ap_main.heightProperty());
        return sp;

    }
}
