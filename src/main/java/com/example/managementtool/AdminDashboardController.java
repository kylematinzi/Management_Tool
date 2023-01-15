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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the screen controller for the admin dashboard screen. This screen allows the fullest use of the application.
 * Through this screen the admin may add/update/remove employees, projects, and tickets. The admin can also view a complete
 * breakdown of all projects in the database and their progress.
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button ticketDetailsButton;
    @FXML
    private Button createNewTicketButton;
    @FXML
    private Button deleteTicketButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button ProjectsButton;
    @FXML
    private TableView<Project> allProjectsTable;
    @FXML
    private TableColumn<Project, Integer> projectIdColumn;
    @FXML
    private TableColumn<Project, String> projectTitleColumn;
    @FXML
    private TableColumn<Project, Date> startDateColumn;
    @FXML
    private TableColumn<Project, Date> endDateColumn;
    @FXML
    private TableColumn<Project, String> projectDescriptionColumn;
    @FXML
    private TableView<Ticket> allTicketsTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, Integer> projectTicketIdColumn;
    @FXML
    private TableColumn<Ticket, String> ticketTitleColumn;
    @FXML
    private TableColumn<Ticket, String> ticketStatusColumn;
    @FXML
    private TableColumn<Ticket, String> ticketPriorityColumn;
    @FXML
    private TableColumn<Ticket, String> ticketDescriptionColumn;
    @FXML
    private RadioButton allTicketsRadioButton;
    @FXML
    private RadioButton selectedProjectRadioButton;
    @FXML
    private ToggleGroup ticketToggleGroup;


    /**
     * This method opens the all projects screen which shows a breakdown of all projects.
     * @param actionEvent
     * @throws IOException
     */
    public void projectsButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane projectDashboardParent = new StackPane();
        projectDashboardParent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("allProjectsScreen.fxml"))));
        Scene scene = new Scene(projectDashboardParent);
        Stage projectDashboardScene = new Stage();
        projectDashboardScene.setScene(scene);
        projectDashboardScene.initModality(Modality.WINDOW_MODAL);
        projectDashboardScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        projectDashboardScene.sizeToScene();
        projectDashboardScene.setResizable(false);
        projectDashboardScene.setTitle("Project Management System");
        projectDashboardScene.show();
    }

    /**
     * This method opens the all users screen when the users button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void usersButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane usersParent = new StackPane();
        usersParent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userDetailDashboard.fxml"))));
        Scene scene = new Scene(usersParent);
        Stage newUsersScene = new Stage();
        newUsersScene.setScene(scene);
        newUsersScene.initModality(Modality.WINDOW_MODAL);
        newUsersScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        newUsersScene.sizeToScene();
        newUsersScene.setResizable(false);
        newUsersScene.setTitle("Project Management System");
        newUsersScene.show();
    }

    /**
     * This method opens a screen showing the details of the selected project on the projects table.
     * @param actionEvent
     * @throws IOException
     */
    public void projectDetailsButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("projectDashboard.fxml"));
            StackPane projectDashboardParent = new StackPane();
            projectDashboardParent.getChildren().add(loader.load());
            Scene scene = new Scene(projectDashboardParent);
            Stage projectDashboardScene = new Stage();
            projectDashboardScene.setScene(scene);
            ProjectDashboardController controller = loader.getController();
            controller.getInitializeData(allProjectsTable.getSelectionModel().getSelectedItem());
            projectDashboardScene.initModality(Modality.WINDOW_MODAL);
            projectDashboardScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            projectDashboardScene.sizeToScene();
            projectDashboardScene.setResizable(false);
            projectDashboardScene.setTitle("Project Management System");
            projectDashboardScene.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No project selected");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This method opens the new project window when the new project button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void createNewProjectButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane newProjectParent = new StackPane();
        newProjectParent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewProjectScreen.fxml"))));
        Scene scene = new Scene(newProjectParent);
        Stage newProjectScene = new Stage();
        newProjectScene.setScene(scene);
        newProjectScene.initModality(Modality.WINDOW_MODAL);
        newProjectScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        newProjectScene.sizeToScene();
        newProjectScene.setResizable(false);
        newProjectScene.setTitle("Project Management System");
        newProjectScene.show();
    }

    /**
     * This method will delete the selected project from the all projects' table on the admin dashboard when the delete
     * project button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void deleteProjectButtonPressed(ActionEvent actionEvent) throws IOException {
        //TODO need to create functionality that if you delete a project it deletes all corresponding tickets or gives
        // the option to leave the corresponding tickets as unassigned.
        try{
            Connection conn = DatabaseConnection.getConnection();
            String deleteStatement = "DELETE FROM Project_Table WHERE Project_Id = ?";
            DatabaseQuery.setPreparedStatement(conn, deleteStatement);
            Project deletingProject = allProjectsTable.getSelectionModel().getSelectedItem();
            int projectId = deletingProject.getProjectId();
            String projectTitle = deletingProject.getProjectTitle();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            if (deletingProject == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No project selected for deletion.");
                alert.setTitle("Empty Selection");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete: "+"\n"
                        +"Project ID: "+ projectId +"\n"+
                        "Project Title: "+ projectTitle);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ps.setInt(1, projectId);
                    ps.execute();
                }
            }
            allProjectsTable.setItems(DatabaseAccess.getAllProjects());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No project selected to delete.");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This method will open a screen to display the details of the selected ticket on all tickets table from the
     * admin dashboard screen.
     * @param actionEvent
     * @throws IOException
     */
    public void ticketDetailsButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TicketDetailsScreen.fxml"));
            StackPane ticketDetailParent = new StackPane();
            ticketDetailParent.getChildren().add(loader.load());
            Scene scene = new Scene(ticketDetailParent);
            Stage ticketDetailScreen = new Stage();
            ticketDetailScreen.setScene(scene);
            TicketDetailsScreenController controller = loader.getController();
            controller.getInitializeData(allTicketsTable.getSelectionModel().getSelectedItem());
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
    }

    /**
     * This method will open a new screen to create a new ticket when the create new ticket button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void createNewTicketButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane newTicketParent = new StackPane();
        newTicketParent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewTicketScreen.fxml"))));
        Scene scene = new Scene(newTicketParent);
        Stage newTicketScene = new Stage();
        newTicketScene.setScene(scene);
        newTicketScene.initModality(Modality.WINDOW_MODAL);
        newTicketScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        newTicketScene.sizeToScene();
        newTicketScene.setResizable(false);
        newTicketScene.setTitle("Project Management System");
        newTicketScene.show();
    }

    /**
     * This method deletes the ticket selected on the admin dashboards all tickets table when the delete ticket button
     * is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void deleteTicketButtonPressed(ActionEvent actionEvent) throws IOException {
        try{
            Connection conn = DatabaseConnection.getConnection();
            String deleteStatement = "DELETE FROM Ticket_Table WHERE Ticket_Id = ?";
            DatabaseQuery.setPreparedStatement(conn, deleteStatement);
            Ticket deletingTicket = allTicketsTable.getSelectionModel().getSelectedItem();
            int ticketId = deletingTicket.getTicketId();
            String ticketTitle = deletingTicket.getTicketTitle();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            if (deletingTicket == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No ticket selected for deletion.");
                alert.setTitle("Empty Selection");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to delete: "+"\n"
                        +"Ticket ID: "+ ticketId +"\n"+
                        "Ticket Title: "+ ticketTitle);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ps.setInt(1, ticketId);
                    ps.execute();
                }
            }
            if(selectedProjectRadioButton.isSelected()) {
                allTicketsTable.setItems(DatabaseAccess.getSelectedTickets(getSelectedProjectID()));
            }
            else{
                allTicketsTable.setItems(DatabaseAccess.getAllTickets());
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No project selected to delete.");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This method will close the admin dashboard when the close button is pressed.
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method retrieves the selected project id from the selected project of the all projects table. Its primary
     * function is to be used in order to find the tickets that correspond with the selected project and fill the
     * selected tickets table.
     * @return - selected projects id
     */
    public int getSelectedProjectID(){
        return allProjectsTable.getSelectionModel().getSelectedItem().getProjectId();
    }

    /**
     * This method will fill the ticket table will all the databases tickets when the all tickets radio button is selected.
     * @param actionEvent
     */
    public void allTicketsRadioButtonSelected(ActionEvent actionEvent){
        allTicketsTable.setItems(DatabaseAccess.getAllTickets());
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketTitleColumn.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        projectTicketIdColumn.setCellValueFactory(new PropertyValueFactory<>("projectAssociation"));
        ticketPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPriorityLevel"));
        ticketStatusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatusLevel"));
        ticketDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("ticketDescription"));
    }

    /**
     * This method will fill the tickets table will only the tickets that correspond to the selected project when the
     * selected project radio button is selected.
     * @param actionEvent
     */
    public void selectedProjectRadioButtonSelected(ActionEvent actionEvent){
        try {
            allTicketsTable.setItems(DatabaseAccess.getSelectedTickets(getSelectedProjectID()));
            ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
            ticketTitleColumn.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
            projectTicketIdColumn.setCellValueFactory(new PropertyValueFactory<>("projectAssociation"));
            ticketPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPriorityLevel"));
            ticketStatusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatusLevel"));
            ticketDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("ticketDescription"));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a project to display its assigned tickets.");
            alert.setTitle("Alert");
            Optional<ButtonType> result = alert.showAndWait();
            allTicketsRadioButton.setSelected(true);
        }
    }

    /**
     * This method will refresh the project table after changes have been made, and it is called on.
     */
    public void refreshAdminProjectTable(){
        allProjectsTable.setItems(DatabaseAccess.getAllProjects());
    }

    /**
     * This method will refresh the ticket table after changes have been made, and it is called on.
     */
    public void refreshAdminTicketTable(){
        allTicketsTable.setItems(DatabaseAccess.getAllTickets());
    }

    /**
     * This method initializes the admin dashboard and fills it with the correct default data to display to the user.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //project table
        allProjectsTable.setItems(DatabaseAccess.getAllProjects());
        projectIdColumn.setCellValueFactory((new PropertyValueFactory<>("projectId")));
        projectTitleColumn.setCellValueFactory((new PropertyValueFactory<>("projectTitle")));
        startDateColumn.setCellValueFactory((new PropertyValueFactory<>("dateCreated")));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("dateCompleted")));
        projectDescriptionColumn.setCellValueFactory((new PropertyValueFactory<>("projectDescription")));
        //ticket table
        allTicketsTable.setItems(DatabaseAccess.getAllTickets());
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketTitleColumn.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        projectTicketIdColumn.setCellValueFactory(new PropertyValueFactory<>("projectAssociation"));
        ticketPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPriorityLevel"));
        ticketStatusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatusLevel"));
        ticketDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("ticketDescription"));
    }
}
