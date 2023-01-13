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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the create new user screen controller. This screen will allow the admin to create a new user / employee
 * and add them to the database.
 */
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
    private ComboBox<String> jobTitleComboBox;
    @FXML
    private TextField emailAddressTextField;
    private String userName;

    /**
     * This method creates and saves a new user / employee to the database when the save button is pressed based on the
     * information in the given entry fields on the screen.
     * @param actionEvent
     * @throws SQLException
     */
    //TODO input validation
    public void saveButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO Employee_Table(Employee_Id, Employee_First_Name, Employee_Last_Name,"
        + "Username, Job_Title, Email_Address, Employee_Password) VALUES (?,?,?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try {
            int employeeId = Integer.parseInt(employeeIdTextField.getText());
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            userName = createUserName(firstName, lastName);
            String jobTitle = jobTitleComboBox.getSelectionModel().getSelectedItem().toString();
            String emailAddress = emailAddressTextField.getText();
            String password = "Test";
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Employee: " + firstName +" "+ lastName + " has been added.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
            //Refresh the table on the previous screen.
            UserDetailDashboardController tableRefresh = new UserDetailDashboardController();
            tableRefresh.refreshUserTable();
        } catch (Exception e){
        }
    }

    /**
     * This method is used to automatically generate a new user / employee's username. The method takes the first and last
     * name of the new user and combines the first letter from the first name with the full last name to create the new
     * username.
     * @param firstName - user's first name
     * @param lastName - user's last name
     * @return - new username
     */
    // TODO avoid duplicate usernames. Just going to search database and if it already exists add +1 to the username.
    public String createUserName(String firstName, String lastName){
        char firstLetter = firstName.charAt(0);
        userName = firstLetter+lastName;
        // Search database
        // if exists = true
        //  add 1 to the username "JSmith1"
        //  check again
        //  if exists = true
        //    username++ "JSmith2"
        // run loop until username is unique
        return userName;
    }

    /**
     * This method creates a unique user / employee id for new users. It locates the highest value employee id in the table
     * then increments it by one to create the new id.
     * @return - new employee id
     * @throws SQLException
     */
    public int createUniqueEmployeeId() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String maxStatement = "SELECT MAX(Employee_Id) from Employee_Table;";
        DatabaseQuery.setPreparedStatement(conn, maxStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) + 1;
    }

    /**
     * This method closes the create new user screen when the close button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method initializes the create new user screen with the correct data to display to the user.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobTitleComboBox.setItems(DatabaseAccess.getJobTitles());
        try {
            employeeIdTextField.setText(String.valueOf(createUniqueEmployeeId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
