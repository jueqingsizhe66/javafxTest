package org.qny;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TreeViewController implements Initializable {

    @FXML
    private TreeView tv_soft;
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> root,bucky, megan;
        //root
        // 两个Node图标
        final Node rootIcon =
                new ImageView(new Image(getClass().getResourceAsStream("/images/文件夹.png")));
        root = new TreeItem<String>("Law",rootIcon);
        root.setExpanded(true);
        //bucky
        bucky = makeBranch("Bucky",root);
        makeBranch("thenewboston",bucky);
        makeBranch("碳刀",bucky);
        makeBranch("Youtude",bucky);
        //megan
        megan = makeBranch("megan",root);
        makeBranch("m1",megan);
        makeBranch("m2",megan);
        makeBranch("m3",megan);
        makeBranch("m4",megan);

//        tv_soft = new TreeView<>(root);
//        tv_soft.getChildrenUnmodifiable().addAll(root);
        tv_soft.setRoot(root);
        tv_soft.setShowRoot(true);
        tv_soft.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue!=null) {
                        System.out.println(newValue);
                    }
                });

//        int dex=foldertree.getSelectionModel().getSelectedIndex();
//
//        获取选中的结点下标
//
//
//        foldertree.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<TreeItem <String>>() {
//                    @Override
//                    public void changed(ObservableValue<? extends TreeItem<String>> observableValue,
//                                        TreeItem<String> oldItem, TreeItem<String> newItem) {
//                        System.out.println(newItem.getValue());
//                    }
//                });将侦听器加入到结点
    }

    private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {

        final Node depIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/branch.png")));
        TreeItem<String> item = new TreeItem<String>(title, depIcon);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

}
