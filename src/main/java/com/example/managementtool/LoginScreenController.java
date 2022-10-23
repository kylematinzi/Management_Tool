package com.example.managementtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private Button closeButton;

    public void loginButtonPressed (ActionEvent actionEvent) throws IOException {
        StackPane adminDashboardParent = new StackPane();
        adminDashboardParent.getChildren().add(FXMLLoader.load(getClass().getResource("AdminDashboard.fxml")));
        Scene scene = new Scene(adminDashboardParent);
        Stage adminDashboardScene = new Stage();
        adminDashboardScene.setScene(scene);
        adminDashboardScene.initModality(Modality.WINDOW_MODAL);
        adminDashboardScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        adminDashboardScene.sizeToScene();
        adminDashboardScene.setResizable(false);
        adminDashboardScene.setTitle("Project Management System");
        adminDashboardScene.show();
    }

    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
