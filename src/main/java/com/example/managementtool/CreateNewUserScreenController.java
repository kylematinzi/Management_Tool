package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewUserScreenController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField employeeIdTextField;
    @FXML
    private ComboBox jobTitleComboBox;
    @FXML
    private TextField emailAddressTextField;


    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobTitleComboBox.setItems(DatabaseAccess.getJobTitles());
    }
}
