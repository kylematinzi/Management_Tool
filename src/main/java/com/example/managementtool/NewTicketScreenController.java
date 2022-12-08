package com.example.managementtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTicketScreenController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button createTicketButton;
    @FXML
    private ComboBox projectNameComboBox;
    @FXML
    private ComboBox teamMemberComboBox;
    @FXML
    private ComboBox priorityLevelComboBox;
    @FXML
    private ComboBox statusComboBox;
    @FXML
    private TableView assignedMemberTable;
    @FXML
    private TextField ticketIdTextField;
    @FXML
    private TextField ticketTitleTextField;
    @FXML
    private TextArea ticketDescriptionTextArea;

    public void createTicketButtonPressed(ActionEvent actionEvent){
        System.out.println("pressed");
    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
