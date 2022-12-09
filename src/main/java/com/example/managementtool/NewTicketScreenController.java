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
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
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
    private int ticketId;
    private int projectAssociation;
    private String ticketTitle;
    private String ticketPriorityLevel;
    private String ticketStatus;
    private LocalDate dateCreated;
    private LocalDate dateCompleted;
    private String ticketDescription;
    private int newTicketId;

    //TODO fix the radio button selection. Need to refresh both selected and all.
    //TODO Not Started option will not work for ticket creation. It wont work becuase the table is only taking not starte.
    // I need to figure out how to add one more character.
    //TODO need to add input validation
    public void createTicketButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO Ticket_Table(Ticket_Id, Project_Association, Ticket_Title, Priority_Level,"+
                " Ticket_Status, Date_Created, Date_Completed, Ticket_Description)Values (?,?,?,?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try{
            ticketId = Integer.parseInt(ticketIdTextField.getText());
            // need to change what's in the project combo box to project not just names.
            //projectAssociation = (int) projectNameComboBox.getValue();
            projectAssociation = 1001; // temporary
            ticketTitle = ticketTitleTextField.getText();
            ticketPriorityLevel = priorityLevelComboBox.getValue().toString();
            ticketStatus = statusComboBox.getValue().toString();
            dateCreated = LocalDate.now();
            dateCompleted = LocalDate.now(); // figure this out later probably give a filler date.
            ticketDescription = ticketDescriptionTextArea.getText();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, ticketId);
            ps.setInt(2, projectAssociation);
            ps.setString(3, ticketTitle);
            ps.setString(4, ticketPriorityLevel);
            ps.setString(5, ticketStatus);
            ps.setDate(6, Date.valueOf(dateCreated));
            ps.setDate(7, Date.valueOf(dateCompleted));
            ps.setString(8, ticketDescription);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ticket Id: " + ticketId +": "+ticketTitle+ " has been added.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
            AdminDashboardController tableRefresh = new AdminDashboardController();
            tableRefresh.refreshAdminTicketTable();
        }
        catch (Exception e){

        }
    }

    public int createUniqueTicketId() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String maxStatement = "SELECT MAX(Ticket_Id) from Ticket_Table;";
        DatabaseQuery.setPreparedStatement(conn, maxStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        rs.next();
        newTicketId = rs.getInt(1) + 1;
        return newTicketId;
    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityLevelComboBox.setItems(DatabaseAccess.getTicketPriorityLevels());
        statusComboBox.setItems(DatabaseAccess.getTicketStatusTypes());
        //projectNameComboBox.setItems(DatabaseAccess.getAllProjects());
        try{
            ticketIdTextField.setText(String.valueOf(createUniqueTicketId()));
        }
        catch (Exception e){

        }
    }
}
