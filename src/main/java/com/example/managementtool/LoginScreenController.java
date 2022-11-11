package com.example.managementtool;

import Utility.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextField usernameTextBox;
    @FXML
    private PasswordField passwordField;
    private String userName;
    private String passWord;
    private boolean usernamePasswordFound = false;
    private Statement statement = null;


    public void loginButtonPressed (ActionEvent actionEvent) throws IOException, SQLException {
        Connection conn = DatabaseConnection.getConnection();
        statement = conn.createStatement();
        userName = usernameTextBox.getText();
        passWord = passwordField.getText();
        String sqlFinder = "SELECT Username, Employee_Password FROM Employee_Table";
        ResultSet rs = statement.executeQuery(sqlFinder);
        while(rs.next()){
            String UserName = rs.getString("Username");
            String PassWord = rs.getString("Employee_Password");
            if(UserName.equals(userName) && PassWord.equals(passWord)){
                usernamePasswordFound = true;
            }
        }
        rs.close();
        if(usernamePasswordFound == true) {
            StackPane adminDashboardParent = new StackPane();
            adminDashboardParent.getChildren().add(FXMLLoader.load(getClass().getResource("adminDashboard.fxml")));
            Scene scene = new Scene(adminDashboardParent);
            Stage adminDashboardScene = new Stage();
            adminDashboardScene.setScene(scene);
            adminDashboardScene.initModality(Modality.WINDOW_MODAL);
            adminDashboardScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            adminDashboardScene.sizeToScene();
            adminDashboardScene.setResizable(false);
            adminDashboardScene.setTitle("Project Management System");
            adminDashboardScene.show();
            usernamePasswordFound = false;
        }
        else{
            System.out.println("Password Incorrect.");
        }
    }

    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
