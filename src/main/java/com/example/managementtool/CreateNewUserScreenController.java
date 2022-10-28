package com.example.managementtool;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewUserScreenController {

    private Button closeButton;

    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
