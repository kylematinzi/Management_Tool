package com.example.managementtool;

import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is the main class of the application and where running the application starts. Throughout the application
 * stack panes will be used to call upon new windows. I have chosen this method to prevent the user from interacting with
 * any screen except the most recently called one. This will ensure the user cannot open a screen, move back to one that
 * the new screen called on for data, change the data in the previous screen, and then making the data in the current screen
 * incorrect.
 */
//TODO Add docker
public class ManagementToolMain extends Application {

    /**
     * This method is used to call on the first screen of the application, which is the login screen.
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        StackPane stackRoot = new StackPane();
        stackRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScreen.fxml"))));
        Scene scene = new Scene(stackRoot);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Project Management System");
        primaryStage.show();
    }

    /**
     * This method is the main method. This method starts and closes the connection with the database at the appropriate
     * times.
     * @param args
     * @throws SQLException
     */
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