package com.example.managementtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDetailDashboardController {

    @FXML
    private Button closeButton;

    @FXML Button viewUserButton;

    @FXML Button createUserButton;

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
}
