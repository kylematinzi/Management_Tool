package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    public void viewUserButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane userDetailParent = new StackPane();
        userDetailParent.getChildren().add(FXMLLoader.load(getClass().getResource("userDetailsScreen.fxml")));
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

    public void createUserButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane userDetailParent = new StackPane();
        userDetailParent.getChildren().add(FXMLLoader.load(getClass().getResource("CreateNewUserScreen.fxml")));
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

    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
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
