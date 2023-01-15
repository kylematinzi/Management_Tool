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


    /**
     * This function creates a new ticket when the create ticket button is pressed. Upon pressing the button the function
     * gathers the applicable data from the specified data fields and executes a SQL insert. Once successfully inserted
     * the gui will prompt the user to let them know the action was completed successfully.
     * @param actionEvent
     * @throws SQLException
     */
    //TODO need to add input validation
    public void createTicketButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO Ticket_Table(Ticket_Id, Project_Association, Ticket_Title, Priority_Level,"+
                " Ticket_Status, Date_Created, Date_Completed, Ticket_Description)Values (?,?,?,?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try{
            int ticketId = Integer.parseInt(ticketIdTextField.getText());
            int projectAssociation = projectNameComboBox.getValue();
            String ticketTitle = ticketTitleTextField.getText();
            String ticketPriorityLevel = priorityLevelComboBox.getValue();
            String ticketStatus = statusComboBox.getValue();
            LocalDate dateCreated = LocalDate.now();
            String ticketDescription = ticketDescriptionTextArea.getText();
            System.out.println(projectNameComboBox.getValue().toString());
            // TODO finish input validation
            if (projectNameComboBox.getValue().toString().equals("Project Id") || ticketTitle.isEmpty()){
                System.out.println("FOUND FOUND FOUND");
            }
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

    /**
     * This method creates a unique ticket id by finding the highest current ticket id and incrementing the next id by
     * plus one.
     * @return unique id in the form of an int.
     * @throws SQLException
     */
    public int createUniqueTicketId() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String maxStatement = "SELECT MAX(Ticket_Id) from Ticket_Table;";
        DatabaseQuery.setPreparedStatement(conn, maxStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) + 1;
    }

    /**
     * This method closes the new ticket screen when the close button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method initializes the new ticket screen with the correct data to display to the user.
     * @param url
     * @param resourceBundle
     */
    //TODO fix the radio button selection. Need to refresh both selected and all.
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
