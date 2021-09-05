package org.openjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    }

    @FXML
    private void switchToMFTreeView() throws IOException {
        // 文件名 大小写敏感
//        App.setRoot("materialsFX/TreeViewsDemo");
    }

    @FXML
    private void switchToMFListView() throws IOException {
        // 文件名 大小写敏感
//        App.setRoot("materialsFX/ListViewsDemo");
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
}

