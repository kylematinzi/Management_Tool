package com.example.managementtool;

import Utility.DatabaseAccess;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectDashboardController implements Initializable {

    private int count = 0;
    @FXML
    private Label projectTitleLabel;
    @FXML
    private Label projectIdLabel;
    @FXML
    private Label dateCreatedLabel;
    @FXML
    private Label daysOpenLabel;
    @FXML
    private Label openTicketsLabel;
    @FXML
    private Label completedTicketsLabel;
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private DatePicker projectedCompletionDatePicker;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView teamMembersListView;
    @FXML
    private ComboBox teamMemberComboBox;
    @FXML
    private Button viewTeamMemberButton;
    @FXML
    private Button removeTeamMemberButton;
    @FXML
    private Button addTeamMemberButton;
    @FXML
    private Button addTicketButton;
    @FXML
    private Button editTicketButton;
    @FXML
    private Button deleteTicketButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private TableView<Ticket> ticketTableView;
    @FXML
    private TableColumn<Ticket, Integer> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> ticketTitleColumn;
    @FXML
    private TableColumn<Ticket, String> ticketStatusColumn;
    @FXML
    private TableColumn<Ticket, String> ticketDescriptionColumn;

    @FXML
    private ProgressBar projectProgressBar;

    private Project selectedProject;
    private int daysOpen;
    private int ticketId;
    private String ticketTitle;
    private String projectTitle;
    private LocalDate projectedCompletion;
    private String projectDescription;
    private int projectId;

    //TODO need to add input validation
    public void saveChangesButtonPressed(ActionEvent actionEvent) throws SQLException {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String updateStatement = "UPDATE Project_Table SET Project_Title = ?, Projected_Completion = ?, Project_Description = ? " +
                    "WHERE Project_Id = ?";
            DatabaseQuery.setPreparedStatement(conn, updateStatement);
            projectTitle = projectTitleTextField.getText();
            projectedCompletion = projectedCompletionDatePicker.getValue();
            projectDescription = projectDescriptionTextArea.getText();
            projectId = Integer.parseInt(projectIdLabel.getText());
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setString(1, projectTitle);
            ps.setDate(2, Date.valueOf(projectedCompletion));
            ps.setString(3, projectDescription);
            ps.setInt(4, projectId);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Project Id: " + projectId+": "+projectTitle+ " has been updated.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
            AdminDashboardController tableRefresh = new AdminDashboardController();
            tableRefresh.refreshAdminProjectTable();
        }
        catch(Exception e){
        }

    }

    public void addTicketButtonPressed(ActionEvent actionEvent){
        System.out.println("add ticket button pressed");
    }

    public void editTicketButtonPressed(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TicketDetailsScreen.fxml"));
            StackPane ticketDetailParent = new StackPane();
            //projectDashboardParent.getChildren().add(FXMLLoader.load(getClass().getResource("projectDashboard.fxml")));
            ticketDetailParent.getChildren().add(loader.load());
            Scene scene = new Scene(ticketDetailParent);
            Stage ticketDetailScreen = new Stage();
            ticketDetailScreen.setScene(scene);
            //new line for initialize below
            TicketDetailsScreenController controller = loader.getController();
            controller.getInitializeData(ticketTableView.getSelectionModel().getSelectedItem());
            ticketDetailScreen.initModality(Modality.WINDOW_MODAL);
            ticketDetailScreen.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            ticketDetailScreen.sizeToScene();
            ticketDetailScreen.setResizable(false);
            ticketDetailScreen.setTitle("Project Management System");
            ticketDetailScreen.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No ticket selected");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
        System.out.println("edit ticket button pressed");
    }

    public void deleteTicketButtonPressed(ActionEvent actionEvent){
        try{
            Connection conn = DatabaseConnection.getConnection();
            String deleteStatement = "DELETE FROM Ticket_Table WHERE Ticket_Id = ?";
            DatabaseQuery.setPreparedStatement(conn, deleteStatement);
            Ticket deletingTicket = ticketTableView.getSelectionModel().getSelectedItem();
            ticketId = deletingTicket.getTicketId();
            ticketTitle = deletingTicket.getTicketTitle();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            if (deletingTicket == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No ticket selected for deletion.");
                alert.setTitle("Empty Selection");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete: "+"\n"
                        +"Ticket ID: "+ ticketId +"\n"+
                        "Ticket Title: "+ticketTitle);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ps.setInt(1, ticketId);
                    ps.execute();
                }
            }
            ticketTableView.setItems(DatabaseAccess.getSelectedTickets(selectedProject.getProjectId()));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No project selected to delete.");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
        System.out.println("delete ticket button pressed");
    }

    public void viewTeamMemberButtonPressed(ActionEvent actionEvent){
        System.out.println("view button pressed");
    }

    public void removeTeamMemberButtonPressed(ActionEvent actionEvent){
        System.out.println("remove button pressed");
    }

    public void addTeamMemberButtonPressed(ActionEvent actionEvent){
        System.out.println("add member button pressed");
    }

    public void closeButtonPressed(ActionEvent actionEvent){
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void getInitializeData(Project project){
        selectedProject = project;
        projectTitleLabel.setText(project.getProjectTitle());
        projectIdLabel.setText(String.valueOf(project.getProjectId()));
        projectTitleTextField.setText(project.getProjectTitle());
        dateCreatedLabel.setText(project.getDateCreated().toString());
        projectDescriptionTextArea.setText(project.getProjectDescription());
        projectedCompletionDatePicker.setValue(project.getDateCompleted());
        daysOpen = Period.between(project.getDateCreated(), LocalDate.now()).getDays();
        daysOpenLabel.setText(String.valueOf(daysOpen));
        ticketTableView.setItems(DatabaseAccess.getSelectedTickets(project.getProjectId()));
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketTitleColumn.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        ticketStatusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatusLevel"));
        ticketDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("ticketDescription"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
