package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.data.Entity.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//https://code.makery.ch/zh-cn/library/javafx-tutorial/part1/
public class PersonalOverviewController implements Initializable {
// 也可以使用splitPane设计，当前用的是BorderPane
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> tv_person;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label lv_firstname;
    @FXML
    private Label lv_lastname;
    @FXML
    private Label lv_street;
    @FXML
    private Label lv_code;
    @FXML
    private Label lv_city;
    @FXML
    private Label lv_birthday;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));

        tv_person.setItems(personData);

        // https://www.bilibili.com/video/av34804641
       firstNameColumn.setCellValueFactory(celldata -> celldata.getValue().firstNameProperty());
       lastNameColumn.setCellValueFactory(celldata->celldata.getValue().lastNameProperty());
    }

    @FXML
    public boolean onMouseClic(){
        Person person= tv_person.getSelectionModel().getSelectedItem();

        if (person != null) {
            // Fill the labels with info from the person object.
            lv_firstname.setText(person.getFirstName());
            lv_lastname.setText(person.getLastName());
            lv_street.setText(person.getStreet());
            lv_code.setText(Integer.toString(person.getPostalCode()));
            lv_city.setText(person.getCity());

            // TODO: We need a way to convert the birthday into a String!
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            lv_firstname.setText("");
            lv_lastname.setText("");
            lv_street.setText("");
            lv_code.setText("");
            lv_city.setText("");
            lv_birthday.setText("");
        }
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonalOverviewController.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } 
    }
}
