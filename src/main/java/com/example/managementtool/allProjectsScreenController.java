package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class allProjectsScreenController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Label projectedDateLabel;
    @FXML
    private Label completionPercentLabel;
    @FXML
    private TableView<Project> allProjectsTableView;
    @FXML
    private PieChart projectsPieChart;
    @FXML
    private ProgressBar projectCompletionBar;
    @FXML
    private TableColumn<Project, Integer> projectIdColumn;
    @FXML
    private TableColumn<Project, String> projectTitleColumn;
    private int selectedProjectId;

    private static final ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();


    /**
     * This function needs to fill the pie chart. Idea is to count total amount of tickets for the selected project. Then
     * count each type of project. Then fill the chart. Going to need counters for each type. I feel like there's a better
     * way to do this,  I'll figure that out later. I'm going to remove unassigned option.
     * @return
     */
    public static ObservableList<PieChart.Data> fillProjectsPieChart(int projectId) throws SQLException {
        // need to add project id to pie chart parameter
        int notStarted, inWork, complete, totalTickets;
        notStarted = DatabaseAccess.countNotStartedTickets(projectId);
        inWork = DatabaseAccess.countInWorkTickets(projectId);
        complete = DatabaseAccess.countCompletedTickets(projectId);
        totalTickets = DatabaseAccess.countAllTickets(projectId);
        // just test data
        pieChartData.clear();
        pieChartData.add(new PieChart.Data("Not started: "+ notStarted, notStarted));
        pieChartData.add(new PieChart.Data("In work: "+ inWork, inWork));
        pieChartData.add(new PieChart.Data("Complete: "+ complete, complete));
        System.out.println(pieChartData);
        return pieChartData;
    }

    public void mouseClicked(MouseEvent mouseEvent) throws SQLException {
        selectedProjectId = allProjectsTableView.getSelectionModel().getSelectedItem().getProjectId();
        projectsPieChart.setData(fillProjectsPieChart(selectedProjectId));
        projectedDateLabel.setText(String.valueOf(allProjectsTableView.getSelectionModel().getSelectedItem().getDateCompleted()));
        projectCompletionBar.setProgress((DatabaseAccess.projectCompletionPercentage(selectedProjectId)) / 100);
        completionPercentLabel.setText((int) DatabaseAccess.projectCompletionPercentage(selectedProjectId)+"%");
    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allProjectsTableView.setItems(DatabaseAccess.getAllProjects());
            allProjectsTableView.getSelectionModel().select(0);
            selectedProjectId = allProjectsTableView.getSelectionModel().getSelectedItem().getProjectId();
            projectIdColumn.setCellValueFactory((new PropertyValueFactory<>("projectId")));
            projectTitleColumn.setCellValueFactory((new PropertyValueFactory<>("projectTitle")));
            projectedDateLabel.setText(String.valueOf(allProjectsTableView.getSelectionModel().getSelectedItem().getDateCompleted()));
            //Chart fill
            projectsPieChart.setData(fillProjectsPieChart(allProjectsTableView.getSelectionModel().getSelectedItem().getProjectId()));
            projectCompletionBar.setProgress((DatabaseAccess.projectCompletionPercentage(selectedProjectId)) / 100);
            completionPercentLabel.setText((int) DatabaseAccess.projectCompletionPercentage(selectedProjectId)+"%");
        }catch (Exception e){

        }

    }
}
