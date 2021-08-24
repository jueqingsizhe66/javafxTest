package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class ChoiceButtonController{
    @FXML
    private CheckBox ck_tuna;
    // 等效于 CheckBox ck_tuna= new Checkbox("tuna"); 并添加到layout，加载到scene的过程
//    VBox layout = new VBox(10);
//        layout.setPadding(new Insets(20, 20, 20, 20));
//        layout.getChildren().addAll(box1, box2, button);
    // 只不过现在只需要在界面中设计，然后这里注解即可
    //
    @FXML
    private CheckBox ck_luna; // 默认设置为selected 在fxml界面

    @FXML
    private Button bt_select;

    @FXML
    private ChoiceBox cb_food;



    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    public void notifySelected(){
        String message = "Users select:  \n";
        if(ck_tuna.isSelected()){
            message += " Tuna. \n";
        }
        if(ck_luna.isSelected()){
            message += " Luna. \n";
        }
        System.out.println(message);
    }

    @FXML
    public void mouseOnDragOver(){
        // 最好是在fxml中设置  choicebox设置value
//        cb_food.getItems().add("Banana");
//        cb_food.getItems().addAll("Apple","Bacon","Watermelan", "Strawberry");
//        cb_food.setValue("Apple");

        //早先没有fxml通过书写java代码 解决，设置setValue 来表示默认值
//        ChoiceBox<String> choiceBox = new ChoiceBox<>();
//
//        //getItems returns the ObservableList object which you can add items to
//        choiceBox.getItems().add("Apples");
//        choiceBox.getItems().add("Bananas");
//        choiceBox.getItems().addAll("Bacon", "Ham", "Meatballs");
//
//        //Set a default value
//        choiceBox.setValue("Apples");
//
//        button.setOnAction(e -> getChoice(choiceBox));

    }
    @FXML
    public void message() throws IOException {
        String food = (String) cb_food.getValue();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("消息通知框");
        alert.setHeaderText("Look, an information dialog");
        alert.setContentText(food);
        alert.showAndWait();
    }
}
