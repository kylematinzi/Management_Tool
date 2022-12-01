package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class ProjectDashboardController implements Initializable {

    private int count = 0;
    @FXML
    private Label projectTitleLabel;
    @FXML
    private Label dateCreatedLabel;
    @FXML
    private Label projectedCompletionLabel;
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

    private Project selectedProject;
    private int daysOpen;

    public void saveChangesButtonPressed(ActionEvent actionEvent){

    }

    public void addTicketButtonPressed(ActionEvent actionEvent){
        System.out.println("add ticket button pressed");
    }

    public void editTicketButtonPressed(ActionEvent actionEvent){
        System.out.println("edit ticket button pressed");
    }

    public void deleteTicketButtonPressed(ActionEvent actionEvent){
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
        dateCreatedLabel.setText(project.getDateCreated().toString());
        projectedCompletionLabel.setText(project.getDateCompleted().toString());
        projectDescriptionTextArea.setText(project.getProjectDescription());
        daysOpen = Period.between(project.getDateCreated(), LocalDate.now()).getDays();
        daysOpenLabel.setText(String.valueOf(daysOpen));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // for some reason this is throwing a nullpointer exception
//        ticketTableView.setItems(DatabaseAccess.getAllTickets());
//        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
    }
}
