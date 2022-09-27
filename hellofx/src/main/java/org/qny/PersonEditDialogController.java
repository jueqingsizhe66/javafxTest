package org.qny;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.data.Entity.Person;

public class PersonEditDialogController {
    private Person person;
    private Stage dialogStage;

    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_street;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_code;
    @FXML
    private TextField tf_birthday;
    @FXML
    public void handleOk(){

    }
    @FXML
    public void handleCancel(){

    }

    public void setDialogStage(Stage dialogStage) {
       this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
       this.person = person;

        if (person != null) {
            // Fill the labels with info from the person object.
            tf_firstname.setText(person.getFirstName());
            tf_lastname.setText(person.getLastName());
            tf_street.setText(person.getStreet());
            tf_code.setText(Integer.toString(person.getPostalCode()));
            tf_city.setText(person.getCity());

            // TODO: We need a way to convert the birthday into a String!
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            tf_firstname.setText("");
            tf_lastname.setText("");
            tf_street.setText("");
            tf_code.setText("");
            tf_city.setText("");
            tf_birthday.setText("");
        }
    }

    public boolean isOkClicked() {
        return false;
    }
}
