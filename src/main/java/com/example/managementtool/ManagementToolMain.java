package com.example.managementtool;

//import com.almasb.fxgl.net.Connection;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;
import java.io.IOException;

/**
 * To start this project I am starting with the GUI screens. After that I will set up and connect a database
 * to hold all the data that will fill the screens. From there I will create the functions that will enable
 * manipulation of the data in various ways through the GUI.
 *
 */
//TODO Add docker
//TODO Create AWS database
public class ManagementToolMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Using stackPane in order to disallow the user to interact with any screen except the
        // top screen.
        StackPane stackRoot = new StackPane();
        stackRoot.getChildren().add(FXMLLoader.load(getClass().getResource("LoginScreen.fxml")));
        Scene scene = new Scene(stackRoot);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Project Management System");
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnection.startConnection();
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO employee_table (Employee_Id, Employee_First_Name, Employee_Last_Name, " +
                "Username, Job_Title, Email_Address, Employee_Password) Values(?,?,?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        launch();
        DatabaseConnection.closeConnection();
    }
}