package com.example.managementtool;

//import com.almasb.fxgl.net.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public static void main(String[] args) {

        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://management-tool-database.c9wsdjztcano.us-east-1.rds.amazonaws.com:3306/Management_Tool_Database","admin", "AdmiN305");
        Statement statement = (connection).createStatement();
        ResultSet resultSet = statement.executeQuery("Select * from Employee_Table");

        while (resultSet.next()){
            System.out.println(resultSet.getString("Employee_First_Name"));
        }

    } catch (Exception e){
            e.printStackTrace();
        }
        // maybe here
        launch();
    }
}