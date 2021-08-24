package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertController {

    @FXML
    public void message() throws IOException {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("消息通知框");
       alert.setHeaderText("Look, an information dialog");
       alert.setContentText("I have a message for you");
       alert.showAndWait();
    }

    @FXML
    public void message2() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("无标题消息通知框");
        alert.setHeaderText(null);
        alert.setContentText("I have a message for you");
        alert.showAndWait();
    }

    @FXML
    public void warning() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警示框");
        alert.setHeaderText("Look , a warning dialog");
        alert.setContentText("I have a warning for you");
        alert.showAndWait();
    }
    @FXML
    public void error() throws IOException{
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setTitle("错误框");
        error.setHeaderText("Look, an Error dialog");
        error.setContentText("Ooops, some error happens");
        error.showAndWait();
    }
    @FXML
    public void sure() throws  IOException{
        Alert sure = new Alert(Alert.AlertType.CONFIRMATION);
        sure.setTitle("Confirmation dialog");
        sure.setHeaderText("Look , an confirmation dialog");
        sure.setContentText("Are you ok with this?");

        Optional<ButtonType> result = sure.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("You choose ok!");
        }else{
            System.out.println("You choose not!");
        }
    }

    @FXML
    public void sureDefined() throws  IOException{
        Alert sure = new Alert(Alert.AlertType.CONFIRMATION);
        sure.setTitle("User defined Confirmation dialog");
        sure.setHeaderText("Look , an user defined confirmation dialog");
        sure.setContentText("Are you ok with this?");

        ButtonType buttoneTypeOne = new ButtonType("One");
        ButtonType buttoneTypeTwo = new ButtonType("Two");
        ButtonType buttoneTypeThree = new ButtonType("Three");
        ButtonType buttoneTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        sure.getButtonTypes().setAll(buttoneTypeCancel,buttoneTypeOne,buttoneTypeTwo, buttoneTypeThree);
        Optional<ButtonType> result = sure.showAndWait();
        if (result.get() == buttoneTypeOne) {
            System.out.println("Button One!");
        }else if(result.get() == buttoneTypeTwo){
            System.out.println("Button Two!");
        }else if(result.get() == buttoneTypeThree){
            System.out.printf("Button Three!");
        }else{
            System.out.println("You cancel it");
        }
    }

    @FXML
    public void inputDialog() throws IOException{
        TextInputDialog dialog = new TextInputDialog("You, input it");
        dialog.setTitle("Text Input dialog");
        dialog.setHeaderText("Look , a text dialog input for you");
        dialog.setContentText("please enter you advice");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your advice: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
//        result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    @FXML
    public void choiceDialog() throws  IOException{
        List<String> choices = new ArrayList<>();
        choices.add("a");
        choices.add("b");
        choices.add("c");
        choices.add("d");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("b",choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Look ,  a choice Dialog");
        dialog.setContentText("Choose your letter: ");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your choice: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
//        result.ifPresent(letter -> System.out.println("Your choice: " + letter));

    }
    @FXML
    public void loginDialog() throws  IOException{
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }

    @FXML
    public  void display() throws Exception {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("自己编写对话框");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("自定义的消息对话框");
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
