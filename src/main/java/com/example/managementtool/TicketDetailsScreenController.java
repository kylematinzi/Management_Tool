package com.example.managementtool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TicketDetailsScreenController implements Initializable {

    private Ticket selectedTicket;
    @FXML
    private TextField ticketTitleTextField;
    @FXML
    private TextField ticketIdTextField;
    @FXML
    private TextArea ticketDescriptionTextArea;
    @FXML
    private ComboBox ticketStatusComboBox;
    @FXML
    private ComboBox ticketPriorityComboBox;
    private ObservableList<String> ticketStatusList = FXCollections.observableArrayList();
    private ObservableList<String> ticketPriorityList = FXCollections.observableArrayList();
    public void getInitializeData(Ticket ticket){
        selectedTicket = ticket;
        ticketTitleTextField.setText(ticket.getTicketTitle());
        ticketIdTextField.setText(String.valueOf(ticket.getTicketId()));
        ticketDescriptionTextArea.setText(ticket.getTicketDescription());
    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketStatusList.add("Not Started");
        ticketStatusList.add("In Work");
        ticketStatusList.add("Complete");
        ticketStatusComboBox.setItems(ticketStatusList);
        ticketPriorityList.add("Low");
        ticketPriorityList.add("Medium");
        ticketPriorityList.add("High");
        ticketPriorityComboBox.setItems(ticketPriorityList);
    }
}
