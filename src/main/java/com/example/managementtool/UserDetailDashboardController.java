package com.example.managementtool;

import Utility.DatabaseAccess;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserDetailDashboardController implements Initializable {

    @FXML
    private Button closeButton;

    @FXML Button viewUserButton;

    @FXML Button createUserButton;
    @FXML
    private TableView<Employee> allUsersTable;
    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Employee, String> firstNameColumn;
    @FXML
    private TableColumn<Employee, String> lastNameColumn;
    @FXML
    private TableColumn<Employee, String> usernameColumn;
    @FXML
    private TableColumn<Employee, String> jobTitleColumn;
    @FXML
    private TableColumn<Employee, String> emailAddressColumn;
    private int employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private int maxNumber;

    //TODO create the user edit function.
    public void viewUserButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserDetailsScreen.fxml"));
            StackPane userDashboardParent = new StackPane();
            userDashboardParent.getChildren().add(loader.load());
            Scene scene = new Scene(userDashboardParent);
            Stage userDetailScene = new Stage();
            userDetailScene.setScene(scene);
            //new line for initialize below
            UserDetailsScreenController controller = loader.getController();
            controller.getInitializeData(allUsersTable.getSelectionModel().getSelectedItem());
            userDetailScene.initModality(Modality.WINDOW_MODAL);
            userDetailScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            userDetailScene.sizeToScene();
            userDetailScene.setResizable(false);
            userDetailScene.setTitle("Project Management System");
            userDetailScene.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No user selected");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    public void createUserButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane userDetailParent = new StackPane();
        userDetailParent.getChildren().add(FXMLLoader.load(getClass().getResource("createNewUserScreen.fxml")));
        Scene scene = new Scene(userDetailParent);
        Stage userDetailScene = new Stage();
        userDetailScene.setScene(scene);
        userDetailScene.initModality(Modality.WINDOW_MODAL);
        userDetailScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        userDetailScene.sizeToScene();
        userDetailScene.setResizable(false);
        userDetailScene.setTitle("Project Management System");
        userDetailScene.show();
    }

    public void removeUserButtonPressed(ActionEvent actionEvent){
        try{
            Connection conn = DatabaseConnection.getConnection();
            String deleteStatement = "DELETE FROM Employee_Table WHERE Employee_Id = ?";
            DatabaseQuery.setPreparedStatement(conn, deleteStatement);
            Employee deletingEmployee = allUsersTable.getSelectionModel().getSelectedItem();
            employeeId = deletingEmployee.getEmployeeId();
            employeeFirstName = deletingEmployee.getEmployeeFirstName();
            employeeLastName = deletingEmployee.getEmployeeLastName();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            if (deletingEmployee == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No user selected for deletion.");
                alert.setTitle("Empty Selection");
                alert.showAndWait();
            } else {
                // The admin id will not be removable
                if(employeeId == 111111){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Admin may not be deleted.");
                    alert.setTitle("Warning");
                    Optional<ButtonType> result = alert.showAndWait();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete: " + "\n"
                            + "Employee ID: " + employeeId + "\n" +
                            "User: " + employeeFirstName + " " + employeeLastName);
                    alert.setTitle("Confirmation");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ps.setInt(1, employeeId);
                        ps.execute();
                    }
                }
            }
            allUsersTable.setItems(DatabaseAccess.getAllEmployees());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No user selected to delete.");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void generateTable(){
        allUsersTable.setItems(DatabaseAccess.getAllEmployees());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allUsersTable.setItems(DatabaseAccess.getAllEmployees());
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeFirstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeLastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        emailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
    }
}
