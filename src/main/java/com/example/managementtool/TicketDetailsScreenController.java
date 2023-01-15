package com.example.managementtool;

import Utility.DatabaseAccess;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the ticket details screen controller. This screen displays to the user a selected tickets details and allows
 * them to edit the ticket.
 */
public class TicketDetailsScreenController implements Initializable {

    @FXML
    private TextField ticketTitleTextField;
    @FXML
    private TextField ticketIdTextField;
    @FXML
    private TextArea ticketDescriptionTextArea;
    @FXML
    private ComboBox<String> ticketStatusComboBox;
    @FXML
    private ComboBox<String> ticketPriorityComboBox;
    @FXML
    private ComboBox<Integer> projectIdComboBox;
    @FXML
    private Label dateCreatedLabel;

    /**
     * This method is used to retrieve the data to initialize the ticket detail screen with the correct default data.
     * @param ticket - selected ticket
     */
    public void getInitializeData(Ticket ticket){
        ticketTitleTextField.setText(ticket.getTicketTitle());
        projectIdComboBox.setValue(ticket.getProjectAssociation());
        ticketIdTextField.setText(String.valueOf(ticket.getTicketId()));
        ticketDescriptionTextArea.setText(ticket.getTicketDescription());
        dateCreatedLabel.setText(ticket.getDateCreated().toString());
        ticketStatusComboBox.setValue(ticket.getTicketStatusLevel());
        ticketPriorityComboBox.setValue(ticket.getTicketPriorityLevel());
    }

    /**
     * This method is used to update the ticket details when the save changes button is pressed. When pressed the method
     * sends an update query to the database updating any fields the user updated provided the ticket detail screen.
     * @param actionEvent
     */
    // TODO create function that if user selects ticket complete as status date completed is set.
    public void saveChangesButtonPressed(ActionEvent actionEvent) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String updateStatement = "UPDATE Ticket_Table SET Ticket_Title = ?, Project_Association = ?, Priority_Level = ?, " +
                    " Ticket_Status = ?, Ticket_Description = ? WHERE Ticket_Id = ?";
            DatabaseQuery.setPreparedStatement(conn, updateStatement);
            String ticketTitle = ticketTitleTextField.getText();
            int projectAssociation = projectIdComboBox.getValue();
            String ticketPriority = String.valueOf(ticketPriorityComboBox.getValue());
            String ticketStatus = String.valueOf(ticketStatusComboBox.getValue());
            String ticketDescription = ticketDescriptionTextArea.getText();
            int ticketId = Integer.parseInt(ticketIdTextField.getText());
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setString(1, ticketTitle);
            ps.setInt(2, projectAssociation);
            ps.setString(3, ticketPriority);
            ps.setString(4, ticketStatus);
            ps.setString(5, ticketDescription);
            ps.setInt(6, ticketId);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ticket Id: " + ticketId + ": " + ticketTitle + " has been updated.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
            AdminDashboardController tableRefresh = new AdminDashboardController();
            tableRefresh.refreshAdminTicketTable();
        }catch (Exception ignored){
        }
    }

    /**
     * This method removes a team member from the selected ticket when the remove team member button is pressed.
     * @param actionEvent
     */
    //TODO create method
    public void removeTeamMemberButtonPressed(ActionEvent actionEvent){
        System.out.println("remove team pressed");
    }

    /**
     * This method closes the ticket details screen when the close button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method initializes the ticket details screen with the correct data when the ticket details screen is loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketStatusComboBox.setItems(DatabaseAccess.getTicketStatusTypes());
        ticketPriorityComboBox.setItems(DatabaseAccess.getTicketPriorityLevels());
        projectIdComboBox.setItems(DatabaseAccess.getAllProjectId());
    }
}
