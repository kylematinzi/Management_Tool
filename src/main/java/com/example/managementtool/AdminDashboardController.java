package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

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

    public void projectsButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane projectDashboardParent = new StackPane();
        projectDashboardParent.getChildren().add(FXMLLoader.load(getClass().getResource("allProjectsScreen.fxml")));
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

    public void usersButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane usersParent = new StackPane();
        usersParent.getChildren().add(FXMLLoader.load(getClass().getResource("userDetailDashboard.fxml")));
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

    public void projectDetailsButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane projectDashboardParent = new StackPane();
        projectDashboardParent.getChildren().add(FXMLLoader.load(getClass().getResource("projectDashboard.fxml")));
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

    public void createNewProjectButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane newProjectParent = new StackPane();
        newProjectParent.getChildren().add(FXMLLoader.load(getClass().getResource("NewProjectScreen.fxml")));
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

    public void deleteProjectButtonPressed(ActionEvent actionEvent) throws IOException {}


    public void ticketDetailsButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane ticketDetailParent = new StackPane();
        ticketDetailParent.getChildren().add(FXMLLoader.load(getClass().getResource("UpdateTicketScreen.fxml")));
        Scene scene = new Scene(ticketDetailParent);
        Stage ticketDetailScene = new Stage();
        ticketDetailScene.setScene(scene);
        ticketDetailScene.initModality(Modality.WINDOW_MODAL);
        ticketDetailScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        ticketDetailScene.sizeToScene();
        ticketDetailScene.setResizable(false);
        ticketDetailScene.setTitle("Project Management System");
        ticketDetailScene.show();
    }

    public void createNewTicketButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane newTicketParent = new StackPane();
        newTicketParent.getChildren().add(FXMLLoader.load(getClass().getResource("NewTicketScreen.fxml")));
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

    public void deleteTicketButtonPressed(ActionEvent actionEvent) throws IOException {

    }


    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allProjectsTable.setItems(DatabaseAccess.getAllProjects());
        projectIdColumn.setCellValueFactory((new PropertyValueFactory<>("projectId")));
        projectTitleColumn.setCellValueFactory((new PropertyValueFactory<>("title")));
        startDateColumn.setCellValueFactory((new PropertyValueFactory<>("start date")));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end date")));
        projectDescriptionColumn.setCellValueFactory((new PropertyValueFactory<>("project description")));

    }
}
