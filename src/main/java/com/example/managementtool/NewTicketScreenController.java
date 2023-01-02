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

/**
 * This class is the new ticket screen controller. This screen allows the user to create a new ticket and assign it to
 * the appropriate project. The ticket's id is unique, automatically generated, and immutable. The user may create the
 * ticket name,ticket priority (Low, Medium, High), ticket status (Not Started, In Work, Complete), ticket description,
 * and add team members to the ticket.
 */
public class NewTicketScreenController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button createTicketButton;
    @FXML
    private ComboBox<Integer> projectNameComboBox;
    @FXML
    private ComboBox<String> teamMemberComboBox;
    @FXML
    private ComboBox<String> priorityLevelComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TableView<Employee> assignedMemberTable;
    @FXML
    private TextField ticketIdTextField;
    @FXML
    private TextField ticketTitleTextField;
    @FXML
    private TextArea ticketDescriptionTextArea;

    private final LocalDate defaultEndDate = LocalDate.parse("2050-01-01");

    //TODO fix the radio button selection. Need to refresh both selected and all.
    //TODO need to add input validation

    /**
     * This function creates a new ticket when the create ticket button is pressed. Upon pressing the button the function
     * gathers the applicable data from the specified data fields and executes a SQL insert. Once successfully inserted
     * the gui will prompt the user to let them know the action was completed successfully.
     * @param actionEvent
     * @throws SQLException
     */
    public void createTicketButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO Ticket_Table(Ticket_Id, Project_Association, Ticket_Title, Priority_Level,"+
                " Ticket_Status, Date_Created, Date_Completed, Ticket_Description)Values (?,?,?,?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try{
            int ticketId = Integer.parseInt(ticketIdTextField.getText());
            int projectAssociation = (int) projectNameComboBox.getValue();
            String ticketTitle = ticketTitleTextField.getText();
            String ticketPriorityLevel = priorityLevelComboBox.getValue().toString();
            String ticketStatus = statusComboBox.getValue().toString();
            LocalDate dateCreated = LocalDate.now();
            String ticketDescription = ticketDescriptionTextArea.getText();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, ticketId);
            ps.setInt(2, projectAssociation);
            ps.setString(3, ticketTitle);
            ps.setString(4, ticketPriorityLevel);
            ps.setString(5, ticketStatus);
            ps.setDate(6, Date.valueOf(dateCreated));
            ps.setDate(7, Date.valueOf(defaultEndDate));
            ps.setString(8, ticketDescription);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ticket Id: " + ticketId +": "+ ticketTitle + " has been added.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
            AdminDashboardController tableRefresh = new AdminDashboardController();
            tableRefresh.refreshAdminTicketTable();
            ProjectDashboardController screenRefresh = new ProjectDashboardController();
            screenRefresh.refreshProjectDashboardView();
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
        return rs.getInt(1) + 1;
    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityLevelComboBox.setItems(DatabaseAccess.getTicketPriorityLevels());
        statusComboBox.setItems(DatabaseAccess.getTicketStatusTypes());
        projectNameComboBox.setItems(DatabaseAccess.getAllProjectId());
        try{
            ticketIdTextField.setText(String.valueOf(createUniqueTicketId()));
        }
        catch (Exception e){

        }
    }
}
