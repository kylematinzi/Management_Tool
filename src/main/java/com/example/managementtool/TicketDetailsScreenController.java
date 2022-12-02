package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private ComboBox projectNameComboBox;
    @FXML
    private Label dateCreatedLabel;
    public void getInitializeData(Ticket ticket){
        // TODO to set the project combo box I need to join the ticket and project tables.
        selectedTicket = ticket;
        ticketTitleTextField.setText(ticket.getTicketTitle());
        ticketIdTextField.setText(String.valueOf(ticket.getTicketId()));
        ticketDescriptionTextArea.setText(ticket.getTicketDescription());
        dateCreatedLabel.setText(ticket.getDateCreated().toString());
        ticketStatusComboBox.setValue(ticket.getTicketStatusLevel());
        ticketPriorityComboBox.setValue(ticket.getTicketPriorityLevel());


    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketStatusComboBox.setItems(DatabaseAccess.getTicketStatusTypes());
        ticketPriorityComboBox.setItems(DatabaseAccess.getTicketPriorityLevels());
        projectNameComboBox.setItems(DatabaseAccess.getAllProjectNames());
    }
}
