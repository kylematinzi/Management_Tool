package com.example.managementtool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * To start this project I am starting with the GUI screens. After that I will set up and connect a database
 * to hold all the data that will fill the screens. From there I will create the functions that will enable
 * manipulation of the data in various ways through the GUI.
 *
 */
public class HelloApplication extends Application {
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
        launch();
    }
}