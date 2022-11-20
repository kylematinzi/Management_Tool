package com.example.managementtool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsScreenController implements Initializable {

    @FXML
    private Label userNameLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label userJobTitleLabel;
    @FXML
    private Label userEmailAddressLabel;

    private Employee selectedEmployee;

    public void getInitializeData(Employee employee){
        selectedEmployee = employee;
        userIdLabel.setText(String.valueOf(employee.getEmployeeId()));
        userNameLabel.setText(employee.getEmployeeFirstName()+" "+employee.getEmployeeLastName());
        userJobTitleLabel.setText(employee.getJobTitle());
        userEmailAddressLabel.setText(employee.getEmailAddress());
        System.out.println("runningggggg");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
