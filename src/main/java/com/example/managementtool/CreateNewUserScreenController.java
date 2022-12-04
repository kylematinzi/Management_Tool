package com.example.managementtool;

import Utility.DatabaseAccess;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
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

    private int employeeId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String jobTitle;
    private String emailAddress;
    private int count = 25;


    public void saveButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO Employee_Table(Employee_Id, Employee_First_Name, Employee_Last_Name,"
        + "Username, Job_Title, Email_Address, Employee_Password) VALUES (?,?,?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try {
            employeeId = count;
            firstName = "Jonny";
            lastName = "Max";
            userName = "JMax";
            jobTitle = "Developer";
            emailAddress = "JohnMax@gmail.com";
            password = "Test";
            // Save new user
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, employeeId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, userName);
            ps.setString(5, jobTitle);
            ps.setString(6, emailAddress);
            ps.setString(7, password);
            ps.execute();
            count++;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Employee: " + firstName+" "+lastName+ " has been added.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
        } catch (Exception e){

        }
    }

    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobTitleComboBox.setItems(DatabaseAccess.getJobTitles());
    }
}
