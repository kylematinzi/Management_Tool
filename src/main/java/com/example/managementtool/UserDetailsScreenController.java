package com.example.managementtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the user detail screen controller. This is a simple screen that only displays data about a selected user.
 */
public class UserDetailsScreenController implements Initializable {

    @FXML
    private Label userNameLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label userJobTitleLabel;
    @FXML
    private Label userEmailAddressLabel;

    /**
     * This method will retrieve the correct data to initialize the user details screen.
     * @param employee - user data to be displayed
     */
    public void getInitializeData(Employee employee){
        userIdLabel.setText(String.valueOf(employee.getEmployeeId()));
        userNameLabel.setText(employee.getEmployeeFirstName()+" "+employee.getEmployeeLastName());
        userJobTitleLabel.setText(employee.getJobTitle());
        userEmailAddressLabel.setText(employee.getEmailAddress());
    }

    /**
     * This method will close the user details screen controller when the close button is pressed.
     * @param actionEvent
     */
    public void closeButtonPressed(ActionEvent actionEvent){
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method can be used to initialize the user detail screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
